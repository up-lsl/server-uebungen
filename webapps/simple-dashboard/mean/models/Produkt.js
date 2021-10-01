const mongoose = require('mongoose');

// Bildet die Dokumentenstruktur der Collection Produkte ab.
const ProduktSchema = mongoose.Schema({
	id: Number,
	name: String,
	bestand: Number
});

// Erstellt das benötigte Schema mit Name, Schema und der zugehörigen Collection!
const Produkt = mongoose.model('Produkt', ProduktSchema, 'produkte');

// Export für den externen Aufruf.
module.exports = Produkt;
