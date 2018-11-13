import express from 'express';
import { ApolloServer, gql } from 'apollo-server-express';
import faker from 'faker';
import lodash from 'lodash';
import typeDefs from './schema';
import resolvers from './resolvers';
import db from './models';


const server = new ApolloServer({
  typeDefs: gql(typeDefs),
  resolvers,
  context: { db }
});

const app = express();
server.applyMiddleware({ app });

app.use(express.static('app/public'));

db.sequelize.sync().then(() => {
  // populate author table with dummy data

  // populate post table with dummy data
  db.sport.bulkCreate(
    lodash.times(10, () => ({
      name: faker.lorem.sentence(),
      image: faker.lorem.sentence(),
      type: faker.lorem.sentence(),
    })),
  );

  app.listen({ port: 4000 }, () =>
    console.log(`🚀 Server ready at http://localhost:4000${server.graphqlPath}`),
  );
});