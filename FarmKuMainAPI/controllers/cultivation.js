import { v4 as uuidv4 } from 'uuid';
import { Cultivation } from '../models/cultivationModel.js';
import fs from 'fs';
import env from '../config/config.js';
import { Plant } from '../models/plantModel.js';

/**
 * --------
 * getCultivations
 * --------
 */
export const getCultivations = async (req, res) => {
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
    const count = await Cultivation.count({
      where: where,
    });

    // Find all the Cultivations with the given where conditions
    const cultivations = await Cultivation.findAll({
      where: where,
    });
    if (cultivations.length < 1) {
      return res.status(404).send({
        error: 'Cultivations not found',
      });
    }

    // Map through the data array and replace the plant_id with the plant object
    const updatedCultivations = await Promise.all(
      cultivations.map(async (item) => {
        const plant = await Plant.findOne({
          where: { id: item.plant_id },
          attributes: ['id', 'name'],
        });
        delete item.plant_id;
        return {
          id: item.id,
          plant: {
            id: plant.id,
            name: plant.name,
          },
          content: item.content,
          created_at: item.created_at,
          updated_at: item.updated_at,
        };
      })
    );

    updatedCultivations.forEach((item) => {
      item.content = JSON.parse(item.content);
    });

    // Return a 200 status code with the list of Cultivations
    return res.status(200).send({
      count: count,
      data: updatedCultivations,
    });
  } catch (error) {
    // If there is an error, return a 500 status code with an error message
    return res.status(500).send({
      error: 'Error retrieving cultivations',
      message: `${error.message}`,
    });
  }
};

/**
 * --------
 * getCultivation
 * --------
 */
export const getCultivation = async (req, res) => {
  // Check if an id was provided in the request parameters
  if (!req.params.id) return res.status(400).send({ error: 'Cultivation ID was not provided' });
  try {
    // Try to find one Cultivation with the provided id
    let cultivation = await Cultivation.findOne({ where: { id: req.params.id } });

    if (!cultivation) {
      return res.status(404).send({ error: 'Cultivation not found' });
    }

    let plant = await Plant.findOne({ where: { id: cultivation.plant_id }, attributes: ['id', 'name'] });
    delete cultivation.dataValues.plant_id;
    cultivation = {
      id: cultivation.id,
      plant,
      content: cultivation.content,
      created_at: cultivation.created_at,
      updated_at: cultivation.updated_at,
    };

    cultivation.content = JSON.parse(cultivation.content);

    return res.status(200).send(cultivation);
  } catch (err) {
    console.log(err);
    return res.status(500).send({ error: 'Error retrieving Cultivation' });
  }
};

/**
 * ----------
 * createCultivation
 * ----------
 */
export const createCultivation = async (req, res) => {
  // Get body request
  const { plant_id, content } = req.body;
  try {
    const plant = await Plant.findOne({ where: { id: plant_id } });
    if (!plant) return res.status(404).send({ error: 'Plant not found' });

    const ID = uuidv4().substring(0, 11);

    // Create cultivation in the database
    await Cultivation.create({
      id: ID,
      plant_id: plant_id,
      content: content,
    });
    return res.status(500).send({ message: 'Cultivation created successfully' });
  } catch (error) {
    console.error(error);
    return res.status(500).send({ error: 'Error creating cultivation' });
  }
};

/**
 * ----------
 * updateCultivation
 * ----------
 */
export const updateCultivation = async (req, res) => {
  // Get body request
  const { plant_id, content } = req.body;
  const { id } = req.params;
  try {
    const plant = await Plant.findOne({ where: { id: plant_id } });
    if (!plant) return res.status(404).send({ error: 'Plant not found' });

    const cultivation = await Cultivation.findOne({ where: { id: id } });

    if (!cultivation) {
      return res.status(404).send({ error: 'Cultivation not found' });
    }

    // Update the cultivation in the database
    await cultivation.update({
      plant_id: plant_id,
      content: content,
    });

    // Cultivation updated successfully
    return res.status(200).send({ message: 'Cultivation updated successfully' });
  } catch (error) {
    console.error(error);
    return res.status(500).send({ error: 'Error updating cultivation' });
  }
};

/**
 * -----------
 * deleteCultivation
 * -----------
 */
export const deleteCultivation = async (req, res) => {
  const id = req.params.id;

  try {
    const cultivation = await Cultivation.findOne({ where: { id } });

    if (!cultivation) {
      return res.status(404).send({ error: 'Cultivation not found' });
    }

    // Destroy cultivation
    await cultivation.destroy({ where: { id } });

    return res.send({ message: 'Cultivation deleted' });
  } catch (error) {
    console.error(error);
    return res.status(500).send({ error: 'Error deleting Cultivation' });
  }
};
