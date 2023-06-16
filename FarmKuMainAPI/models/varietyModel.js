import { Sequelize, DataTypes } from 'sequelize';
import { mysqlDB } from '../config/db.js';

export const Variety = mysqlDB.define(
  'variety',
  {
    id: {
      type: DataTypes.STRING(11),
      primaryKey: true,
      allowNull: false,
    },
    plant_id: {
      type: DataTypes.STRING(11),
      allowNull: false,
    },
    name: {
      type: DataTypes.STRING(50),
      allowNull: false,
    },
    optimal_condition: {
      type: DataTypes.JSON,
      allowNull: true,
    },
    image: {
      type: DataTypes.STRING,
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
      beforeCreate: (variety, options) => {
        variety.created_at = new Date();
      },
      beforeUpdate: (variety, options) => {
        variety.updated_at = new Date();
      },
    },
  }
);
