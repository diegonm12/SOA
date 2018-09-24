const mongoose = require('mongoose');

// user schema
const userScheme = mongoose.Schema({
    name: {
        type: String,
        required: true
    },
    email: {
        type: String,
        required: true
    },
    password: {
        type: String,
        required: true
    },
    profilePicture: {
        type: String,
        required: true
    },
    sessionInit: {
        type: String,
        required: true
    },
    favSport: {
        type: Array,
        required: true
    },
	type: {
        type: String,
        required: true
    }
});

// Para que sea accedido desde afuera
const User = module.exports = mongoose.model('User', userScheme);

// Get de todos los users
module.exports.getUsers = function (callback) {
    User.find(callback);
};

// Get de un solo user segun sea su email
module.exports.getUserByEmail = function (email, callback) {
    User.findOne({email: email}, callback);
};

// Get de un solo user segun sea su nombre
module.exports.getUserByName = function (name, callback) {
    User.findOne({name: name}, callback);
};

// Get de los usuarios que tienen palabras similares a las buscadas
module.exports.getUsersSearch = function (name,req,callback) {
    const regex = new RegExp(escapeRegex(req.query.search),'gi');
	User.find({name:regex},callback);
};

// agregar user
module.exports.addUser = function (user, callback) {
    User.create(user, callback);
};

// actualizar user
module.exports.updateUser = function (email, user, options, callback) {
    const query = {email: email};
    const update = {
        name: user.name,
        email: user.email,
        password: user.password,
        profilePicture: user.profilePicture,
        sessionInit: user.sessionInit,
		favSport: user.favSport,
		type: user.type
    };
    User.findOneAndUpdate(query, update, options, callback);
};

// eliminar user
module.exports.removeUser = function (email, callback) {
    const query = {email: email};
    User.deleteOne(query, callback);
};

//Esta funcion ayuda a la busqueda de los textos de expresiones regulares
function escapeRegex(text){
    return text.replace(/[-\/\\^$*+?.()|[\]{}]/g, '\\$&');
};
