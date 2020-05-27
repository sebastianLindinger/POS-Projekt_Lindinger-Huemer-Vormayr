var express = require('express');
var app = express();
var fs = require('fs');
var mongo = require('mongodb');
var MongoClient = require('mongodb').MongoClient;
const url = 'mongodb://localhost:27017/';
var fetch = require('node-fetch');

app.use(express.json());       // to support JSON-encoded bodies
app.use(express.urlencoded()); // to support URL-encoded bodies

var bodyParser = require('body-parser')
app.use(bodyParser.json());       // to support JSON-encoded bodies
app.use(bodyParser.urlencoded({     // to support URL-encoded bodies
    extended: true
}));

var content = fs.readFileSync('municipalities.json');
var jsonMunicipalities = JSON.parse(content);

const collectionName = 'collection';
const dbSize = 1556;

var dbo;
var i = 0;

function initCollection() {
    //create collection
    dbo.createCollection(collectionName, function (err, res) {
        if (err) throw err;
        console.log('Collection created!');

        //insert data into collection
        dbo.collection(collectionName).insertMany(jsonMunicipalities, function (err, res) {
            if (err) throw err;
            console.log('Collection was filled up!');

            //fill collection with weather data
            getWeatherData();
        });
    });
}

MongoClient.connect(url, function (err, db) {
    if (err) throw err;
    dbo = db.db('sunfinderDB');

    //check if collection exists and create one if not
    dbo.listCollections({ name: collectionName }).toArray(function (err, items) {
        if (err) throw err;
        else if (items.length == 1) getWeatherData();
        else initCollection();
    });
});

async function getWeatherData() {
    var date = new Date();
    var hours = date.getHours();
    var urlAPI = 'http://api.openweathermap.org/data/2.5/weather?q=<name>,at&lang=de&appid=e612c50567b28c47bd1e1d25d43fe21e';
    var currentName;

    //update weatherData only between 5.00 a.m. to 9.59 p.m
    if (hours >= 5 && hours <= 21) {
        if (i == dbSize) i = 0;

        dbo.collection(collectionName).find({}).toArray(function (err, result) {
            if (err) throw err;

            currentName = result[i].name;
            urlAPI = urlAPI.replace('<name>', currentName);
            console.log(currentName);

            fetch(urlAPI)
                .then(response => response.json())
                .then(data => {
                    var myQuery = { _id: i.toString() };
                    var newValues = { $set: { weatherData: data } };
                    dbo.collection(collectionName).updateOne(myQuery, newValues, function (err, res) {
                        if (err) throw err;
                    });
                });

            i++;
        });
    }

    //repeat this every 1.87 seconds
    setTimeout(getWeatherData, 1870);
}

//GET method to receive all places in the database
//url looks like this: 'http://localhost:3000/db'
app.get('/db', function (req, res) {
    console.log('Recieved GET-Request...')
    dbo.collection(collectionName).find({}).toArray(function (err, result) {
        if (err) throw err;
        res.json(result);
    })
});

//PUT method writes fact about the place in the database
//url looks like this: 'http://localhost:3000/sunFinder/put?id=1'
app.put('/sunfinder/put', function (req, res) {
    console.log('Recieved PUT-Request...')

    var id = req.query.id;
    var newFact = req.body.fact;

    var myQuery = { _id: id.toString() };
    var newValues = { $addToSet: { facts: newFact } };
    dbo.collection(collectionName).updateOne(myQuery, newValues, function (err, result) {
        if (err) throw err;
        res.send('succesfully updated')
    });
});

//GET method to receive all SUNNY places in the database
//url looks like this: 'http://localhost:3000/sunFinder/get?lat=24&lon=42'
app.get('/sunFinder/get', function (req, res) {
    console.log('Recieved GET-Request...')
    var query = req.query;

    //variables are needed for distance calculation
    var lat = query.lat;
    var lon = query.lon;

    dbo.collection(collectionName).find({}).toArray(function (err, result) {
        if (err) throw err;
        placesArr = [];

        for (var j = 0; j < dbSize; j++) {
            var resultObj = result[j];

            //variables are needed for distance calculation
            var latObj = resultObj.weatherData.coord.lat;
            var lonObj = resultObj.weatherData.coord.lon;

            //calc Distance
            resultObj.distance = getDistance(lat, lon, latObj, lonObj);

            placesArr.push(resultObj);
        }

        //sort array
        placesArr = sortJSON(placesArr, 'distance');

        //remove places with bad weather and show only first x places
        var sunnyPlacesArr = removeBadWeatherPlaces(placesArr, 5);

        console.log(sunnyPlacesArr)
        res.json(sunnyPlacesArr);
    });
});

app.listen(3000, function () {
    console.log('Example app listening on port 3000!');
});

//method for distance calculation
function getDistance(lat1, lon1, lat2, lon2) {
    var R = 6371; // Radius of the earth in km
    var dLat = degToRad(lat2 - lat1);  // deg2rad below
    var dLon = degToRad(lon2 - lon1);
    var a =
        Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(degToRad(lat1)) * Math.cos(degToRad(lat2)) *
        Math.sin(dLon / 2) * Math.sin(dLon / 2)
        ;
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    var d = R * c; // Distance in km
    return d;
}

function degToRad(deg) {
    return deg * (Math.PI / 180)
}

function sortJSON(data, key) {
    return data.sort(function (a, b) {
        var x = a[key]; var y = b[key];
        return ((x < y) ? -1 : ((x > y) ? 1 : 0));
    });
}

//function gets array of places and count
//returns the place where you are and count-1 places near you where sun shines
function removeBadWeatherPlaces(placesArr, count) {
    var sunnyPlacesArr = [];

    //first index = your current place
    sunnyPlacesArr[0] = placesArr[0];

    for(var j = 1; j < dbSize; j++) {
        var resultObj = placesArr[j];

        //check if sun shines
        //if sun shines -> push to array
        if(resultObj.weatherData.weather[0].icon == '01d' && resultObj.weatherData.weather[0].id == 800) {
            sunnyPlacesArr.push(resultObj);
            if(count == sunnyPlacesArr.length) return sunnyPlacesArr;
        }
    }
    return sunnyPlacesArr;
}