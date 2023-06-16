import express from 'express';

import {
    getPlantProblems,
    getPlantProblem,
    createPlantProblem,
    deletePlantProblem,
    updatePlantProblem,
} from '../controllers/plantProblem.js';
import { createMulter } from '../utils/multer.js';
import { checkApiKey } from '../utils/checkApiKey.js';

const router = express.Router();

// All routesin here are starting with /PlantProblem
router.get('/', getPlantProblems);
router.get('/:id', getPlantProblem);
router.post('/', createMulter().single('image'), createPlantProblem);
router.delete('/:id', deletePlantProblem);
router.patch('/:id', createMulter().single('image'), updatePlantProblem);

export default router;