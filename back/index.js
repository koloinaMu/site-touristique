const express = require('express');
const bodyParser = require('body-parser');
const { MongoClient } = require('mongodb');

const app = express();
const port = 3000;

const uri = "mongodb://127.0.0.1:27017";
const client = new MongoClient(uri, { useNewUrlParser: true, useUnifiedTopology: true });
app.set('view engine', 'ejs');


app.get('/sites',function(req,res) {
	client.connect(uri,function (err,db) {
		if(err) throw err;
		var dbo=db.db("sitetouristique");
		dbo.collection("site").find({}).toArray(function (err,ress) {
			db.close();
			res.send(ress);
		})
	})
});

app.listen(port, () => {
    console.log(`Serveur Express en cours d'exécution sur le port ${port}.`);
  });
  