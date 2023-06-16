import { mysqlDB } from '../config/db.js';
import models from '../models/index.js';

export const syncModels = async () => {
  try {
    await mysqlDB.authenticate();
    console.log('Connection to the database has been established successfully.');

    /**
     * -------
     * DANGER |
     * -------
     * Don't set sync({force:true})
     *
     * That will be drop table and re-create new table from schema
     * And the data that have created before will be lost
     */
    // Get all model names
    const modelNames = Object.keys(models);

    /**
     * ! Danger
     * Don't change force:false -> force:true
     * It will remove data from database
     */
    // Loop through and sync each model
    modelNames.forEach((modelName) => {
      const model = models[modelName];
      model.sync({ force: false, alter: true, match: /_test$/ }).then(() => {
        console.log(`Synced table for model: ${modelName}`);
      });
    });

    console.log('All models were synchronized successfully.');
  } catch (error) {
    console.error('Unable to connect to the database:', error);
  }
};
