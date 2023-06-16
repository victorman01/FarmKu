import { v4 as uuidv4 } from 'uuid';
import { User } from '../models/userModel.js';
import { Address, getAddressById } from '../models/addressModel.js';

import bcrypt from 'bcryptjs';
import jwt from 'jsonwebtoken';
import validator from 'validator';

import env from '../config/config.js';
import transporter from '../utils/transporter.js';

/**
 * -------
 * signUp
 * -------
 * This function handles the sign up logic for a new user.
 *
 * @param {Object} req - The request object containing the user's information.
 * @param {Object} res - The response object that will contain the server's response.
 * @returns {void}
 */
export const signUp = async (req, res) => {
  // Extract the user's information from the request body.
  const { name, email, password, phone_number, address } = req.body;

  // Define an array of required fields that must be provided by the user.
  const requiredFields = ['name', 'email', 'password', 'phone_number', 'address'];

  // Check if all required fields are provided, and return an error response if not.
  for (const field of requiredFields) {
    if (!req.body[field]) {
      return res.status(400).json({
        success: false,
        error: `${field} is required`,
      });
    }
  }

  // Check if the email already exists in the database, and return an error response if it does.
  const userExists = await User.findOne({ where: { email } });
  if (userExists) {
    return res.status(400).send({
      success: false,
      error: 'User with provided email already exists',
    });
  }

  // Generate a salt for the password and hash it with bcrypt.
  const salt = bcrypt.genSaltSync(10);
  const hashedPassword = bcrypt.hashSync(password, salt);

  try {
    // Create a new user with the provided information.
    const user = await User.create({
      id: uuidv4(),
      name: name,
      email: email,
      password: hashedPassword,
      phone_number: phone_number,
      address_id: address,
    });

    // Return a success response with the newly created user's ID.
    return res.status(200).send({
      success: true,
      message: 'User registered successfully',
      id: user.id,
    });
  } catch (error) {
    // Return an error response if the user could not be created.
    return res.status(500).send({
      success: false,
      message: error.errors[0].message,
    });
  }
};

/**
 * -------
 * signIn
 * -------
 *
 * This function handles the sign in logic for an existing user.
 * @param {Object} req - The request object containing the user's email and password.
 * @param {Object} res - The response object that will contain the server's response.
 * @returns {void}
 */
export const signIn = async (req, res) => {
  // Destructure email and password from request body
  const { email, password } = req.body;

  try {
    // Find user based on given email
    let user = await User.findOne({
      where: { email },
      include: {
        model: Address,
      },
    });

    user = Object.assign(user.dataValues, { address: await getAddressById(user.address_id) });

    // If user does not exist or if the passwords do not match, reject the request
    if (!user || !bcrypt.compareSync(password, user.password)) {
      return res.status(401).send({
        success: false,
        message: 'Email or password is incorrect',
      });
    } else {
      // TODO: Make user to response
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
      // Generate token based on user id
      const token = jwt.sign({ user }, env.JWT_SECRET, { expiresIn: '1h' });

      // Send response with token
      return res.status(200).send({
        success: true,
        token,
      });
    }
  } catch (err) {
    console.log(err);
    // If there is an error, send it as response
    return res.status(500).send({
      success: false,
      message: err.message,
    });
  }
};

/**
 * --------------
 * forgotPassword
 * --------------
 * Function to send a password reset link to the user's email
 *
 * @param {Object} req - The request object containing the user's email and password.
 * @param {Object} res - The response object that will contain the server's response.
 * @returns {void}
 */
export const forgotPassword = async (req, res) => {
  try {
    // Extract the email input from the request body
    const { email } = req.body;

    // Validate the email input
    if (!validator.isEmail(email)) {
      return res.status(400).json({ error: 'Invalid email address' });
    }

    // Find the user in the database based on the email address
    const user = await User.findOne({ where: { email } });

    // If the user is not found, return an error response
    if (!user) {
      return res.status(404).json({ error: 'User not found' });
    }
    // Generate a reset token for the user
    const token = jwt.sign({ email: email }, env.JWT_SECRET, { expiresIn: '1h' });
    // Update the user record in the database with the reset token
    await user.update({ reset_token: token });

    // Send an email to the user with the reset link
    const mailOptions = {
      from: env.SMTP_USER,
      to: email,
      subject: 'Password reset',
      html: `<p>Please click the following link to reset your password:</p>
         <a href="${env.APP_PROTOCOL}://${env.APP_HOST}/${env.APP_PORT}/reset-password?token=${token}">Reset Password</a>`,
    };
    await transporter.sendMail(mailOptions);

    // Return a success response to the client
    return res.status(200).send({
      success: true,
      message: 'Password reset link sent to the email',
      token: token,
      client: `${env.APP_PROTOCOL}://${env.APP_HOST}/${env.APP_PORT}/reset-password?token=${token}`,
    });
  } catch (error) {
    // If there is an error, return a generic error response
    console.log('Error sending email: ', error);
    return res.status(500).send({
      success: false,
      message: 'Error sending password reset email',
    });
  }
};

/**
 * --------------
 * resetPassword
 * --------------
 * Function to reset password of user
 *
 * @param {Object} req - The request object containing the user's email and new password and reset token.
 * @param {Object} res - The response object that will contain the server's response.
 * @returns {void}
 */
export const resetPassword = async (req, res) => {
  try {
    // Get the user data from request body
    const { newPassword } = req.body;
    const { token } = req.params;

    // Decode the JWT token and retrieve its payload
    const decodedPayload = jwt.verify(token, env.JWT_SECRET);
    const email = decodedPayload.email;

    // Find the user with that email
    const user = await User.findOne({ where: { email } });

    // If the user is not found return a 401
    if (!user) {
      return res.status(401).send({
        success: false,
        message: 'User not found',
      });
    }

    // If the tokens does not match return a 401
    if (user.reset_token !== token) {
      return res.status(401).send({
        success: false,
        message: 'Invalid or expired token',
      });
    }

    // Generate a salt for password hashing
    const salt = await bcrypt.genSalt(10);

    // Create a hash from the new password
    const hashedPassword = await bcrypt.hash(newPassword, salt);

    // Set the new Password and reset the token to null in the database
    await user.update({
      password: hashedPassword,
      reset_token: null,
    });

    // Return a success response
    return res.status(200).send({
      success: true,
      message: 'Password has been reset',
    });
  } catch (error) {
    // Return an error response if something goes wrong
    return res.status(500).send({
      success: false,
      message: error.message,
    });
  }
};
