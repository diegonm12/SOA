var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var mongoose = require('mongoose');

User = require('./models/user');
Sport = require('./models/sport');
New = require('./models/news');
Challenge = require('./models/challenges');
Team = require('./models/team');
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
	if(req.query.search){
		User.getUsersSearch(req.params.name,req,function (err, users) {
		    if (err) {
		        throw err;
		    }
		    res.json(users);
		});
	
	}
	else{
		User.getUsers(function (err, users) {
		    if (err) {
		        throw err;
		    }
		    res.json(users);
		});
	}
});

//el add de un usuario
app.post('/api/users', function (req, res) {
    var user = req.body;
    User.addUser(user, function (err, user) {
        if (err) {
            throw err;
        }
        res.json(user);
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
        res.json(user);
    });
});

//la eliminacion de un user
app.delete('/api/users/:email', function (req, res) {
    var email = req.params.email;
    User.removeUser(email, function (err, user) {
        if (err) {
            throw err;
        }
        res.json(user);
    });
});

//el get de un solo user con el id como email
app.get('/api/users/:email', function (req, res) {
    User.getUserByEmail(req.params.email, function (err, user) {
        if (err) {
            throw err;
        }
        res.json(user);
    });
});

//el get de un solo user con el id como nombre
app.get('/api/users/name/:name', function (req, res) {
    User.getUserByName(req.params.name, function (err, user) {
        if (err) {
            throw err;
        }
        res.json(user);
    });
});


//Metodos CRUD para los deportes
//******************************************************
//el get de todos los deportes
app.get('/api/sports', function (req, res) {
	if(req.query.search){
		Sport.getSportsSearch(req.params.name,req,function (err, sports) {
		    if (err) {
		        throw err;
		    }
		    res.json(sports);
		});
	
	}
	else{
		Sport.getSports(function (err, sports) {
		    if (err) {
		        throw err;
		    }
		    res.json(sports);
		});
	}
});

//el get de un solo deporte segun sea elnombre
app.get('/api/sports/:name', function (req, res) {
    Sport.getSportByName(req.params.name,function (err, sport) {
        if (err) {
            throw err;
        }
        res.json(sport);
    });
});

//el add de un usuario
app.post('/api/sports', function (req, res) {
    var sport = req.body;
    Sport.addSport(sport, function (err, sport) {
        if (err) {
            throw err;
        }
        res.json(sport);
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
        res.json(sport);
    });
});

//la eliminacion de un deporte
app.delete('/api/sports/:name', function (req, res) {
    var name = req.params.name;
    Sport.removeSport(name, function (err, sport) {
        if (err) {
            throw err;
        }
        res.json(sport);
    });
});

//
//***********************************************************************


//Metodos CRUD para las noticias
//******************************************************
//el get de todas las noticias
app.get('/api/news', function (req, res) {
	if(req.query.search){
		New.getNewsSearch(req.params.title,req,function (err, news) {
		    if (err) {
		        throw err;
		    }
		    res.json(news);
		});
	
	}
	else{
		New.getNews(function (err, news) {
		    if (err) {
		        throw err;
		    }
		    res.json(news);
		});
	}
});


//el get de una sola noticia segun sea su id
app.get('/api/news/id/:_id', function (req, res) {
    New.getNewsById(req.params._id,function (err, news) {
        if (err) {
            throw err;
        }
        res.json(news);
    });
});

//el add de una noticia
app.post('/api/news', function (req, res) {
    var news = req.body;
    New.addNew(news, function (err, news) {
        if (err) {
            throw err;
        }
        res.json(news);
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
        res.json(news);
    });
});

//la eliminacion de una noticia
app.delete('/api/news/:title', function (req, res) {
    var title = req.params.title;
    New.removeNew(title, function (err, news) {
        if (err) {
            throw err;
        }
        res.json(news);
    });
});

