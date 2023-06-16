import { Sequelize, DataTypes } from 'sequelize';
import { mysqlDB } from '../config/db.js';

export const Measurement = mysqlDB.define(
  'measurement',
  {
    id: {
      type: DataTypes.STRING(11),
      primaryKey: true,
    },
    land_id: {
      type: DataTypes.STRING(11),
    },
    device_id: {
      type: DataTypes.STRING(11),
    },
    start: {
      type: DataTypes.DATE,
    },
    end: {
      type: DataTypes.DATE,
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
      beforeCreate: (measurement, options) => {
        measurement.created_at = new Date();
      },
      beforeUpdate: (measurement, options) => {
        measurement.updated_at = new Date();
      },
    },
  }
);
