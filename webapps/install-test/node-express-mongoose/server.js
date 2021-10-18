//Importiere express.
const express = require('express');
const app = express();
const PORT = 3000;

// Stelle Verbindung mit Datenbank her.
const dbURL = 'mongodb://localhost:27017/dashboarddb';
const options = {
    user: 'dbuser',
    pass: 'dbuser123',
    useNewUrlParser: true,
    useUnifiedTopology: true
};

const mongoose = require('mongoose');
const bodyParser = require('body-parser');
mongoose.connect(dbURL, options);
mongoose.connection.once('open', ()=>console.log('Mit Datenbank verbunden'));
app.use(bodyParser.urlencoded({extended: true}));
app.use(bodyParser.json());

// Importiere die nötigen Models.
const Produkt = require('./models/Produkt.js');

// Route für eine POST-Anfrage nach Kunden.
app.post('/api/produkte', function(req, res){
    console.log('Received:'+req);
    // Überprüfen, ob die notwendigen Daten übermittelt wurden.
    if(!req.body || !req.body.id || !req.body.name || !req.body.bestand){
        return res.status(400).send('Der Datensatz ist unvollständig!');
    }
    // Neuen Kunden anlegen.
    let produktInstance = new Produkt(req.body);
    // Produkt in Datenbank ablegen.
    produktInstance.save()
        .catch(err=>{
            console.log(err.toString());
            res.status(500).send(err.toString());
        })
        .then(dbres=>{
            console.log(dbres);
            res.json(dbres);
        });
});

// Route für eine GET-Anfrage nach allen Produkten.
app.get('/api/produkte', function(req, res){
    Produkt.find()
        .catch(err=>{
            console.log(err.toString());
            res.status(500).send(err.toString());
        })
        .then(dbres=>{
            console.log(dbres);
            res.json(dbres);
        });
});

// Route für eine GET-Anfrage nach einem spezifischen Produkt (_id in der Datenbank).
app.get('/api/produkte/:_id', function(req, res){
    console.log(req.body);
    let idToGet = req.params._id;
    // Identifiziere das gesuchte Produkt anhand der Datenbank ID
    Produkt.findById(idToGet)
        .catch(err=>{
            console.log(err.toString());
            res.status(500).send(err.toString());
        })
        .then(dbres=>{
            // Gibt das Produkt zurück.
            console.log(dbres);
            res.json(dbres);
        });
});

// Route für eine PUT-Anfrage nach Produkten.
app.put('/api/produkte/:_id', function(req, res){
    console.log(req.body);
    let idToUpdate = req.params._id;
    // Identifiziere das gesuchte Produkt anhand der Datenbank-ID
    Produkt.findByIdAndUpdate(idToUpdate, req.body)
        .catch(err=>{
            console.log(err.toString());
            res.status(500).send(err.toString());
        })
        .then(dbres=>{
            // Gibt die ALTEN Werte zurück.
            console.log(dbres);
            res.json(dbres);
        });
});

// Starte Server.
app.listen(PORT, function(){
    console.log('Server running at port:' + PORT);
});

