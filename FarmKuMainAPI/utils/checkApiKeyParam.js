import env from '../config/config.js';

// Get API Key from .env
const validApiKeys = env.API_KEY;

export const checkApiKeyParam = async(req, res, next) => {
    const {
        apiKey
    } = req.params;

    if (!apiKey) {
        //console.log('jadi')
        return res.status(401).send({ message: 'API key missing param' });
    }

    if (!validApiKeys.includes(apiKey)) {
        //console.log('gagal')
        return res.status(403).send({ message: 'Invalid API key param' });
    }


    // API key is valid, call next middleware function
    next();
};