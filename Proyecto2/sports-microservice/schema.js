export default `
  type Sport {
    name: String!
    image: String!
    type: String!
  }
  
  type Query {
    allSports: [Sport!]!
  }
  type Mutation {
    createSport(name: String, image:String!, type: String): Sport!
  }
`;