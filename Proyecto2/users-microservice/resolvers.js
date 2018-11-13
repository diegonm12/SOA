export default {
    Query: {
        allUsers: (parent, args, { db }, info) => db.user.findAll()
    },
    Mutation: {
        createUser: (parent, { name, email, password, profilePicture, sessionInit, type }, { db }, info) =>
        db.user.create({
          name: name,
          email: email,
          password: password,
          profilePicture: profilePicture,
          sessionInit: sessionInit,
          type: type
        })
    }
  };