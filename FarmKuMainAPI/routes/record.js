import express from 'express';
import recordController from '../controllers/record.js';
import { checkApiKeyParam } from '../utils/checkApiKeyParam.js';
import { checkApiKey } from '../utils/checkApiKey.js';

const router = express.Router();

router.param('apiKey', checkApiKeyParam);
// This route will available for device to send record to MongoDB
router.get('/from-device/:apiKey/:measurementId', recordController.createRecordByDevice);

router.use(checkApiKey);

router.get('/', recordController.getAllRecords);
router.get('/:id', recordController.getRecordById);
router.post('/', recordController.createRecord);
router.patch('/:id', recordController.updateRecord);
router.delete('/:id', recordController.deleteRecord);



export default router;