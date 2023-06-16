import express from 'express';

import { getUsers, getUser, createUser, deleteUser, updateUser } from '../controllers/user.js';
import { checkApiKey } from '../utils/checkApiKey.js';

const router = express.Router();

// All routesin here are starting with /user
router.get('/', getUsers);
router.get('/:id', getUser);
router.post('/', createUser);
router.delete('/:id', deleteUser);
router.patch('/:id', updateUser);

export default router;