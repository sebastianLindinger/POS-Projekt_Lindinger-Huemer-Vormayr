var express = require('express');
var app = express();
var fs = require('fs');
var mongo = require('mongodb');
var MongoClient = require('mongodb').MongoClient;
const url = 'mongodb://localhost:27017/';
var fetch = require('node-fetch');

var content = fs.readFileSync('municipalities.json');
var jsonMunicipalities = JSON.parse(content);

const collectionName = 'newestData222';
const dbSize = 1555;

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
    var urlAPI = 'http://api.openweathermap.org/data/2.5/weather?q=<name>,at&appid=e612c50567b28c47bd1e1d25d43fe21e';
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

//GET method to receive all SUNNY places in the database
//url looks like this: 'http://localhost:3000/sunFinder?lat=24&lon=42'
app.get('/sunFinder', function (req, res) {
    console.log('Recieved GET-Request...')
    var query = req.query;

    //variables are needed for distance calculation
    var lat = query.lat;
    var lon = query.lon;

    dbo.collection(collectionName).find({}).toArray(function (err, result) {
        if (err) throw err;
        sunnyPlacesArr = [];
        for (var j = 0; j < dbSize; j++) {
            var resultObj = result[j];
            if (resultObj.weatherData.clouds.all <= 20) {
                //variables are needed for distance calculation
                var latJ = resultObj.weatherData.coord.lat;
                var lonJ = resultObj.weatherData.coord.lon;

                //calc Distance

                sunnyPlacesArr.push(resultObj);
            }
        }
        res.json(sunnyPlacesArr);
    });
});

app.listen(3000, function () {
    console.log('Example app listening on port 3000!');
});

//method for distance calculation
function getDistance(lat1, lon1, lat2, lon2) {
    var radlat1 = Math.PI * lat1 / 180
    var radlat2 = Math.PI * lat2 / 180
    var radlon1 = Math.PI * lon1 / 180
    var radlon2 = Math.PI * lon2 / 180

    var theta = lon1 - lon2
    var radtheta = Math.PI * theta / 180
    var dist = Math.sin(radlat1) * Math.sin(radlat2) + Math.cos(radlat1) * Math.cos(radlat2) * Math.cos(radtheta);
    dist = Math.acos(dist)

    dist = (dist * 180 / Math.PI) * 60 * 1.853159616;
    
    return dist
}