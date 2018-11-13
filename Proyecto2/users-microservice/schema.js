export default `
  type User {
    name: String!
    email: String!
    password: String!
    profilePicture: String!
    sessionInit: String!
    type: String!
  }
  
  type Query {
    allUsers: [User!]!
  }
  type Mutation {
    createUser(name: String, email:String!, password: String, profilePicture:String, sessionInit:String, type:String ): User!
    updateUser(email: String!, name: String, password:String!): Boolean
    deleteUser(email: String!):Boolean
  }
`;