//el get de las noticias segun sea su deporte
app.get('/api/news/:sport', function (req, res) {
    New.getNewsBySport(req.params.sport, function (err, news) {
        if (err) {
            throw err;
        }
        res.json(news);
    });
});

//Metodos CRUD para los equipos
//******************************************************
//el get de todos los equipos
app.get('/api/teams', function (req, res) {
	if(req.query.search){
		Team.getTeamsSearch(req.params.name,req,function (err, teams) {
		    if (err) {
		        throw err;
		    }
		    res.json(teams);
		});
	
	}
	else{
		Team.getTeams(function (err, teams) {
		    if (err) {
		        throw err;
		    }
		    res.json(teams);
		});
	}
});


//el get de un solo equipo segun sea su id
app.get('/api/teams/id/:_id', function (req, res) {
    Team.getTeamsById(req.params._id,function (err, team) {
        if (err) {
            throw err;
        }
        res.json(team);
    });
});

//el add de un equipo
app.post('/api/teams', function (req, res) {
    var team = req.body;
    Team.addTeam(team, function (err, team) {
        if (err) {
            throw err;
        }
        res.json(team);
    });
});

//la actualizacion de un equipo
app.put('/api/teams/:name', function (req, res) {
    var name = req.params.name;
    var team = req.body;
    Team.updateTeam(name, team, {}, function (err, team) {
        if (err) {
            throw err;
        }
        res.json(team);
    });
});

//la eliminacion de un equipo
app.delete('/api/teams/:name', function (req, res) {
    var name = req.params.name;
    Team.removeTeam(name, function (err, team) {
        if (err) {
            throw err;
        }
        res.json(team);
    });
});

//
//***********************************************************************

//Metodos CRUD para los Retos
//******************************************************
//el get de todos los retos
app.get('/api/challenges', function (req, res) {
	Challenge.getChallenges(function (err, challenges) {
		if (err) {
			throw err;
		}
		res.json(challenges);
		});
	
});


//el get de un solo reto segun sea su id
app.get('/api/challenges/id/:_id', function (req, res) {
    Challenge.getChallengesById(req.params._id,function (err, challenge) {
        if (err) {
            throw err;
        }
        res.json(challenge);
    });
});

//el add de un reto
app.post('/api/challenges', function (req, res) {
    var challenge = req.body;
    Challenge.addChallenge(challenge, function (err, challenge) {
        if (err) {
            throw err;
        }
        res.json(challenge);
    });
});

//la actualizacion de un reto
app.put('/api/challenges/:name', function (req, res) {
    var name = req.params.name;
    var challenge = req.body;
    Challenge.updateChallenge(name, challenge, {}, function (err, challenge) {
        if (err) {
            throw err;
        }
        res.json(challenge);
    });
});

//la eliminacion de un reto
app.delete('/api/challenges/:name', function (req, res) {
    var name = req.params.name;
    Challenge.removeChallenge(name, function (err, challenge) {
        if (err) {
            throw err;
        }
        res.json(challenge);
    });
});

//
//***********************************************************************

//Buscador de contenido
//****************************

//este corresponde al get que por medio de un search buscara todos los contenidos en
//titulos de noticia, usuario, nombre de deportes o de equipos con las palabras relacionadas al search
app.get("/api/content", function(req, res) {
    if(req.query.search){
		const regex = new RegExp(escapeRegex(req.query.search),'gi');
		New.find({title:regex}, function(error, news){
        	User.find({name:regex}, function(error, user){
				Sport.find({name:regex},function(error,sport){
				    res.json({
				        news: news,
				        user: user,
						sport:sport
				    });
				});
         	});
     	});
	}
	else{
		res.json({
				  news: [],
				  user: [],
				  sport: []
				  });
	}

});

//Esta funcion ayuda a la busqueda de los textos de expresiones regulares
function escapeRegex(text){
    return text.replace(/[-\/\\^$*+?.()|[\]{}]/g, '\\$&');
};



//**********************************************************************

app.listen(3000);
console.log('Corriendo en el puerto: 3000');
