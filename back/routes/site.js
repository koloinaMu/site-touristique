var express = require('express');
var router = express.Router();
const mongoose = require('mongoose');
var formidable = require('formidable');
const admin=require('../config/firebaseAdmin')
const Site=require('../models/site')

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

router.get('/randomSite',(req,res)=>{
    Site.aggregate([{$sample: {size: 1}}])
        .then(site => res.status(200).json(site))
        .catch(error => res.status(400).json({ error }));
})

const notification_options = {
    priority: "high",
    timeToLive: 60 * 60 * 24
  };
router.post('/firebase/notification',  (req, res)=>{
  //console.log(req)   
      admin.database().ref("Tokens").get().then((snapshot)=>{          
          const registrationToken=snapshot.val();
          console.log(registrationToken)
         // const  registrationToken = req.body.registrationToken
            const message = {        
                notification: {            
                    title: req.body.title,            
                    body: req.body.body        
                },              
                data: req.body.data || {}    
            };
            const options =  notification_options
            
            Site.aggregate([{$sample: {size: 1}}])
                .then(site =>{
                    console.log(site[0].nom)
                    const message = {        
                        notification: {            
                            title: req.body.title,            
                            body: req.body.body        
                        },              
                        data: req.body.data || {}    
                    };
                    const options =  notification_options
                    admin.messaging().sendToDevice(registrationToken, message, options)
                    .then( response => {

                        res.status(200).send("Notification sent successfully")
                    
                    })
                })
                .catch(error => res.status(400).json({ error }));

            

      })

})



module.exports = router;