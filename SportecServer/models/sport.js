const mongoose = require('mongoose');
// sport schema
const sportScheme = mongoose.Schema({
    name: {
        type: String,
        required: true
    },
    image: {
        type: String,
        required: true
    }
});

// Para que sea accedido desde afuera
const Sport = module.exports = mongoose.model('Sport', sportScheme);

// Get de todos los deportes
module.exports.getSports = function (callback) {
    console.log('access sport!!');
    Sport.find(callback);
};

// Get de los deportes que tienen palabras similares a las buscadas
module.exports.getSportsSearch = function (name,req,callback) {
    const regex = new RegExp(escapeRegex(req.query.search),'gi');
	Sport.find({name:regex},callback);
};

//Get de un solo sport segun sea su nombre
module.exports.getSportByName = function (name, callback) {
    console.log('access only one sport!!');
    Sport.findOne({name: name}, callback);
};

// agregar deporte
module.exports.addSport = function (sport, callback) {
    console.log('se agrega deporte!!');
    Sport.create(sport, callback);
};

// actualizar deporte segun sea el nombre deldeporte
module.exports.updateSport = function (name, sport, options, callback) {
    console.log('se actualiza  sport!!');
    const query = {name: name}; // para buscar el deporte, segun su nombre
    const update = {
        name: sport.name,
        image: sport.image
    };
    Sport.findOneAndUpdate(query, update, options, callback);
};

// eliminar deporte
module.exports.removeSport = function (name, callback) {
    const query = {name: name};
    Sport.deleteOne(query, callback);
};

//Esta funcion ayuda a la busqueda de los textos de expresiones regulares
function escapeRegex(text){
    return text.replace(/[-\/\\^$*+?.()|[\]{}]/g, '\\$&');
};

