import { v4 as uuidv4 } from 'uuid';
import { Device } from '../models/deviceModel.js';
/**
 * --------
 * getDevices
 * --------
 */
export const getDevices = async (req, res) => {
  try {
    // Initialize an empty object to store the filter conditions for the query
    let where = {};

    // For all other query parameters, add a filter condition to search for users with a matching field value
    for (let key in req.query) {
      if (req.query.hasOwnProperty(key) && !req.query.address) {
        where[key] = req.query[key];
      }
    }

    // Use the Sequelize `count` method to calculate the total number of users that match the filter conditions
    const count = await Device.count({
      where: where,
    });

    // Find all the Devices with the given where conditions
    const devices = await Device.findAll({
      where: where,
    });

    if (devices.length < 1) {
      return res.status(404).send({
        error: 'Devices not found',
      });
    }
    // Return a 200 status code with the list of Devices
    return res.status(200).send({
      count: count,
      data: devices,
    });
  } catch (error) {
    // If there is an error, return a 500 status code with an error message
    return res.status(500).send({
      error: 'Error retrieving Devices',
      message: `${error.message}`,
    });
  }
};

/**
 * --------
 * getDevice
 * --------
 */
export const getDevice = async (req, res) => {
  try {
    const device = await Device.findOne({ where: { id: req.params.id } });
    if (!device) {
      return res.status(404).send({ error: 'Device not found' });
    }
    return res.status(200).send(device);
  } catch (err) {
    return res.status(500).send({ error: 'Error retrieving Device' });
  }
};

/**
 * ----------
 * createDevice
 * ----------
 */
export const createDevice = async (req, res) => {
  const { name } = req.body;

  try {
    const ID = uuidv4().substring(0, 11);
    await Device.create({
      id: ID,
      name: name,
    });
    return res.status(201).send({ message: 'Device created successfully', id: `${ID}` });
  } catch (error) {
    return res.status(500).send({ error: 'Error creating Device' });
  }
};

/**
 * ----------
 * updateDevice
 * ----------
 */
export const updateDevice = async (req, res) => {
  const { id } = req.params;
  const { name, state } = req.body;

  try {
    const device = await Device.findOne({ where: { id: id } });

    if (!device) {
      return res.status(404).send({ error: 'Device not found' });
    }

    device.update({
      name: name,
      state: state,
    });
    return res.status(200).send({ message: 'Device updated successfully' });
  } catch (error) {
    return res.status(500).send({ error: 'Error updating Device' });
  }
};

/**
 * -----------
 * deleteDevice
 * -----------
 */
export const deleteDevice = async (req, res) => {
  const id = req.params.id;

  try {
    const device = await Device.findOne({ where: { id } });

    if (!device) {
      return res.status(404).send({ error: 'Device not found' });
    }

    await device.destroy({ where: { id } });

    return res.send({ message: 'Device deleted' });
  } catch (error) {
    console.error(error);
    return res.status(500).send({ error: 'Error deleting Device' });
  }
};
