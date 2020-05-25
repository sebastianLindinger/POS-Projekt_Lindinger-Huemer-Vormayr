var express = require('express');
var app = express();
var fs = require('fs');
var mongo = require('mongodb');
var MongoClient = require('mongodb').MongoClient;
var url = "mongodb://localhost:27017/";
var fetch = require('node-fetch');



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

MongoClient.connect(url, function (err, db) {
    if (err) throw err;
    dbo = db.db("sunfinderDB");

    //beim ersten mal
    //dbo.collection("gemeinden1").insertMany(jsonGemeinden, function (err, res) {
    //    if (err) throw err;
    //});

    getWeatherData();
});

async function getWeatherData() {
    var d = new Date();
    var hours = d.getHours();
    var urlAPI = 'http://api.openweathermap.org/data/2.5/weather?q=<municipality>&appid=e612c50567b28c47bd1e1d25d43fe21e';
    var actualName;


    if (hours <= 21 && hours >= 5) {
        if (i == 1582) i = 0;

        dbo.collection("gemeinden1").find({}).toArray(function (err, result) {
            if (err) throw err;
            console.log(result);

            actualName = result[i].name;
            urlAPI = urlAPI.replace("<municipality>", actualName);
            console.log(actualName);

            fetch(urlAPI)
                .then(response => response.json())
                .then(data => {

                    var myquery = { _id: i.toString() };
                    var newvalues = { $set: { weatherData: JSON.stringify(data)} };
                    dbo.collection("gemeinden1").updateOne(myquery, newvalues, function (err, res) {
                        if (err) throw err;
                    });

                });

            urlAPI = urlAPI.replace(actualName, "<municipality>");
            i = i + 1;
        });   
    }
    setTimeout(getWeatherData, 1870);
}

app.get('/', function (req, res) {
    res.send( "fef"
      // dbo.collection("gemeinden1").find({}).toArray(function (err, result) {
        //    if (err) throw err;
         //   console.log(result);
        //})
    );
});

app.listen(3000, function () {
    console.log('Example app listening on port 3000!');
});