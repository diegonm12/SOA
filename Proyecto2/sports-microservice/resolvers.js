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
            }),
        updateSport: (parent, { image, type, name }, { db }, info) =>
            db.sport.update({
                image: image,
                type: type
            },
                {
                    where: {
                        name: name
                    }
                }),
        deleteSport: (parent, { name }, { db }, info) =>
            db.sport.destroy({
                where: {
                    name: name
                }
            })
    }
};
