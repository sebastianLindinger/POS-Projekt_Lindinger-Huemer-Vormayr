var express = require('express');
var app = express();
var fs = require('fs');
var mongo = require('mongodb');
var MongoClient = require('mongodb').MongoClient;
const url = "mongodb://localhost:27017/";
var fetch = require('node-fetch');

var content = fs.readFileSync('municipalities.json');
var jsonMunicipalities = JSON.parse(content);

const collectionName = "test566";

var dbo;

function initCollection() {
    //create collection
    dbo.createCollection(collectionName, function (err, res) {
        if (err) throw err;
        console.log("Collection created!");

        //insert data into collection
        dbo.collection(collectionName).insertMany(jsonMunicipalities, function (err, res) {
            if (err) throw err;
            console.log("collection was filled up!");

            //fill collection with weather data
            getWeatherData();
        });
    });
}

MongoClient.connect(url, function (err, db) {
    if (err) throw err;
    dbo = db.db("sunfinderDB");

    //check if collection exists
    dbo.listCollections({ name: collectionName }).toArray(function (err, items) {
        if (err) throw err;
        else if (items.length == 1) getWeatherData();
        else initCollection();
    });
});

var i = 0;

async function getWeatherData() {
    var date = new Date();
    var hours = date.getHours();
    var urlAPI = 'http://api.openweathermap.org/data/2.5/weather?q=<name>&appid=e612c50567b28c47bd1e1d25d43fe21e';
    var currentName;

    //update weatherData only between 5.00 a.m. to 9.59 p.m
    if (hours <= 21 && hours >= 5) {
        if (i == 1582) i = 0;

        dbo.collection(collectionName).find({}).toArray(function (err, result) {
            if (err) throw err;
            console.log(result);

            currentName = result[i].name;
            urlAPI = urlAPI.replace("<name>", currentName);
            console.log(currentName);

            fetch(urlAPI)
                .then(response => response.json())
                .then(data => {
                    var myQuery = { _id: i.toString() };
                    var newValues = { $set: { weatherData: JSON.stringify(data) } };
                    dbo.collection(collectionName).updateOne(myQuery, newValues, function (err, res) {
                        if (err) throw err;
                    });
                });

            i++;
        });
    }
    setTimeout(getWeatherData, 1870);
}

app.get('/', function (req, res) {
    dbo.collection(collectionName).find({}).toArray(function (err, result) {
        if (err) throw err;
        res.json(result);
    })

});

app.listen(3000, function () {
    console.log('Example app listening on port 3000!');
});