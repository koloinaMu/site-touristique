const mongoose = require('mongoose');

const siteSchema = mongoose.Schema( { 
    nom: String,
    description: String,
    region: String,
    files:[]
    //fileId: [] //mongoose.Schema.Types.ObjectId
});

module.exports=mongoose.model('Site', siteSchema);