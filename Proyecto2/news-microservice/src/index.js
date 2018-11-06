import express from 'express';
const app = express();

import mongoose from 'mongoose';

mongoose.connect('mongodb://mongo:27017/docker-news-mongo', {useNewUrlParser: true})
    .then(()=> console.log('Mongo connected'))
    .catch(err => console.log(err));

//Mongodb Models
import News from './models/News';

import {graphiqlExpress, graphqlExpress} from 'apollo-server-express';
import {makeExecutableSchema} from 'graphql-tools';
import typeDefs from './schema';
import resolvers from './resolvers';

//propiedades y metodos
const schema = makeExecutableSchema({
    typeDefs,
    resolvers
})

//Routes
app.use('/graphql', express.json(), graphqlExpress({
    schema,
    context:{
        News
    }
}));
//Para la interfaz
app.use('/graphiql', graphiqlExpress({
    endpointURL: '/graphql'
}))

app.listen(4000);
console.log("Corriendo en el puerto 4000");