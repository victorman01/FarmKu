import { DataTypes } from 'sequelize';
import { mysqlDB } from '../config/db.js';

export const Address = mysqlDB.define(
  'address',
  {
    id: {
      type: DataTypes.STRING(13),
      primaryKey: true,
    },
    name: {
      type: DataTypes.STRING(100),
    },
  },
  {
    freezeTableName: true,
    timestamps: false,
  }
);

export const getAddressById = async (address_id) => {
  const addressLength = [2, 5, 8, 13];
  const province = await Address.findByPk(address_id.substring(0, addressLength[0]));
  const district = await Address.findByPk(address_id.substring(0, addressLength[1]));
  const regency = await Address.findByPk(address_id.substring(0, addressLength[2]));
  const village = await Address.findByPk(address_id.substring(0, addressLength[3]));

  return {
    province: province.name,
    district: district.name,
    regency: regency.name,
    village: village.name,
  };
};
