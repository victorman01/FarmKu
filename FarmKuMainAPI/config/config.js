import * as dotenv from 'dotenv';
dotenv.config();
let env = process.env;

env.MYSQL_DB = env.MYSQL_DB + '_' + env.ENV_MODE.toLowerCase();

// Set up for the app
const PROTOCOL = env.APP_PROTOCOL || 'http';
const HOST = env.APP_HOST || 'localhost';
const PORT = env.APP_PORT || 5000;
env.URL = `${PROTOCOL}://${HOST}:${PORT}/`;

export default env;
