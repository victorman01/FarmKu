import { Sequelize, DataTypes } from 'sequelize';
import { mysqlDB } from '../config/db.js';

export const Land = mysqlDB.define(
  'land',
  {
    id: {
      type: DataTypes.STRING(11),
      primaryKey: true,
    },
    name: {
      type: DataTypes.STRING(50),
    },
    user_id: {
      type: DataTypes.STRING(36),
    },
    variety_id: {
      type: DataTypes.STRING(11),
    },
    area: {
      type: DataTypes.INTEGER,
    },
    address_id: {
      type: DataTypes.STRING(13),
    },
    location: {
      type: DataTypes.JSON,
    },
    created_at: {
      type: DataTypes.DATE,
    },
    updated_at: {
      type: DataTypes.DATE,
      defaultValue: Sequelize.literal('CURRENT_TIMESTAMP'),
    },
  },
  {
    underscored: true,
    freezeTableName: true,
    timestamps: false,
    hooks: {
      beforeCreate: (land, options) => {
        land.created_at = new Date();
      },
      beforeUpdate: (land, options) => {
        land.updated_at = new Date();
      },
    },
  }
);
