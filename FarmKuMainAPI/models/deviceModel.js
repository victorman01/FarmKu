import { Sequelize, DataTypes } from 'sequelize';
import { mysqlDB } from '../config/db.js';

export const Device = mysqlDB.define(
  'device',
  {
    id: {
      type: DataTypes.STRING(11),
      primaryKey: true,
    },
    name: {
      type: DataTypes.STRING(50),
    },
    state: {
      type: DataTypes.ENUM('ON', 'OFF', 'DISABLED'),
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
      beforeCreate: (device, options) => {
        device.created_at = new Date();
      },
      beforeUpdate: (device, options) => {
        device.updated_at = new Date();
      },
    },
  }
);
