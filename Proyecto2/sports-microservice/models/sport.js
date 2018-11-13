module.exports = (sequelize, DataTypes) => {
  const Sport = sequelize.define('sport', {
    name: {
      type: DataTypes.STRING,
      allowNull: false
    },
    image: {
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
  return Sport;
}