import { Sequelize, DataTypes } from 'sequelize';
import { mysqlDB } from '../config/db.js';
import { Address } from './addressModel.js';

export const User = mysqlDB.define(
  'user',
  {
    id: {
      type: DataTypes.UUID,
      defaultValue: DataTypes.UUIDV4,
      primaryKey: true,
    },
    name: {
      type: DataTypes.STRING(50),
    },
    email: {
      type: DataTypes.STRING(50),
      unique: true,
    },
    password: {
      type: DataTypes.STRING,
    },
    phone_number: {
      type: DataTypes.STRING(20),
      unique: true,
    },
    address_id: {
      type: DataTypes.STRING(13),
    },
    role: {
      type: DataTypes.ENUM,
      defaultValue: 'USER',
      values: ['ADMIN', 'USER'],
    },
    reset_token: {
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
    underscored: true,
    freezeTableName: true,
    timestamps: false,
    hooks: {
      beforeCreate: (user, options) => {
        user.created_at = new Date();
      },
      beforeUpdate: (user, options) => {
        user.updated_at = new Date();
      },
    },
  }
);

User.belongsTo(Address, { foreignKey: 'address_id' });
