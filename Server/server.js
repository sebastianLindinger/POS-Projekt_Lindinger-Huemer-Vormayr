var express = require('express');
var app = express();
var fs = require('fs');
var mongo = require('mongodb');
var MongoClient = require('mongodb').MongoClient;
var url = "mongodb://localhost:27017/";



var content = fs.readFileSync('municipalities.json');
var jsonMunicipalities = JSON.parse(content);

function municipalitiesToArray(json) {
    var result = [];
    var keys = Object.keys(json);
    keys.forEach(function(key) {
        result.push(json[key]);
    });
    return result;
}
var municipalities = municipalitiesToArray(jsonMunicipalities);

MongoClient.connect(url, function(err, db) {
    if (err) throw err;
    var dbo = db.db("sunfinderDB");

    //nur beim 1. durchlauf
    dbo.createCollection("municipalities", function(err, res) {
    if (err) throw err;
    console.log("Collection created!");
    });
    dbo.collection("municipalities").insertMany(jsonMunicipalities);

    //show everything
    dbo.collection("municipalities").find({}).toArray(function(err, result) {
        if (err) throw err;
        console.log(result);
        db.close();
    });

    //show id and postCode
    dbo.collection("municipalities").find({}, {projection: {name: 0}}).toArray(function(err, result) {
        if (err) throw err;
        console.log(result);
        db.close();
    });
});

app.get('/', function(req, res) {
    res.send(municipalities);
});

app.listen(3000, function() {
    console.log('Example app listening on port 3000!');
});