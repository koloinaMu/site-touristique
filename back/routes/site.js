var express = require('express');
var router = express.Router();
const mongoose = require('mongoose');
const Utilisateur=require('../models/utilisateur')
var formidable = require('formidable');

router.get('/formAjoutSite', (req, res) => {
    res.sendFile( __dirname  + '/formAjout.html');
    
})

router.post('/ajoutSite', (req, res) => {
    var form = new formidable.IncomingForm();
    form.parse(req, function (err, fields, files) {
        console.log(files)
      res.write('File uploaded');
      res.end();
    });
    
})


module.exports = router;