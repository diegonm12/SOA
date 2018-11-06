import mongoose from 'mongoose'

// news schema
const NewsScheme = mongoose.Schema({
    sport: String,
    title: String,
    content: String,
    important: String,
    image: String,
    type: String 
});

const News = mongoose.model('news',NewsScheme);

export default News;