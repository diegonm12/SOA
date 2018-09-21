var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var mongoose = require('mongoose');

User = require('./models/user');
Sport = require('./models/sport');
New = require('./models/news');
app.use(bodyParser.json());


//Conectar a mongoose que se llama sportec por medio de mongoose
mongoose.connect('mongodb://localhost/sportec', {useNewUrlParser: true});

app.get('/', function (req, res) {
    res.send('Utilizar api/sportec')

});

//Metodos CRUD para los usuarios
//******************************************************
//el get de todos los users
app.get('/api/users', function (req, res) {
    User.getUsers(function (err, users) {
        if (err) {
            throw err;
        }
        res.json(users)
    });
});

//el add de un usuario
app.post('/api/users', function (req, res) {
    var user = req.body;
    User.addUser(user, function (err, user) {
        if (err) {
            throw err;
        }
        res.json(user)
    });
});

//la actualizacion de un user
app.put('/api/users/:email', function (req, res) {
    var email = req.params.email;
    var user = req.body;
    User.updateUser(email, user, {}, function (err, user) {
        if (err) {
            throw err;
        }
        res.json(user)
    });
});

//la eliminacion de un user
app.delete('/api/users/:email', function (req, res) {
    var email = req.params.email;
    User.removeUser(email, function (err, user) {
        if (err) {
            throw err;
        }
        res.json(user)
    });
});

//el get de un solo user con el id como email
app.get('/api/users/:email', function (req, res) {
    User.getUserByEmail(req.params.email, function (err, user) {
        if (err) {
            throw err;
        }
        res.json(user)
    });
});


//Metodos CRUD para los deportes
//******************************************************
//el get de todos los deportes
app.get('/api/sports', function (req, res) {
    Sport.getSports(function (err, sports) {
        if (err) {
            throw err;
        }
        res.json(sports)
    });
});

//el add de un usuario
app.post('/api/sports', function (req, res) {
    var sport = req.body;
    Sport.addSport(sport, function (err, sport) {
        if (err) {
            throw err;
        }
        res.json(sport)
    });
});

//la actualizacion de un deporte
app.put('/api/sports/:name', function (req, res) {
    var name = req.params.name;
    var sport = req.body;
    Sport.updateSport(name, sport, {}, function (err, sport) {
        if (err) {
            throw err;
        }
        res.json(sport)
    });
});

//la eliminacion de un deporte
app.delete('/api/sports/:name', function (req, res) {
    var name = req.params.name;
    Sport.removeSport(name, function (err, sport) {
        if (err) {
            throw err;
        }
        res.json(sport)
    });
});

//
//***********************************************************************


//Metodos CRUD para las noticias
//******************************************************
//el get de todas las noticias
app.get('/api/news', function (req, res) {
    New.getNews(function (err, news) {
        if (err) {
            throw err;
        }
        res.json(news)
    });
});

//el add de una noticia
app.post('/api/news', function (req, res) {
    var news = req.body;
    New.addNew(news, function (err, news) {
        if (err) {
            throw err;
        }
        res.json(news)
    });
});

//la actualizacion de una noticia
app.put('/api/news/:title', function (req, res) {
    var title = req.params.title;
    var news = req.body;
    New.updateNew(title, news, {}, function (err, news) {
        if (err) {
            throw err;
        }
        res.json(news)
    });
});

//la eliminacion de una noticia
app.delete('/api/news/:title', function (req, res) {
    var title = req.params.title;
    New.removeNew(title, function (err, news) {
        if (err) {
            throw err;
        }
        res.json(news)
    });
});

//el get de un solo user con el id como email
app.get('/api/news/:sport', function (req, res) {
    New.getNewsBySport(req.params.sport, function (err, news) {
        if (err) {
            throw err;
        }
        res.json(news)
    });
});


//
//***********************************************************************


app.listen(3000);
console.log('Corriendo en el puerto: 3000');
