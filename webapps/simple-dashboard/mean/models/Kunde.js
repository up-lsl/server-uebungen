const mongoose = require('mongoose');

// Bildet die Dokumentenstruktur der Collection Kundentabelle ab.
const KundeSchema = mongoose.Schema(
	{
		id: Number,
		name: String
	}
);

// Erstellt das benötigte Schema mit Name, Schema und der zugehörigen Collection!
const Kunde = mongoose.model('Kunde', KundeSchema, 'kunden');

// Export für den externen Aufruf.
module.exports = Kunde;
