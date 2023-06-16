import { v4 as uuidv4 } from 'uuid';
import { Variety } from '../models/varietyModel.js';
import fs from 'fs';
import env from '../config/config.js';
import { Plant } from '../models/plantModel.js';

/**
 * --------
 * getVarieties
 * --------
 */
export const getVarieties = async (req, res) => {
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
    const count = await Variety.count({
      where: where,
    });

    // Find all the Varieties with the given where conditions
    const varieties = await Variety.findAll({
      where: where,
    });
    if (varieties.length < 1) {
      return res.status(404).send({
        error: 'Varieties not found',
      });
    }
    varieties.forEach((variety) => {
      variety.optimal_condition = JSON.parse(variety.optimal_condition);
    });

    const updatedVarieties = await Promise.all(
      varieties.map(async (variety) => {
        let plant = await Plant.findOne({ where: { id: variety.plant_id }, attributes: ['id', 'name'] });

        variety = {
          id: variety.id,
          name: variety.name,
          optimal_condition: variety.optimal_condition,
          image: variety.image,
          plant,
          created_at: variety.created_at,
          updated_at: variety.updated_at,
        };

        return variety;
      })
    );

    // Loop through each object in the varieties array and add the new property
    const varietiesWithUrls = updatedVarieties.map((variety) => {
      return {
        ...variety,
        image: {
          name: variety.image,
          url: encodeURI(`${env.URL}${fileDirectory}${variety.image}`),
        },
      };
    });
    // Return a 200 status code with the list of Varieties
    return res.status(200).send({
      count: count,
      data: varietiesWithUrls,
    });
  } catch (error) {
    // If there is an error, return a 500 status code with an error message
    return res.status(500).send({
      error: 'Error retrieving varieties',
      message: `${error.message}`,
    });
  }
};

/**
 * --------
 * getVariety
 * --------
 */
export const getVariety = async (req, res) => {
  // Check if an id was provided in the request parameters
  if (!req.params.id) return res.status(400).send({ error: 'Variety ID was not provided' });
  try {
    // Try to find one Variety with the provided id
    let variety = await Variety.findOne({ where: { id: req.params.id } });

    if (!variety) {
      return res.status(404).send({ error: 'Variety not found' });
    }

    variety.optimal_condition = JSON.parse(variety.optimal_condition);

    const plant = await Plant.findOne({ where: { id: variety.plant_id }, attributes: ['id', 'name'] });
    variety = {
      id: variety.id,
      name: variety.name,
      optimal_condition: variety.optimal_condition,
      image: variety.image,
      plant,
      created_at: variety.created_at,
      updated_at: variety.updated_at,
    };
    const varietyWithUrl = {
      ...variety,
      image: {
        name: variety.image,
        url: encodeURI(`${env.URL}${fileDirectory}${variety.image}`),
      },
    };

    return res.status(200).send(varietyWithUrl);
  } catch (err) {
    console.log(err);
    return res.status(500).send({ error: 'Error retrieving Variety' });
  }
};

/**
 * ----------
 * createVariety
 * ----------
 */
export const createVariety = async (req, res) => {
  // Get body request
  const { plant_id, name, optimal_condition } = req.body;
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

    // Create variety in the database
    await Variety.create({
      id: ID,
      plant_id: plant_id,
      name: name,
      optimal_condition: JSON.parse(optimal_condition),
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
        return res.status(201).send({ message: 'Variety created successfully', id: `${ID}` });
      });
    });
  } catch (error) {
    console.error(error);
    return res.status(500).send({ error: 'Error creating variety' });
  }
};

/**
 * ----------
 * updateVariety
 * ----------
 */
export const updateVariety = async (req, res) => {
  // Get body request
  const { plant_id, name, optimal_condition } = req.body;
  const { id } = req.params;
  const file = req.file;
  try {
    const plant = await Plant.findOne({ where: { id: plant_id } });
    if (!plant) return res.status(404).send({ error: 'Plant not found' });

    const variety = await Variety.findOne({ where: { id: id } });

    if (!variety) {
      return res.status(404).send({ error: 'Variety not found' });
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
      const oldPath = `${fileDirectory}${variety.image}`;
      const newPath = `${fileDirectory}${filename}.${fileExtension}`;

      const image = `${filename}.${fileExtension}`;

      fs.unlink(oldPath, (err) => {
        if (err) {
          // Handle error deleting temporary file
          console.error(err);
        }
        fs.copyFileSync(file.path, newPath);
        // Update the variety in the database
        variety.update({
          plant_id: plant_id,
          name: name,
          optimal_condition: JSON.parse(optimal_condition),
          image: image,
        });
        return res.status(200).send({ message: 'Variety updated successfully' });
      });
    } else {
      // Update the variety in the database
      await variety.update({
        plant_id: plant_id,
        name: name,
        optimal_condition: JSON.parse(optimal_condition),
      });

      // Variety updated successfully
      return res.status(200).send({ message: 'Variety updated successfully' });
    }
  } catch (error) {
    console.error(error);
    return res.status(500).send({ error: 'Error updating variety' });
  }
};

/**
 * -----------
 * deleteVariety
 * -----------
 */
export const deleteVariety = async (req, res) => {
  const id = req.params.id;

  try {
    const variety = await Variety.findOne({ where: { id } });

    if (!variety) {
      return res.status(404).send({ error: 'Variety not found' });
    }

    // Unlink variety.image if it exists
    if (variety.image) {
      const filePath = `${fileDirectory}${variety.image}`;

      fs.unlink(filePath, (err) => {
        if (err) {
          console.error(err);
        }
      });
    }
    // Destroy variety
    await variety.destroy({ where: { id } });

    return res.send({ message: 'Variety deleted' });
  } catch (error) {
    console.error(error);
    return res.status(500).send({ error: 'Error deleting Variety' });
  }
};

// Generate filename
function generateFilename(name) {
  const timestamp = new Date().toISOString().replace(/:/g, '-');
  const uuid = uuidv4().substring(0, 6);
  return `${name}-${timestamp}-${uuid}`;
}

// This string represents the directory path where uploaded variety files can be stored.
const fileDirectory = 'public/uploads/variety/';

// Define the file types that are allowed to be uploaded
const fileTypes = ['image/jpeg', 'image/png', 'image/svg+xml'];
