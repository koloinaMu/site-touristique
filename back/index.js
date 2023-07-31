var createError = require("http-errors");
const express = require('express')
var path = require("path");
var cors = require("cors");
const bodyParser = require('body-parser');
const mongoose = require('mongoose');

const app = express()
const port = 3000

mongoose.connect('mongodb://0.0.0.0:27017/sitetouristique?retryWrites=true&w=majority',
  { useNewUrlParser: true,
    useUnifiedTopology: true })
  .then(() => console.log('Connexion à MongoDB réussie !'))
  .catch(() => console.log('Connexion à MongoDB échouée !'));

const loginRouter=require('./routes/login')


app.use(bodyParser.json())
app.use(bodyParser.urlencoded({ extended: false }))

app.use(cors());
app.use(express.json());
app.use(express.urlencoded({ extended: false }));


app.use(function(req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
  next();
});

// routes config
app.get('/', (req, res) => {
  res.send('Hello World!')
})
app.use('/user',loginRouter);

//app
const server=app.listen(port, () => {
  console.log(`App running on http://localhost:${port}`)
})

module.exports = app;