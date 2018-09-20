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

// Get de un solo user segun sea su email
//module.exports.getUserByEmail = function (email, callback) {
//    console.log('access only one sport!!');
//    User.findOne({email: email}, callback);
//};

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
