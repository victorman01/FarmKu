import mongoose from 'mongoose';

const RecordSchema = new mongoose.Schema({
  measurementId: {
    type: String,
    unique: false,
    required: true,
    maxlength: 11,
  },
  condition: {
    type: Object,
    required: true,
    default: {
      nitrogen: '0 kg/ha',
      phosphorus: '0 kg/ha',
      potassium: '0 kg/ha',
      ph: '0.0',
      soil_moisture: '0%',
      soil_temperature: '0°C',
      light: '0 lux',
      rainfall: '0 mm/year',
    },
  },
  location: {
    type: Object,
    required: true,
    default: { long: 0, lat: 0 },
  },
  createdAt: {
    type: Date,
    default: Date.now,
    required: true,
  },
  updatedAt: {
    type: Date,
    default: Date.now,
    required: true,
  },
});

const Record = mongoose.model('Record', RecordSchema, 'records');

export default Record;
