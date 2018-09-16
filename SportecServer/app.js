var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var mongoose = require('mongoose');

User = require('./models/user');
app.use(bodyParser.json());


//Conectar a mongoose
mongoose.connect('mongodb://localhost/sportec', {useNewUrlParser: true});

app.get('/', function (req, res) {
    res.send('Utilizar api/sportec')

});

//el get de todos los users
app.get('/api/users', function (req, res) {
    User.getUsers(function (err, users) {
        if (err) {
            throw err;
        }
        res.json(users)
    });
});

//el get de todos los users
app.post('/api/users', function (req, res) {
    var user = req.body;
    User.addUser(user, function (err, user) {
        if (err) {
            throw err;
        }
        res.json(user)
    });
});

//el get de todos los users
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

//el get de todos los users
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

app.listen(3000);
console.log('Corriendo en el puerto: 3000');