var express = require('express');
var router = express.Router();
const mongoose = require('mongoose');
const Utilisateur=require('../models/utilisateur')
const md5=require('md5')

router.post('/login', (req, res) => {
    let data = req.body;
    //console.log(data);
    //console.log(data.mail);
    mail=data.mail;
    var res1=String(mail)
    .toLowerCase()
    .match(/^[a-zA-Z0-9.]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+$/);
    //console.log(res1);
    var erreur=[];
    if(res1!=null){
        //console.log(erreur.erreur)
        mdp=md5(data.mdp);
        Utilisateur.findOne({ mail: mail,mdp:mdp })
            .then(utilisateur => res.status(200).json(utilisateur))
            .catch(error => res.status(400).json({ error }));
    }else{
        erreur={erreur:'Mail invalide'};
        //res.send('ok erreur');
        res.send(erreur);
    }
    
})

router.post('/inscription', (req, res) => {
    let data = req.body;
    //console.log(data);
    //console.log(data.mail);
    mail=data.mail;
    var res1=String(mail)
    .toLowerCase()
    .match(/^[a-zA-Z0-9.]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+$/);
    //console.log(res1);
    var erreur=[];
    if(res1!=null){
        //console.log(erreur.erreur)
        delete req.body._id;
        const user = new Utilisateur({
            nom: data.nom,
            prenom: data.prenom,
            mail: mail,
            mdp: md5(data.mdp)
        });
        user.save()
            .then(() => res.status(201).json({ message: 'Utilisateur enregistré !'}))
            .catch(error => res.status(400).json({ error }));
    }else{
        erreur={erreur:'Mail invalide'};
        //res.send('ok erreur');
        res.send(erreur);
    }
    
})


module.exports = router;