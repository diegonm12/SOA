module.exports = (sequelize, DataTypes) => {
    const User = sequelize.define('user', {
        name: {
          type: DataTypes.STRING,
          allowNull: false
        },
        email: {
          type: DataTypes.STRING,
          allowNull: false
        },
        password: {
          type: DataTypes.STRING,
          allowNull: false
        },
        profilePicture: {
          type: DataTypes.STRING,
          allowNull: false
        },
        sessionInit: {
          type: DataTypes.STRING,
          allowNull: false
        },
        type: {
            type: DataTypes.STRING,
            allowNull: false
          }
      },
      {
        freezeTableName: true,
      }
    );
  /*
    Sport.associate = (models) => {
        Sport.belongsTo(models.author);
    };
  */
    return User;
  }