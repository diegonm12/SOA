export default {
    Query: {
        allSports: (parent, args, { db }, info) => db.sport.findAll()
    },
    Mutation: {
        createSport: (parent, { name, image, type }, { db }, info) =>
        db.sport.create({
          name: name,
          image: image,
          type: type
        })
    }
  };
