import { v4 as uuidv4 } from 'uuid';
import { PlantProblem } from '../models/plantProblemModel.js';
import fs from 'fs';
import env from '../config/config.js';
import { Plant } from '../models/plantModel.js';

/**
 * --------
 * getPlantProblems
 * --------
 */
export const getPlantProblems = async (req, res) => {
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
    const count = await PlantProblem.count({
      where: where,
    });

    // Find all the PlantProblems with the given where conditions
    const plantProblems = await PlantProblem.findAll({
      where: where,
    });
    if (plantProblems.length < 1) {
      return res.status(404).send({
        error: 'PlantProblems not found',
      });
    }
    const updatedPlantProblems = await Promise.all(
      plantProblems.map(async (plantProblem) => {
        let plant = await Plant.findOne({ where: { id: plantProblem.plant_id }, attributes: ['id', 'name'] });

        plantProblem = {
          id: plantProblem.id,
          name: plantProblem.name,
          type: plantProblem.type,
          cause: plantProblem.cause,
          description: plantProblem.description,
          symptom: plantProblem.symptom,
          treatment: plantProblem.treatment,
          image: plantProblem.image,
          plant,
          created_at: plantProblem.created_at,
          updated_at: plantProblem.updated_at,
        };

        return plantProblem;
      })
    );

    // Loop through each object in the PlantProblems array and add the new property
    const plantProblemsWithUrls = updatedPlantProblems.map((plantProblem) => {
      return {
        ...plantProblem,
        image: {
          name: plantProblem.image,
          url: encodeURI(`${env.URL}${fileDirectory}${plantProblem.image}`),
        },
      };
    });

    // Return a 200 status code with the list of PlantProblems
    return res.status(200).send({
      count: count,
      data: plantProblemsWithUrls,
    });
  } catch (error) {
    // If there is an error, return a 500 status code with an error message
    return res.status(500).send({
      error: 'Error retrieving PlantProblems',
      message: `${error.message}`,
    });
  }
};

/**
 * --------
 * getPlantProblem
 * --------
 */
export const getPlantProblem = async (req, res) => {
  // Check if an id was provided in the request parameters
  if (!req.params.id) return res.status(400).send({ error: 'PlantProblem ID was not provided' });
  try {
    // Try to find one PlantProblem with the provided id
    let plantProblem = await PlantProblem.findOne({ where: { id: req.params.id } });
    if (!plantProblem) {
      return res.status(404).send({ error: 'PlantProblem not found' });
    }
    const plant = await Plant.findOne({ where: { id: plantProblem.plant_id }, attributes: ['id', 'name'] });
    plantProblem = {
      id: plantProblem.id,
      name: plantProblem.name,
      type: plantProblem.type,
      cause: plantProblem.cause,
      description: plantProblem.description,
      symptom: plantProblem.symptom,
      treatment: plantProblem.treatment,
      image: plantProblem.image,
      plant,
      created_at: plantProblem.created_at,
      updated_at: plantProblem.updated_at,
    };

    const plantProblemWithUrl = {
      ...plantProblem,
      plant,
      image: {
        name: plantProblem.image,
        url: encodeURI(`${env.URL}${fileDirectory}${plantProblem.image}`),
      },
    };

    return res.status(200).send(plantProblemWithUrl);
  } catch (err) {
    console.log(err);
    return res.status(500).send({ error: 'Error retrieving PlantProblem' });
  }
};

/**
 * ----------
 * createPlantProblem
 * ----------
 */
export const createPlantProblem = async (req, res) => {
  // Get body request
  const { plant_id, name, type, cause, description, symptom, treatment } = req.body;
  const file = req.file;
  try {
    const plant = await Plant.findOne({ where: { id: plant_id } });
    if (!plant) return res.status(404).send({ error: 'Plant not found' });

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

    // Create PlantProblem in the database
    await PlantProblem.create({
      id: ID,
      plant_id: plant_id,
      name: name,
      type: type,
      cause: cause,
      description: description,
      symptom: symptom,
      treatment: treatment,
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
        return res.status(201).send({ message: 'PlantProblem created successfully', id: `${ID}` });
      });
    });
  } catch (error) {
    console.error(error);
    return res.status(500).send({ error: 'Error creating PlantProblem' });
  }
};

/**
 * ----------
 * updatePlantProblem
 * ----------
 */
export const updatePlantProblem = async (req, res) => {
  // Get body request
  const { plant_id, name, type, cause, description, symptom, treatment } = req.body;
  const { id } = req.params;
  const file = req.file;
  try {
    const plant = await Plant.findOne({ where: { id: plant_id } });
    if (!plant) return res.status(404).send({ error: 'Plant not found' });

    const plantProblem = await PlantProblem.findOne({ where: { id: id } });

    if (!plantProblem) {
      return res.status(404).send({ error: 'PlantProblem not found' });
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
      const oldPath = `${fileDirectory}${plantProblem.image}`;
      const newPath = `${fileDirectory}${filename}.${fileExtension}`;

      const image = `${filename}.${fileExtension}`;

      fs.unlink(oldPath, (err) => {
        if (err) {
          // Handle error deleting temporary file
          console.error(err);
        }
        fs.copyFileSync(file.path, newPath);
        // Update the PlantProblem in the database
        plantProblem.update({
          plant_id: plant_id,
          name: name,
          type: type,
          cause: cause,
          description: description,
          symptom: symptom,
          treatment: treatment,
          image: image,
        });
        return res.status(200).send({ message: 'PlantProblem updated successfully' });
      });
    } else {
      // Update the PlantProblem in the database
      await plantProblem.update({
        plant_id: plant_id,
        name: name,
        type: type,
        cause: cause,
        description: description,
        symptom: symptom,
        treatment: treatment,
      });

      // PlantProblem updated successfully
      return res.status(200).send({ message: 'PlantProblem updated successfully' });
    }
  } catch (error) {
    console.error(error);
    return res.status(500).send({ error: 'Error updating PlantProblem' });
  }
};

/**
 * -----------
 * deletePlantProblem
 * -----------
 */
export const deletePlantProblem = async (req, res) => {
  const id = req.params.id;

  try {
    const plantProblem = await PlantProblem.findOne({ where: { id } });

    if (!plantProblem) {
      return res.status(404).send({ error: 'PlantProblem not found' });
    }

    // Unlink PlantProblem.image if it exists
    if (plantProblem.image) {
      const filePath = `${fileDirectory}${plantProblem.image}`;

      fs.unlink(filePath, (err) => {
        if (err) {
          console.error(err);
        }
      });
    }
    // Destroy PlantProblem
    await plantProblem.destroy({ where: { id } });

    return res.send({ message: 'PlantProblem deleted' });
  } catch (error) {
    console.error(error);
    return res.status(500).send({ error: 'Error deleting PlantProblem' });
  }
};

// Generate filename
function generateFilename(name) {
  const timestamp = new Date().toISOString().replace(/:/g, '-');
  const uuid = uuidv4().substring(0, 6);
  return `${name}-${timestamp}-${uuid}`;
}

// This string represents the directory path where uploaded PlantProblem files can be stored.
const fileDirectory = 'public/uploads/plant_problem/';

// Define the file types that are allowed to be uploaded
const fileTypes = ['image/jpeg', 'image/png', 'image/svg+xml'];
