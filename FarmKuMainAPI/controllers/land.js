import { v4 as uuidv4 } from 'uuid';
import { Address, getAddressById } from '../models/addressModel.js';
import { Land } from '../models/landModel.js';
import { User } from '../models/userModel.js';
import { Variety } from '../models/varietyModel.js';
/**
 * --------
 * getLands
 * --------
 */
export const getLands = async (req, res) => {
  try {
    // Initialize an empty object to store the filter conditions for the query
    let where = {};

    if (req.query.address) {
      const addressExists = await Address.findAll({
        where: {
          id: { [Op.like]: `${req.query.address}%` },
        },
      });

      if (addressExists < 1) {
        return res.status(400).send({ error: 'The address you are trying to get does not exist' });
      } else {
        // Add the filter condition to search for users whose address starts with the given value
        where = {
          ...where,
          address_id: { [Op.like]: `${req.query.address}%` },
        };
      }
    }
    // For all other query parameters, add a filter condition to search for users with a matching field value
    for (let key in req.query) {
      if (req.query.hasOwnProperty(key) && !req.query.address) {
        where[key] = req.query[key];
      }
    }

    // Use the Sequelize `count` method to calculate the total number of users that match the filter conditions
    const count = await Land.count({
      where: where,
    });

    // Find all the lands with the given where conditions
    const lands = await Land.findAll({
      where: where,
    });

    if (lands.length < 1) {
      return res.status(404).send({
        error: 'Lands not found',
      });
    }

    lands.forEach((land) => {
      land.location = JSON.parse(land.location);
    });

    const landWithAddress = await Promise.all(
      lands.map(async (land) => {
        const address = await getAddressById(land.address_id);
        const user = await User.findOne({ where: { id: land.user_id }, attributes: ['id', 'name'] });
        return {
          id: land.id,
          name: land.name,
          location: land.location,
          user,
          address: {
            id: land.address_id,
            ...address,
          },
          created_at: land.created_at,
          updated_at: land.updated_at,
        };
      })
    );

    // Return a 200 status code with the list of lands
    return res.status(200).send({
      count: count,
      data: landWithAddress,
    });
  } catch (error) {
    // If there is an error, return a 500 status code with an error message
    return res.status(500).send({
      error: 'Error retrieving lands',
      message: `${error.message}`,
    });
  }
};

/**
 * --------
 * getLand
 * --------
 */
export const getLand = async (req, res) => {
  try {
    let land = await Land.findOne({ where: { id: req.params.id } });
    if (!land) {
      return res.status(404).send({ error: 'Land not found' });
    }
    land = Object.assign(land.dataValues, { address: await getAddressById(land.address_id) });
    const user = await User.findOne({ where: { id: land.user_id }, attributes: ['id', 'name'] });
    land.location = JSON.parse(land.location);
    land = {
      id: land.id,
      name: land.name,
      location: land.location,
      address: {
        id: land.address_id,
        ...land.address,
      },
      created_at: land.created_at,
      updated_at: land.updated_at,
    };
    return res.status(200).send(land);
  } catch (err) {
    console.log(err);
    return res.status(500).send({ error: 'Error retrieving land' });
  }
};

/**
 * ----------
 * createLand
 * ----------
 */
export const createLand = async (req, res) => {
  const { name, user_id, variety_id, area, address_id, location } = req.body;

  if (!name || !user_id || !variety_id || !area || !address_id || !location) {
    return res.status(400).send({ error: 'Missing fields in request' });
  }

  try {
    const ID = uuidv4().substring(0, 11);
    const land = {
      id: ID,
      name,
      user_id,
      variety_id,
      area,
      address_id,
      location: location,
    };

    // Make sure the variety and address exists before adding the Land
    const varietyExists = await Variety.findOne({ where: { id: variety_id } });
    const addressExists = await Address.findOne({ where: { id: address_id } });

    if (!varietyExists || !addressExists) {
      return res.status(400).send({ error: 'Invalid variety or address id' });
    }

    await Land.create(land);
    return res.status(201).send({ message: 'Land created successfully', id: `${ID}` });
  } catch (error) {
    return res.status(500).send({ error: 'Error creating land' });
  }
};

/**
 * ----------
 * updateLand
 * ----------
 */
export const updateLand = async (req, res) => {
  const { id } = req.params;
  const { name, variety_id, area, address, location } = req.body;

  try {
    const land = await Land.update({ name, variety_id, area, address, location }, { where: { id } });

    if (land[0] === 0) {
      return res.status(404).send({ error: 'Land not found' });
    } else {
      return res.status(200).send({ message: 'Land updated successfully' });
    }
  } catch (error) {
    return res.status(500).send({ error: 'Error updating land' });
  }
};

/**
 * -----------
 * deleteLand
 * -----------
 */
export const deleteLand = async (req, res) => {
  const id = req.params.id;

  try {
    const land = await Land.findOne({ where: { id } });

    if (!land) {
      return res.status(404).send({ error: 'Land not found' });
    }

    await Land.destroy({ where: { id } });

    return res.send({ message: 'Land deleted' });
  } catch (error) {
    console.error(error);
    return res.status(500).send({ error: 'Error deleting land' });
  }
};
