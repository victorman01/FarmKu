// Import required dependencies
import express from 'express';
import bodyParser from 'body-parser';
import cors from 'cors';
import mongoose from 'mongoose';

// import config functions from ./config
import env from './config/config.js';
import { mysqlDB, checkDb } from './config/db.js';

// import utilities from ./utils
import { syncModels } from './utils/syncModels.js';
import { checkApiKey } from './utils/checkApiKey.js';
import { checkForNull } from './utils/checkNull.js';

// Import from routes
import userRoutes from './routes/user.js';
import landRoutes from './routes/land.js';
import deviceRoutes from './routes/device.js';
import measurementRoutes from './routes/measurement.js';
import plantRoutes from './routes/plant.js';
import varietyRoutes from './routes/variety.js';
import cultivationRoutes from './routes/cultivation.js';
import plantProblemRoutes from './routes/plantProblem.js';
import authRoutes from './routes/auth.js';
import addressRoutes from './routes/address.js';
import recordRoutes from './routes/record.js';

// Import from controllers
import { getFile } from './controllers/public.js';

// Create Express app
const app = express();

/**
 * ==================================================
 *                Public Access File                |
 * ==================================================
 */
app.get('/', (req, res) => {
    return res.send(`Land Monitoring API running on: <a href="${env.URL}">${env.URL}</a>`);
});
app.get('/public/:type/:dir/:filename', getFile);


/**
 * ==================================================
 *                  Middleware                      |
 * ==================================================
 */

// Apply body-parser middleware
app.use(bodyParser.json({ extended: true }));
app.use(bodyParser.urlencoded({ extended: true }));

// Apply CORS middleware
app.use(cors());

//without header api-key for from-device
app.use('/record', recordRoutes);

// Middleware untuk menambahkan semua middleware ke setiap permintaan
app.use(checkApiKey);
app.use(checkForNull);

/**
 * ==================================================
 *                  Routes management               |
 * ==================================================
 */

// Use {ep} routes for '/{ep}' endpoint
app.use('/user', userRoutes);
app.use('/land', landRoutes);
app.use('/device', deviceRoutes);
app.use('/measurement', measurementRoutes);
app.use('/plant', plantRoutes);
app.use('/variety', varietyRoutes);
app.use('/cultivation', cultivationRoutes);
app.use('/plant-problem', plantProblemRoutes);
app.use('/auth', authRoutes);
app.use('/address', addressRoutes);



/**
 * ==================================================
 *             Prepare for Starting App             |
 * ==================================================
 */
// Counter for database connections
let dbConnections = 0;

/**
 * --------------------
 * Connect to MongoDB |
 * --------------------
 */
mongoose.set('strictQuery', true);
const mongooseDB = mongoose.connect(env.MONGODB_URL, { useNewUrlParser: true, useUnifiedTopology: true });

mongooseDB
    .then(() => {
        console.log('Connected to MongoDB');
        dbConnections += 1;
        startApp();
    })
    .catch((error) => console.log(error.message));

/**
 * ------------------
 * Connect to MySQL |
 * ------------------
 */
//checkDb();
mysqlDB
    .authenticate()
    .then(() => {
        console.log('Connected to MySQL');
        dbConnections += 1;
        startApp();
    })
    .catch((error) => console.log(error.message));

/**
 * -------------------
 * Start Application |
 * -------------------
 * Start the app if both database connections are established
 */
const startApp = async() => {
    try {
        if (dbConnections === 2) {
            // Sync all Sequelize models
            // if (env.SYNC_MODELS == 'TRUE') await syncModels();;
            // Start the app
            app.listen(env.APP_PORT, () => {
                console.log(`=========================================================`);
                console.log(`| Land Monitoring API running on: ${env.URL} |`);
                console.log(`=========================================================`);
            });
        }
    } catch (error) {
        console.error('Unable to sync models:', error);
    }
};