var express = require('express');
var app = express();
var fs = require('fs');
var mongo = require('mongodb');
var MongoClient = require('mongodb').MongoClient;
var url = "mongodb://localhost:27017/";



var content = fs.readFileSync('gemeinden.json');
var jsonGemeinden = JSON.parse(content);

function gemeinden2Array(json) {
	var result = [];
	var keys = Object.keys(json);
	keys.forEach(function (key) {
		result.push(json[key]);
	});
	return result;
}
var gemeinden = gemeinden2Array(jsonGemeinden);

MongoClient.connect(url, function(err, db) {
  if (err) throw err;
  var dbo = db.db("sunfinderDB");
  var myobj = { postCode: "4902", name: "Wolfsegg am Hausruck"};
  dbo.collection("TEST").insertMany(jsonGemeinden, function(err, res) {
      if(err) throw err;
      console.log(res);
      db.close();
  });
});

app.get('/', function (req, res) {
  res.send(
      gemeinden
     );
});

app.listen(3000, function () {
  console.log('Example app listening on port 3000!');
});