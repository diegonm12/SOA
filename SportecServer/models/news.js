const mongoose = require('mongoose');

// news schema
const newScheme = mongoose.Schema({
    sport: {
        type: String,
        required: true
    },
    title: {
        type: String,
        required: true
    },
    content: {
        type: String,
        required: true
    },
    important: {
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
    }
});

// Para que sea accedido desde afuera
const New = module.exports = mongoose.model('New', newScheme);

// Get de todas las noticias
module.exports.getNews = function (callback) {
    New.find(callback);
};

// Get de las noticias que tienen palabras similares a las buscadas
module.exports.getNewsSearch = function (title,req,callback) {
    const regex = new RegExp(escapeRegex(req.query.search),'gi');
	New.find({title:regex},callback);
};

// Get de una sola noticia segun sea su id
module.exports.getNewsById = function (id, callback) {
    New.findOne({_id: id}, callback);
};

// Get de noticias segun el  deporte
module.exports.getNewsBySport = function (sport, callback) {
    New.find({sport: sport}, callback);
};

// agregar una noticia
module.exports.addNew = function (news, callback) {
    New.create(news, callback);
};

// actualizar noticia segun sea el titulo existente
module.exports.updateNew = function (title, news, options, callback) {
    const query = {title: title};
    const update = {
        sport: news.sport,
		title: news.title,
        content: news.content,
        important: news.important,
        image: news.image,
		type: news.type
    };
    New.findOneAndUpdate(query, update, options, callback);
};

// eliminar una noticia
module.exports.removeNew = function (title, callback) {
    const query = {title: title};
    New.deleteOne(query, callback);
};

//Esta funcion ayuda a la busqueda de los textos de expresiones regulares
function escapeRegex(text){
    return text.replace(/[-\/\\^$*+?.()|[\]{}]/g, '\\$&');
};






