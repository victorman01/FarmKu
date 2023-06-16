import { Sequelize } from 'sequelize';
import mongoose from 'mongoose';

// Import the function to get env
import env from './config.js';

/**
 * --------------------
 * Connect to MongoDB |
 * --------------------
 */
mongoose.set('strictQuery', true);
export const mongooseDB = mongoose.connect(env.MONGODB_URL, { useNewUrlParser: true, useUnifiedTopology: true });

/**
 * ------------------
 * Check Database
 * ------------------
 */
const sequelize = new Sequelize(`mysql://${env.MYSQL_USER}:${env.MYSQL_PASSWORD}@${env.MYSQL_HOST}:${env.MYSQL_PORT}`);

export const checkDb = async () => {
  try {
    await sequelize.authenticate();
    console.log('Connection has been established successfully.');

    const hasDatabase = await sequelize.query(`SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '${env.MYSQL_DB}'`);

    if (hasDatabase[0].length === 0) {
      await sequelize.query(`CREATE DATABASE ${env.MYSQL_DB}`);
      console.log('Database created!');
    } else {
      console.log('Database already exists.');
    }
  } catch (error) {
    console.error('Unable to connect to the database:', error);
  } finally {
    await sequelize.close();
  }
};

/**
 * ------------------
 * Connect to MySQL
 * ------------------
 */
export const mysqlDB = new Sequelize(env.MYSQL_DB, env.MYSQL_USER, env.MYSQL_PASSWORD, {
  host: env.MYSQL_HOST,
  port: env.MYSQL_PORT,
  dialect: 'mysql',
  // logging: env.MYSQL_LOGGING,
});
