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
var op = 1460;

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

    //fillAlPlacesWithLatLong() is just a temporary function
    //fillAlPlacesWithLatLong();

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
    setTimeout(getWeatherData, 1000);
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

//GET method to receive all SUNNY places in the database by Coordinates
//url looks like this: 'http://localhost:3000/sunFinder/getByCoord?lat=48.18&lon=13.79'
app.get('/sunFinder/getByCoord', function (req, res) {
    console.log('Recieved GET-Request...')

    //variables are needed for distance calculation
    var lat = req.query.lat;
    var lon = req.query.lon;

    dbo.collection(collectionName).find({}).toArray(function (err, result) {
        if (err) throw err;

        //calc distance for all entries in database
        var placesArr = calcDistanceArr(result, lat, lon);

        //sort array
        placesArr = sortJSON(placesArr, 'distance');

        //remove places with bad weather and show only first x places
        var sunnyPlacesArr = removeBadWeatherPlaces(placesArr, 5);

        res.json(sunnyPlacesArr);
    });
});

//GET method to receive all SUNNY places in the database by name and postcode
//url looks like this: 'http://localhost:3000/sunFinder/getByNameAndPostcode?name=Meggenhofen&postcode=4714'
app.get('/sunFinder/getByNameAndPostcode', function (req, res) {
    console.log('Recieved GET-Request...')

    //variables are needed for distance calculation
    var nameOfPlace = req.query.name;
    var postcodeOfPlace = req.query.postcode;

    dbo.collection(collectionName).find({}).toArray(function (err, result) {
        if (err) throw err;

        //get place for distance calculation
        var myPlace;
        for (var j = 0; j < dbSize; j++) {
            var resultObj = result[j];
            if (nameOfPlace == resultObj.name) {
                myPlace = resultObj;
                break;
            } else if (postcodeOfPlace == resultObj.postCode) {
                myPlace = resultObj;
            }
        }

        //check if place was found
        if (myPlace == null) {
            //bad
            res.json('Did not find Place');
        } else {
            if (err) throw err;

            //calc distance for all entries in database
            var placesArr = calcDistanceArr(result, myPlace.weatherData.coord.lat, myPlace.weatherData.coord.lon);

            //sort array
            placesArr = sortJSON(placesArr, 'distance');

            //remove places with bad weather and show only first x places
            var sunnyPlacesArr = removeBadWeatherPlaces(placesArr, 5);

            res.json(sunnyPlacesArr);
        }
    });
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

app.listen(3000, function () {
    console.log('Example app listening on port 3000!');
});

//function calculates distance for all entries in result
function calcDistanceArr(result, lat, lon) {
    var placesArr = [];
    for (var j = 0; j < dbSize; j++) {
        var resultObj = result[j];

        //variables are needed for distance calculation
        var latObj = resultObj.weatherData.coord.lat;
        var lonObj = resultObj.weatherData.coord.lon;

        //calc Distance
        resultObj.distance = calcDistance(lat, lon, latObj, lonObj);

        placesArr.push(resultObj);
    }
    return placesArr;
}

//function for distance calculation
//returns distance of to places in km
function calcDistance(lat1, lon1, lat2, lon2) {
    const RADIUS = 6371; // Radius of the earth in km
    var dLat = degToRad(lat2 - lat1);  // deg2rad below
    var dLon = degToRad(lon2 - lon1);
    var a =
        Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(degToRad(lat1)) * Math.cos(degToRad(lat2)) *
        Math.sin(dLon / 2) * Math.sin(dLon / 2);
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    var distance = RADIUS * c;
    return distance;
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

//function gets array of places and count as params
//returns count places near you where sun shines (first index in array is always the place where you are)
function removeBadWeatherPlaces(placesArr, count) {
    var sunnyPlacesArr = [];

    //first index = your current place
    sunnyPlacesArr[0] = placesArr[0];

    for (var j = 1; j < dbSize; j++) {
        var resultObj = placesArr[j];

        //check if sun shines
        if (resultObj.weatherData.weather[0].icon == '01d' && resultObj.weatherData.weather[0].id == 800) {
            sunnyPlacesArr.push(resultObj);
            if (count == sunnyPlacesArr.length) return sunnyPlacesArr;
        }
    }
    return sunnyPlacesArr;
}

//function that fills up a Collection, where all AUstrian cities are in,
//with better location data then the openWeatherMap has to get a more exactly distance
function fillAlPlacesWithLatLong() {
    dbo.collection("test").find({}).toArray(function (err, result) {
        if (err) throw err;
        var locationURL = "https://eu1.locationiq.com/v1/search.php?key=a22bb0cf158da3&q=<Place>&format=json"
        var place = result[op].name;
        place = place.split(' ').join('%20');
        place = place.split('ä').join('ae');
        place = place.split('ö').join('oe');
        place = place.split('ü').join('ue');
        place = place.split('Ä').join('Ae');
        place = place.split('Ö').join('Oe');
        place = place.split('Ü').join('Ue');
        place = place.split('ß').join('ss');
        var useURL = locationURL.replace('<Place>', place);
        console.log("");
        console.log(useURL);

        fetch(useURL)
            .then(response => response.json())
            .then(data => {
                //console.log(op);
                var myQuery = { _id: (op+1).toString() };
                console.log(myQuery);
                if(data == undefined){
                    console.log(useURL);
                }
                
                var newValues = { $set: { latitude: data[0].lat, longitude: data[0].lon } };
                //console.log(newValues);
                dbo.collection("test").updateOne(myQuery, newValues, function (err, res) {
                    if (err) {
                        console.log(data);
                        console.log(useURL);
                        console.log(op);
                        throw err;
                    }
                    setTimeout(fillAlPlacesWithLatLong, 1200);
                    op++;
                });


            })


    });
  
    
}