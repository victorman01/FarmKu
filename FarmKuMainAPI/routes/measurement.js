import express from 'express';

import {
    getMeasurements,
    getMeasurement,
    createMeasurement,
    deleteMeasurement,
    updateMeasurement,
} from '../controllers/measurement.js';
import { checkApiKey } from '../utils/checkApiKey.js';

const router = express.Router();

// All routesin here are starting with /Measurement
router.get('/', getMeasurements);
router.get('/:id', getMeasurement);
router.post('/', createMeasurement);
router.delete('/:id', deleteMeasurement);
router.patch('/:id', updateMeasurement);

export default router;