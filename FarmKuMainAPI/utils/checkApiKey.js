import env from '../config/config.js';

// Get API Key from .env
const validApiKeys = env.API_KEY;

export const checkApiKey = (req, res, next) => {
    const apiKey = req.headers['x-api-key'];

    if (!apiKey) {
        console.log(apiKey);

        return res.status(401).send({ message: 'API key missing header' });
    }

    if (!validApiKeys.includes(apiKey)) {
        console.log(apiKey);

        return res.status(403).send({ message: 'Invalid API key header' });
    }


    // API key is valid, call next middleware function
    next();
};