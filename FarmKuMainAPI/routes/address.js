import express from 'express';

import { getAddress, getAddresses } from '../controllers/address.js';
import { checkApiKey } from '../utils/checkApiKey.js';

const router = express.Router();

// All routesin here are starting with /address
router.get('/', getAddresses);
router.get('/:id', getAddress);

export default router;