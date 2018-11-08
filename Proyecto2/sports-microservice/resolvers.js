export default {
    Query: {
        allSports: (parent, args, { db }, info) => db.sport.findAll()
    },
    Mutation: {
        createSport: (parent, { name, image, type }, { db }, info) =>
        db.post.create({
          name: name,
          image: image,
          type: type
        })
    }
  };