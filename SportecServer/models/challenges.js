const mongoose = require('mongoose');

// challenges schema
const challengeScheme = mongoose.Schema({
    name: {
        type: String,
        required: true
    },
    estado: {
        type: String,
        required: true
    },
    asignaciona: {
        type: String,
        required: true
    },
    asignacionpor: {
        type: String,
        required: true
    },
    ganador: {
        type: String,
        required: true
    }
});

// Para que sea accedido desde afuera
const Challenge = module.exports = mongoose.model('Challenge', challengeScheme);

// Get de todos los challenges
module.exports.getChallenges = function (callback) {
    Challenge.find(callback);
};

// Get de un solo challenge segun sea su id de Mongo
module.exports.getChallengesById = function (id, callback) {
    Challenge.findOne({_id: id}, callback);
};

// agregar un challenge
module.exports.addChallenge = function (challenge, callback) {
    Challenge.create(challenge, callback);
};

// actualizar el  challenge segun sea el nombre del mismo
module.exports.updateChallenge = function (name, challenge, options, callback) {
    const query = {name: name};
    const update = {
        name: challenge.name,
		estado: challenge.estado,
        asignaciona: challenge.asignaciona,
        asignacionpor: challenge.asignacionpor,
        ganador: challenge.ganador
    };
    Challenge.findOneAndUpdate(query, update, options, callback);
};

// eliminar un challenge
module.exports.removeChallenge = function (name, callback) {
    const query = {name: name};
    Challenge.deleteOne(query, callback);
};
