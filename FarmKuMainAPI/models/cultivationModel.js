import { Sequelize, DataTypes } from 'sequelize';
import { mysqlDB } from '../config/db.js';

export const Cultivation = mysqlDB.define(
  'cultivation',
  {
    id: {
      type: DataTypes.STRING(11),
      primaryKey: true,
    },
    plant_id: {
      type: DataTypes.STRING(11),
      allowNull: false,
    },
    content: {
      type: DataTypes.JSON,
      defaultValue: null,
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
      beforeCreate: (cultivation, options) => {
        cultivation.created_at = new Date();
      },
      beforeUpdate: (cultivation, options) => {
        cultivation.updated_at = new Date();
      },
    },
  }
);
