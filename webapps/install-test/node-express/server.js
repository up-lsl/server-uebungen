const express = require('express');
const app = express();
const PORT = 3000;

// Route für den Fall das ein GET-Request an '/' gesendet wird.
app.get('/test', function (req, res) {
    res.send('Ganz einfacher Express-Test war erfolgreich!');
});


// Middleware sorgt dafür, dass statische Inhalte die über einen HTTP-Request angefragt werden
// aus dem entsprechenden Ordner ausgeliefert werden.
// (Ähnlich zu 'var/www/html' bei Apache wird standardmäßig static/index.html ausgeliefert.)
app.use(express.static('static'));


app.listen(PORT, function(){
    console.log('Server running at port:'+PORT);
})
