import { v4 as uuidv4 } from 'uuid';
import { Device } from '../models/deviceModel.js';
import { Land } from '../models/landModel.js';
import { Measurement } from '../models/measurementModel.js';
import { User } from '../models/userModel.js';
/**
 * --------
 * getMeasurements
 * --------
 */
export const getMeasurements = async (req, res) => {
  try {
    // Initialize an empty object to store the filter conditions for the query
    let where = {};

    // For all other query parameters, add a filter condition to search for users with a matching field value
    for (let key in req.query) {
      if (req.query.hasOwnProperty(key) && !req.query.address && !req.query.user_id) {
        where[key] = req.query[key];
      }
    }

    // Use the Sequelize `count` method to calculate the total number of users that match the filter conditions
    const count = await Measurement.count({
      where: where,
    });

    // Find all the Measurements with the given where conditions
    let measurements = await Measurement.findAll({
      where: where,
    });

    if (measurements.length < 1) {
      return res.status(404).send({
        error: 'Measurements not found',
      });
    }

    const updatedMeasurements = await Promise.all(
      measurements.map(async (measurement) => {
        let land = await Land.findOne({ where: { id: measurement.land_id }, attributes: ['id', 'name', 'location'] });
        land = {
          id: land.id,
          name: land.name,
          location: JSON.parse(land.location),
        };
        let device = await Device.findOne({ where: { id: measurement.device_id }, attributes: ['id', 'name', 'state'] });

        measurement = {
          id: measurement.id,
          start: measurement.start,
          end: measurement.end,
          land,
          device,
          created_at: measurement.created_at,
          updated_at: measurement.updated_at,
        };

        return measurement;
      })
    );

    // Return a 200 status code with the list of Measurements
    return res.status(200).send({
      count: count,
      data: updatedMeasurements,
    });
  } catch (error) {
    console.log(error);
    // If there is an error, return a 500 status code with an error message
    return res.status(500).send({
      error: 'Error retrieving Measurements',
      message: `${error.message}`,
    });
  }
};

/**
 * --------
 * getMeasurement
 * --------
 */
export const getMeasurement = async (req, res) => {
  try {
    let measurement = await Measurement.findOne({
      where: { id: req.params.id },
    });

    let land = await Land.findOne({ where: { id: measurement.land_id }, attributes: ['id', 'name', 'location'] });
    land = {
      id: land.id,
      name: land.name,
      location: JSON.parse(land.location),
    };
    let device = await Device.findOne({ where: { id: measurement.device_id }, attributes: ['id', 'name', 'state'] });

    measurement = {
      id: measurement.id,
      start: measurement.start,
      end: measurement.end,
      land,
      device,
      created_at: measurement.created_at,
      updated_at: measurement.updated_at,
    };
    if (!measurement) {
      return res.status(404).send({ error: 'Measurement not found' });
    }
    return res.status(200).send(measurement);
  } catch (err) {
    console.log(err);
    return res.status(500).send({ error: 'Error retrieving Measurement' });
  }
};

/**
 * ----------
 * createMeasurement
 * ----------
 */
export const createMeasurement = async (req, res) => {
  const { land_id, user_id } = req.body;

  try {
    const user = await User.findOne({ where: { id: user_id } });
    if (!user) {
      return res.status(404).send({ error: 'User not found' });
    }

    const land = await Land.findOne({ where: { id: land_id } });
    if (!land) {
      return res.status(404).send({ error: 'Land not found' });
    }

    if (land.user_id != user_id) {
      return res.status(401).send({ error: `Land is not owned by you ${user_id}` });
    }

    const ID = uuidv4().substring(0, 11);
    await Measurement.create({
      id: ID,
      land_id: land_id,
    });
    return res.status(201).send({ message: 'Measurement created successfully', id: `${ID}` });
  } catch (error) {
    console.log(error);
    return res.status(500).send({ error: 'Error creating Measurement' });
  }
};

/**
 * ----------
 * updateMeasurement
 * ----------
 */
export const updateMeasurement = async (req, res) => {
  const { id } = req.params;
  const { device_id, condition } = req.body;
  let update = {};
  let connectedDevice;
  try {
    const measurement = await Measurement.findOne({ where: { id: id } });
    if (!measurement) return res.status(404).send({ error: 'Measurement not found' });

    const scannedDevice = await Device.findOne({ where: { id: device_id } });
    if (!scannedDevice) return res.status(404).send({ error: 'Device not found' });

    switch (condition) {
      case 'scan':
        // Scan Device
        if (measurement.device_id) {
          const connectedDevice = await Device.findOne({ where: { id: measurement.device_id } });
          if (connectedDevice.state == 'ON')
            return res.status(500).send({ error: `Can't change Device ID when Measurement is starting` });
        }

        update = {
          device_id: device_id,
          start: null,
          end: null,
        };
        break;

      case 'start':
        // Start Measurement
        connectedDevice = await Device.findOne({ where: { id: measurement.device_id } });
        if (connectedDevice.state == 'OFF') {
          update = {
            start: new Date(),
            end: null,
          };
          // State Device ID : ON
          await connectedDevice.update({ state: 'ON' });
        } else if (device_id == measurement.device_id) {
          update = {
            start: new Date(),
            end: null,
          };
          // TODO: Reset Records
          console.log('Reset records');
        } else {
          // Device ID is Using in another Measurement
          return res.status(401).send({ error: `Device with ID: ${device_id} is using in another Measurement` });
        }
        break;

      case 'end':
        // Stop Measurement
        connectedDevice = await Device.findOne({ where: { id: measurement.device_id } });

        update = {
          end: new Date(),
        };
        // State Device ID : OFF
        await connectedDevice.update({ state: 'OFF' });
        break;
      default:
        break;
    }
    // Update
    await measurement.update(update);
    return res.status(200).send({ message: 'Measurement updated successfully' });
  } catch (error) {
    console.log(error);
    return res.status(500).send({ error: 'Error updating Measurement' });
  }
};

/**
 * -----------
 * deleteMeasurement
 * -----------
 */
export const deleteMeasurement = async (req, res) => {
  const id = req.params.id;

  try {
    const measurement = await Measurement.findOne({ where: { id } });

    if (!measurement) {
      return res.status(404).send({ error: 'Measurement not found' });
    }

    await Measurement.destroy({ where: { id } });

    return res.send({ message: 'Measurement deleted' });
  } catch (error) {
    console.error(error);
    return res.status(500).send({ error: 'Error deleting Measurement' });
  }
};
