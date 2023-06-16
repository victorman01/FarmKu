import { Sequelize, DataTypes } from 'sequelize';
import { mysqlDB } from '../config/db.js';

export const PlantProblem = mysqlDB.define(
  'plant_problem',
  {
    id: {
      type: DataTypes.STRING(11),
      primaryKey: true,
    },
    plant_id: {
      type: DataTypes.STRING(11),
    },
    name: {
      type: DataTypes.STRING(50),
      allowNull: false,
    },
    type: {
      type: DataTypes.ENUM('DISEASE', 'PEST'),
      allowNull: false,
    },
    cause: {
      type: DataTypes.TEXT,
      allowNull: true,
    },
    description: {
      type: DataTypes.TEXT,
      allowNull: true,
    },
    symptom: {
      type: DataTypes.TEXT,
      allowNull: true,
    },
    treatment: {
      type: DataTypes.TEXT,
      allowNull: true,
    },
    image: {
      type: DataTypes.STRING,
      allowNull: true,
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
      beforeCreate: (plant_problem, options) => {
        plant_problem.created_at = new Date();
      },
      beforeUpdate: (plant_problem, options) => {
        plant_problem.updated_at = new Date();
      },
    },
  }
);
