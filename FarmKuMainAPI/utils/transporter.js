import nodemailer from 'nodemailer';

// This function is using when transporter.sendMail() is executed
const transporter = nodemailer.createTransport({
  host: 'smtp-relay.sendinblue.com',
  port: 587,
  secure: false, // true for 465, false for other ports
  auth: {
    user: 'ridhofataulwan@gmail.com',
    pass: 'xsmtpsib-e93971ef617e5da80ce54cb7b4cd68fe75e613c6b0a417e6663b125f27fc7b1d-Qbk0DpTCdGaJ6SLM',
  },
});

export default transporter;
