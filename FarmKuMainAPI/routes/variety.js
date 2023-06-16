import express from 'express';

import { getVarieties, getVariety, createVariety, deleteVariety, updateVariety } from '../controllers/variety.js';
import { createMulter } from '../utils/multer.js';
import { checkApiKey } from '../utils/checkApiKey.js';

const router = express.Router();

// All routesin here are starting with /Variety
router.get('/', getVarieties);
router.get('/:id', getVariety);
router.post('/', createMulter().single('image'), createVariety);
router.delete('/:id', deleteVariety);
router.patch('/:id', createMulter().single('image'), updateVariety);

export default router;