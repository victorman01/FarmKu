import express from 'express';

import { getLands, getLand, createLand, deleteLand, updateLand } from '../controllers/land.js';
import { checkApiKey } from '../utils/checkApiKey.js';

const router = express.Router();

// All routesin here are starting with /land
router.get('/', getLands);
router.get('/:id', getLand);
router.post('/', createLand);
router.delete('/:id', deleteLand);
router.patch('/:id', updateLand);


export default router;