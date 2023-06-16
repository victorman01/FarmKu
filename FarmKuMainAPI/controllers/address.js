import { Op, Sequelize } from 'sequelize';
import { Address } from '../models/addressModel.js';

/**
 * ------------------
 * Get Address by ID
 * ------------------
 * getAddress will fetch an array of address based on the ID parameter
 *
 * @param (Object) req - Express Request Object
 * @param (Object) res - Express Respose Object
 *
 * @query (String) search - Must be one of the following values: province, district, regency or village
 * @query (String) id - Will return address with value begin with the string
 *
 * @return (Object[]) An array of Address entities
 */
// Finds the address which matches the id parameter from the request
export const getAddress = async (req, res) => {
  try {
    //Find a single address record in database by its id
    const address = await Address.findOne({ where: { id: req.params.id } });
    //If not found, returns error
    if (!address) {
      return res.status(404).send({ error: 'Address not found' });
    }
    //Sends OK response with address found
    return res.status(200).send(address);
  } catch (error) {
    //If there was an internal error, sends error message
    return res.status(500).send({
      error: 'Error retrieving address',
      message: error,
    });
  }
};

/**
 * ------------
 * getAddresses
 * ------------
 * getAddresses will fetch an array of addresses based on the query parameters provided
 *
 * @param (Object) req - Express Request Object
 * @param (Object) res - Express Respose Object
 * @param (string) req.query.search = one of the four address types, i.e 'province', 'district', 'regency' or 'village'.
 * @param (string) req.query.id - optional param to indicate which address should be fetched
 *
 * @returns (Object[]) - an array of address objects representing the addresses found (may have length 0 if not found)
 *
 * Throws error 500 wih a message if there is an unforeseen server-side error
 */
export const getAddresses = async (req, res) => {
  try {
    // Map search terms to length of the address id
    const param = {
      province: 2,
      district: 5,
      regency: 8,
      village: 13,
    };

    // Initialize the `where` object for the query
    let where = {};
    // ✅
    if (req.query.id && !req.query.search) {
      where = {
        id: req.query.id,
      };
    }

    // ✅
    // Check if a `search` query is present and get the corresponding id length
    if (req.query.search && !req.query.id) {
      let lengthId = param[req.query.search];
      if (lengthId) {
        where = {
          value: Sequelize.where(Sequelize.fn('LENGTH', Sequelize.col('id')), lengthId),
        };
      }
    }

    // Check if an `id` query is present and add it to the `where` object
    if (req.query.id && req.query.search) {
      let lengthId = param[req.query.search];
      where = {
        ...where,
        id: { [Op.like]: `${req.query.id}%` },
        value: Sequelize.where(Sequelize.fn('LENGTH', Sequelize.col('id')), lengthId),
      };
    }

    // Execute the query to retrieve addresses
    const address = await Address.findAll({
      where: where,
    });

    // If no addresses are found, return a 404 error
    if (!address || address.length < 1) {
      return res.status(404).send({ error: 'Address not found' });
    }

    // Return the retrieved addresses with a 200 status code
    res.status(200).send(address);
  } catch (error) {
    // Return a 500 error if there was an error while retrieving addresses
    res.status(500).send({
      error: 'Error retrieving address',
      message: `${error}`,
    });
  }
};
