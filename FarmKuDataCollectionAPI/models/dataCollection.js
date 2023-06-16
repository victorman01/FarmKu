const { DataTypes } = require('sequelize');
const { sequelize } = require('../config/dbConfig.js');

const DataCollection = sequelize.define('data_collection', {
  id: {
    type: DataTypes.INTEGER,
    autoIncrement: true,
    primaryKey: true
  },
  nama_varietas: {
    type: DataTypes.STRING(255),
    allowNull: false
  },
  N: {
    type: DataTypes.FLOAT,
    allowNull: false
  },
  P: {
    type: DataTypes.FLOAT,
    allowNull: false
  },
  K: {
    type: DataTypes.FLOAT,
    allowNull: false
  },
  PH: {
    type: DataTypes.FLOAT,
    allowNull: false
  },
  Longitude: {
    type: DataTypes.FLOAT,
    allowNull: false
  },
  Latitude: {
    type: DataTypes.FLOAT,
    allowNull: false
  },
  Image: {
    type: DataTypes.JSON,
    allowNull: false,
  },
  Description: {
    type: DataTypes.TEXT,
    allowNull: false
  }
}, {
  // tambahkan opsi 'freezeTableName' dengan nilai true
  freezeTableName: true,
  timestamps: false // tambahkan opsi 'timestamps' dengan nilai false
});

module.exports = DataCollection;
