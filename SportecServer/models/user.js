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
    }
});

// Para que sea accedido desde afuera
const User = module.exports = mongoose.model('User', userScheme);

// Get de todos los users
module.exports.getUsers = function (callback) {
    console.log('access!!');
    User.find(callback);
};

// Get de un solo user segun sea su email
module.exports.getUserByEmail = function (email, callback) {
    console.log('access only one!!');
    User.findOne({email: email}, callback);
};

// agregar user
module.exports.addUser = function (user, callback) {
    console.log('se agrega user!!');
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
        sessionInit: user.sessionInit
    };
    User.findOneAndUpdate(query, update, options, callback);
};

// eliminar user
module.exports.removeUser = function (email, callback) {
    const query = {email: email};
    User.deleteOne(query, callback);
};
