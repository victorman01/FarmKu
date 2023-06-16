import express from 'express';

import {
    getCultivations,
    getCultivation,
    createCultivation,
    deleteCultivation,
    updateCultivation,
} from '../controllers/cultivation.js';
import { checkApiKey } from '../utils/checkApiKey.js';

const router = express.Router();

// All routesin here are starting with /Cultivation
router.get('/', getCultivations);
router.get('/:id', getCultivation);
router.post('/', createCultivation);
router.delete('/:id', deleteCultivation);
router.patch('/:id', updateCultivation);

export default router;