import { v4 as uuidv4 } from 'uuid';
import { Plant } from '../models/plantModel.js';
import fs from 'fs';
import env from '../config/config.js';

/**
 * --------
 * getPlants
 * --------
 */
export const getPlants = async (req, res) => {
  try {
    // Initialize an empty object to store the filter conditions for the query
    let where = {};

    // For all other query parameters, add a filter condition to search for users with a matching field value
    for (let key in req.query) {
      if (req.query.hasOwnProperty(key)) {
        where[key] = req.query[key];
      }
    }
    // Use the Sequelize `count` method to calculate the total number of users that match the filter conditions
    const count = await Plant.count({
      where: where,
    });

    // Find all the Plants with the given where conditions
    const plants = await Plant.findAll({
      where: where,
    });
    if (plants.length < 1) {
      return res.status(404).send({
        error: 'Plants not found',
      });
    }
    // Loop through each object in the plants array and add the new property
    const plantsWithUrls = plants.map((plant) => {
      return {
        ...plant.dataValues,
        image: {
          name: plant.dataValues.image,
          url: encodeURI(`${env.URL}${fileDirectory}${plant.dataValues.image}`),
        },
      };
    });
    // Return a 200 status code with the list of Plants
    return res.status(200).send({
      count: count,
      data: plantsWithUrls,
    });
  } catch (error) {
    // If there is an error, return a 500 status code with an error message
    return res.status(500).send({
      error: 'Error retrieving plants',
      message: `${error.message}`,
    });
  }
};

/**
 * --------
 * getPlant
 * --------
 */
export const getPlant = async (req, res) => {
  // Check if an id was provided in the request parameters
  if (!req.params.id) return res.status(400).send({ error: 'Plant ID was not provided' });
  try {
    // Try to find one Plant with the provided id
    const plant = await Plant.findOne({ where: { id: req.params.id } });

    if (!plant) {
      return res.status(404).send({ error: 'Plant not found' });
    }
    console.log(plant);

    const plantWithUrl = {
      ...plant.dataValues,
      image: {
        name: plant.dataValues.image,
        url: encodeURI(`${env.URL}${fileDirectory}${plant.dataValues.image}`),
      },
    };

    return res.status(200).send(plantWithUrl);
  } catch (err) {
    return res.status(500).send({ error: 'Error retrieving Plant' });
  }
};

/**
 * ----------
 * createPlant
 * ----------
 */
export const createPlant = async (req, res) => {
  // Get body request
  const { name } = req.body;
  const file = req.file;
  try {
    // Generate filename
    const filename = generateFilename(name);
    const fileExtension = file.originalname.split('.').pop();

    // Check if the uploaded file type is allowed
    if (!fileTypes.includes(file.mimetype)) {
      return res.status(400).send({ error: 'Invalid file type' });
    }

    // Rename file and move to new folder
    const oldPath = file.path;
    const newPath = `${fileDirectory}${filename}.${fileExtension}`;

    const image = `${filename}.${fileExtension}`;

    const ID = uuidv4().substring(0, 11);

    // Create plant in the database
    await Plant.create({
      id: ID,
      name: name,
      image: image,
    });

    // Move uploaded file from temporary location to new folder
    fs.copyFile(oldPath, newPath, (err) => {
      if (err) {
        // Handle error saving file
        return res.status(500).send({ error: 'Error saving file' });
      }

      // Delete the temporary file
      fs.unlink(oldPath, (err) => {
        if (err) {
          // Handle error deleting temporary file
          return res.status(500).send({ error: 'Error deleting temporary file' });
        }

        // File saved successfully
        return res.status(201).send({ message: 'Plant created successfully', id: `${ID}` });
      });
    });
  } catch (error) {
    console.error(error);
    return res.status(500).send({ error: 'Error updating plant' });
  }
};

/**
 * ----------
 * updatePlant
 * ----------
 */
export const updatePlant = async (req, res) => {
  // Get body request
  const { name } = req.body;
  const { id } = req.params;
  const file = req.file;
  try {
    const plant = await Plant.findOne({ where: { id: id } });

    if (!plant) {
      return res.status(404).send({ error: 'Plant not found' });
    }

    if (file) {
      // Generate filename
      const filename = generateFilename(name);
      const fileExtension = file.originalname.split('.').pop();

      // Check if the uploaded file type is allowed
      if (!fileTypes.includes(file.mimetype)) {
        return res.status(400).send({ error: 'Invalid file type' });
      }

      // Rename file and move to new folder
      const oldPath = `${fileDirectory}${plant.image}`;
      const newPath = `${fileDirectory}${filename}.${fileExtension}`;

      const image = `${filename}.${fileExtension}`;

      fs.unlink(oldPath, (err) => {
        if (err) {
          // Handle error deleting temporary file
          console.error(err);
        }
        fs.copyFileSync(file.path, newPath);
        // Update the plant in the database
        plant.update({
          name: name,
          image: image,
        });
        return res.status(200).send({ message: 'Plant updated successfully' });
      });
    } else {
      // Update the plant in the database
      await plant.update({
        name: name,
      });

      // Plant updated successfully
      return res.status(200).send({ message: 'Plant updated successfully' });
    }
  } catch (error) {
    console.error(error);
    return res.status(500).send({ error: 'Error updating plant' });
  }
};

/**
 * -----------
 * deletePlant
 * -----------
 */
export const deletePlant = async (req, res) => {
  const id = req.params.id;

  try {
    const plant = await Plant.findOne({ where: { id } });

    if (!plant) {
      return res.status(404).send({ error: 'Plant not found' });
    }

    // Destroy plant
    await plant.destroy({ where: { id } });
    // Unlink plant.image if it exists
    if (plant.image) {
      const filePath = `${fileDirectory}${plant.image}`;

      fs.unlink(filePath, (err) => {
        if (err) {
          console.error(err);
        }
      });
    }

    return res.send({ message: 'Plant deleted' });
  } catch (error) {
    console.error(error);
    return res.status(500).send({ error: 'Error deleting Plant' });
  }
};

// Generate filename
function generateFilename(name) {
  const timestamp = new Date().toISOString().replace(/:/g, '-');
  const uuid = uuidv4().substring(0, 6);
  return `${name}-${timestamp}-${uuid}`;
}

// This string represents the directory path where uploaded plant files can be stored.
const fileDirectory = 'public/uploads/plant/';

// Define the file types that are allowed to be uploaded
const fileTypes = ['image/jpeg', 'image/png', 'image/svg+xml'];
