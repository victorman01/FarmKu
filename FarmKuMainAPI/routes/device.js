import express from 'express';

import { getDevices, getDevice, createDevice, deleteDevice, updateDevice } from '../controllers/device.js';
import { checkApiKey } from '../utils/checkApiKey.js';

const router = express.Router();

// All routesin here are starting with /Device
router.get('/', getDevices);
router.get('/:id', getDevice);
router.post('/', createDevice);
router.delete('/:id', deleteDevice);
router.patch('/:id', updateDevice);

export default router;