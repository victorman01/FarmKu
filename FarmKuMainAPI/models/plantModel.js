import { Sequelize, DataTypes } from 'sequelize';
import { mysqlDB } from '../config/db.js';

export const Plant = mysqlDB.define(
  'plant',
  {
    id: {
      type: DataTypes.STRING(11),
      primaryKey: true,
    },
    name: {
      type: DataTypes.STRING(50),
    },
    image: {
      type: DataTypes.STRING,
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
    freezeTableName: true,
    timestamps: false,
    hooks: {
      beforeCreate: (plant, options) => {
        plant.created_at = new Date();
      },
      beforeUpdate: (plant, options) => {
        plant.updated_at = new Date();
      },
    },
  }
);
