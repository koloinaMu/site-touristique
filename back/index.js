var express=require("express");
var app=express();
var cors=require('cors');
app.use(cors());
var bodyParser = require('body-parser');
var mongo = require('mongodb');
const uri =
"mongodb://127.0.0.1:27017/touriste";
var md5=require("md5");


var jsonParser = bodyParser.json();
var MongoClient=mongo.MongoClient;
 
var urlencodedParser = bodyParser.urlencoded({ extended: false });



app.get('/sites',function(req,res) {
	MongoClient.connect(uri,function (err,db) {
		if(err) throw err;
		var dbo=db.db("sitetouristique");
		dbo.collection("site").find({}).toArray(function (err,ress) {
			db.close();
			res.send(ress);
		})
	})
});


app.get('/details_site/:id',function(req,res) {
	var id=req.params.id;
	MongoClient.connect(uri,function (err,db) {
		if(err) throw err;
		
		var myquery = { _id: new mongo.ObjectId(id) };
		var dbo=db.db("sitetouristique");
	
		dbo.collection("site").find(myquery).toArray(function (err,ress) {
			console.log(query);
			db.close();			
			res.send(ress);
		});
	})
});


app.listen(3000,function () {
	console.log("start app");
});


