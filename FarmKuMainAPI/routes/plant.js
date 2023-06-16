import express from 'express';

import { getPlants, getPlant, createPlant, deletePlant, updatePlant } from '../controllers/plant.js';
import { createMulter } from '../utils/multer.js';
import { checkApiKey } from '../utils/checkApiKey.js';

const router = express.Router();

// All routesin here are starting with /Plant
router.get('/', getPlants);
router.get('/:id', getPlant);
router.post('/', createMulter().single('image'), createPlant);
router.delete('/:id', deletePlant);
router.patch('/:id', createMulter().single('image'), updatePlant);

export default router;