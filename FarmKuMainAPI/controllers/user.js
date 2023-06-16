import { v4 as uuidv4 } from 'uuid';
import { User } from '../models/userModel.js';
import { Address, getAddressById } from '../models/addressModel.js';
import { Op } from 'sequelize';

/**
 * ---------
 * getUsers
 * ---------
 * This function retrieves users from the database and returns a list of users matching the given query parameters.
 *
 * @param {Object} req - Express.js request object
 * @param {Object} res - Express.js response object
 *
 * @returns (Object[]) Returns a Promise that resolves with an Express.Response object, containing the queried user or an error
 */
export const getUsers = async (req, res) => {
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
    }
    // Add the filter condition to search for users whose address starts with the given value
    where = {
      ...where,
      address_id: { [Op.like]: `${req.query.address}%` },
    };
  }

  // For all other query parameters, add a filter condition to search for users with a matching field value
  for (let key in req.query) {
    if (req.query.hasOwnProperty(key) && !req.query.address) {
      where[key] = req.query[key];
    }
  }

  try {
    // Use the Sequelize `count` method to calculate the total number of users that match the filter conditions
    const count = await User.count({
      where: where,
    });

    // Use the Sequelize `findAll` method to retrieve all users that match the filter conditions
    const users = await User.findAll({
      where: where,
    });
    if (users.length === 0) {
      return res.status(404).send({
        error: 'Users not found',
      });
    }
    const usersWithAddress = await Promise.all(
      users.map(async (user) => {
        const address = await getAddressById(user.address_id);
        return {
          id: user.id,
          name: user.name,
          email: user.email,
          phone_number: user.phone_number,
          address: {
            id: user.address_id,
            ...address,
          },
          role: user.role,
          created_at: user.created_at,
          updated_at: user.updated_at,
        };
      })
    );
    // Return a 200 status code with the list of users
    return res.status(200).send({
      count: count,
      data: usersWithAddress,
    });
  } catch (error) {
    // If there is an error, return a 500 status code with an error message
    return res.status(500).send({
      error: 'Error retrieving users',
      message: `${error.message}`,
    });
  }
};

/**
 * --------
 * getUser
 * --------
 * Finds a single user in the database and select by ID, ignoring their password
 *
 * @param (Object) req - Express request object containing parameters such as ID
 * @param (Object) res - Express response object to respond to the client
 *
 * @returns (Object[]) Returns a Promise that resolves with an Express.Response object, containing the queried user or an error
 */
export const getUser = async (req, res) => {
  try {
    // Find user based on given email
    let user = await User.findOne({
      where: { id: req.params.id },
    });
    user = Object.assign(user.dataValues, { address: await getAddressById(user.address_id) });
    user = {
      id: user.id,
      name: user.name,
      email: user.email,
      phone_number: user.phone_number,
      address: {
        id: user.address_id,
        ...user.address,
      },
      role: user.role,
      created_at: user.created_at,
      updated_at: user.updated_at,
    };
    if (!user) {
      return res.status(404).send({ error: 'User not found' });
    }
    return res.status(200).send(user);
  } catch (error) {
    console.log(error);
    return res.status(500).send({
      error: 'Error retrieving user',
      message: error,
    });
  }
};

/**
 * -----------
 * createUser
 * -----------
 * createUser() adds a new user to the database.
 *
 * @param (Object) req - The request object from the client such as name, email, password, phone_number, and address.
 * @param (Object) res - The response object to the client.
 *
 * @returns (Object) - A object containing the message and the id of the user
 */
export const createUser = async (req, res) => {
  const { name, email, password, phone_number, address } = req.body;

  // Check if the email already exists in the database
  const userExists = await User.findOne({ where: { email } });
  if (userExists) {
    return res.status(400).send({ error: 'User with provided email already exists' });
  }

  try {
    let user = await User.create({
      id: uuidv4(),
      name: name,
      email: email,
      password: password,
      phone_number: phone_number,
      address_id: address,
    });
    return res.status(201).send({ message: 'User created successfully', id: user.id });
  } catch (error) {
    return res.status(500).send({
      error: 'Error creating user',
      message: error,
    });
  }
};

/**
 * ------------------
 * Update User by ID
 * ------------------
 * Update a user's information
 *
 * @param (Object) req - Express request object
 * @param (Object) res - Express response object
 * @param (string) req.params.id - ID of the user to update
 * @param (Object) req.body - Request body containing the fields to update
 *
 * @returns (Object) - Response object containing the updated user information
 * @throws (Error) - If there is an error updating the user
 */
export const updateUser = async (req, res) => {
  const { id } = req.params;
  const { name, email, phone_number, address_id } = req.body;

  try {
    if (address_id) {
      // Check if address exists
      const addressExists = await Address.findOne({ where: { id: address_id } });

      if (!addressExists) {
        return res.status(400).send({ error: 'The address you are trying to update does not exist' });
      }
    }

    const user = await User.findOne({ where: { id: id } });
    if (!user) {
      return res.status(404).send({ error: 'User not found' });
    }

    await user.update({ name, email, phone_number, address_id });

    return res.status(200).send({ message: 'User updated successfully' });
  } catch (error) {
    return res.status(500).send({
      error: 'Error updating user',
      message: error,
    });
  }
};

/**
 * -----------
 * deletedUser
 * -----------
 * This function deletes a user from the database based on the given id in the request parameters.
 *
 * @param (Object) req - Express.js request object
 * @param (Object) res - Express.js response object
 * @param (string) req.params.id - ID of the user to delete
 *
 * @returns (Object) - Response object containing the updated user information
 */
export const deleteUser = async (req, res) => {
  try {
    // Use the Sequelize `destroy` method to delete the user with the given id
    const deletedUser = await User.destroy({
      where: {
        id: req.params.id,
      },
    });

    // If the user is not found, return a 404 status code with an error message
    if (!deletedUser) {
      return res.status(404).send({ error: 'User not found' });
    }

    // Return a 200 status code with a success message
    return res.status(200).send({ message: 'User deleted successfully!' });
  } catch (error) {
    // If there is an error, return a 500 status code with an error message
    return res.status(500).send({ error: 'Error deleting user' });
  }
};
