const { Sequelize } = require('sequelize');

// Konfigurasi koneksi ke MySQL
const sequelize = new Sequelize('farmku_collection', 'farmku', '@g|}T,rB?$){H)jX', {
  host: '34.101.213.242',
  port: 3306,
  dialect: 'mysql'
});

// Ekspor objek sequelize dan fungsi testConnection
module.exports = { sequelize };