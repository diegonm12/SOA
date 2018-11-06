export default `
    type News{
        _id: String!
        sport: String!
        title: String!
        content: String!
        important: String!
        image: String!
        type: String!
    }
    type Query{
        allNews: [News!]!
    }
    type Mutation{
        createNews(sport: String!,title: String!
            content: String!, important: String!
            image:String!, type:String!): News!
    }
`;