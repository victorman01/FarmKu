import express from 'express';

import { signIn, signUp, forgotPassword, resetPassword } from '../controllers/auth.js';
import { checkApiKey } from '../utils/checkApiKey.js';

const router = express.Router();

// All routesin here are starting with /auth
router.post('/signin', signIn);
router.post('/signup', signUp);
router.post('/forgot-password', forgotPassword);
router.post('/reset-password/:token', resetPassword);

export default router;