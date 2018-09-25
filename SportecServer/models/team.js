const mongoose = require('mongoose');

// teams schema
const teamScheme = mongoose.Schema({
    name: {
        type: String,
        required: true
    },
    image: {
        type: String,
        required: true
    },
    type: {
        type: String,
        required: true
    },
    member: {
        type: Array,
        required: true
    },
    request: {
        type: Array,
        required: true
    },
	sport: {
        type: String,
        required: true
    }
});

// Para que sea accedido desde afuera
const Team = module.exports = mongoose.model('Team', teamScheme);

// Get de todos los equipos
module.exports.getTeams = function (callback) {
    Team.find(callback);
};

// Get de los equipos que tienen nombres similares a las palabras buscadas en el buscador de contenido
module.exports.getTeamsSearch = function (name,req,callback) {
    const regex = new RegExp(escapeRegex(req.query.search),'gi');
	Team.find({name:regex},callback);
};

// Get de un solo team segun sea su id de Mongo
module.exports.getTeamsById = function (id, callback) {
    Team.findOne({_id: id}, callback);
};

// agregar un equipo
module.exports.addTeam = function (team, callback) {
    Team.create(team, callback);
};

// actualizar el  equipo segun sea el nombre del mismo
module.exports.updateTeam = function (name, team, options, callback) {
    const query = {name: name};
    const update = {
        name: team.name,
		image: team.image,
        type: team.type,
        member: team.member,
        request: team.request,
		sport: team.sport
    };
    Team.findOneAndUpdate(query, update, options, callback);
};

// eliminar un equipo
module.exports.removeTeam = function (name, callback) {
    const query = {name: name};
    Team.deleteOne(query, callback);
};

//Esta funcion ayuda a la busqueda de los textos de expresiones regulares
function escapeRegex(text){
    return text.replace(/[-\/\\^$*+?.()|[\]{}]/g, '\\$&');
};


