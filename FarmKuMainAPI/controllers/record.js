import { Device } from '../models/deviceModel.js';
import { Measurement } from '../models/measurementModel.js';
import Record from '../models/recordModel.js';

export default {
    async getAllRecords(req, res) {
        try {
            const { measurementId } = req.query;
            const filter = measurementId ? { measurementId } : {};
            if (measurementId) {
                const measurement = await Measurement.findOne({ where: { id: measurementId } });
                if (!measurement) return res.status(400).send({ error: 'Measurement not found' });
            }
            const records = await Record.find(filter);
            const count = await Record.countDocuments(filter);
            if (!records) res.status(400).send({ error: 'There is no record' });
            return res.status(200).send({
                count,
                records,
            });
        } catch (err) {
            console.error(err);
            res.status(500).send({ error: 'Internal server error' });
        }
    },

    async getRecordById(req, res) {
        try {
            const record = await Record.findById(req.params.id);
            if (!record) {
                return res.status(404).send({ error: 'Record not found' });
            }
            res.json(record);
        } catch (err) {
            console.error(err);
            res.status(500).send({ error: 'Internal server error' });
        }
    },

    async createRecord(req, res) {
        const { measurementId, condition, location } = req.body;
        try {
            const measurement = await Measurement.findOne({ where: { id: measurementId } });
            if (!measurement) return res.status(404).send({ error: 'Measurement not found' });

            const device = await Device.findOne({ where: { id: measurement.device_id } });
            if (device.state == 'OFF')
                return res.status(400).send({
                    error: `Device ID ${device.id} state is OFF`,
                    message: 'Please set your device state into ON to send a record',
                });
            const newRecord = new Record({ measurementId, condition, location });
            await newRecord.save();
            res.json(newRecord);
        } catch (err) {
            console.error(err);
            res.status(500).send({ error: 'Internal server error', message: err });
        }
    },

    async updateRecord(req, res) {
        const { measurementId, condition, location } = req.body;
        try {
            const measurement = Measurement.findOne({ where: { id: measurementId } });
            if (!measurement) return res.status(404).send('Measurement not found');

            let record = await Record.findById(req.params.id);
            if (!record) {
                return res.status(404).send({ error: 'Record not found' });
            }
            record.measurementId = measurementId;
            record.condition = condition;
            record.location = location;
            record.updatedAt = Date.now();
            await record.save();
            res.json(record);
        } catch (err) {
            console.error(err);
            res.status(500).send({ error: 'Internal server error' });
        }
    },

    async deleteRecord(req, res) {
        try {
            let record = await Record.findById(req.params.id);
            if (!record) {
                return res.status(404).send({ error: 'Record not found' });
            }
            await record.remove();
            res.send('Record deleted');
        } catch (err) {
            console.error(err);
            res.status(500).send({ error: 'Internal server error' });
        }
    },

    async createRecordByDevice(req, res) {
        const { n, p, k, ph, lon, lat } = req.query;
        const { measurementId } = req.params;
        console.log(measurementId);
        try {
            const measurement = await Measurement.findOne({ where: { id: measurementId } });
            if (!measurement) return res.status(404).send({ error: 'Measurement not found' });

            const device = await Device.findOne({ where: { id: measurement.device_id } });
            if (device.state == 'OFF') {
                return res.status(400).send({
                    error: `Device ID ${device.id} state is OFF`,
                    message: 'Please set your device state into ON to send a record',
                });
            }

            const condition = {
                nitrogen: n + ' kg/ha',
                phosphorus: p + ' kg/ha',
                potassium: k + ' kg/ha',
                ph: ph,
                soil_moisture: ' %',
                soil_temperature: ' °C',
                light: ' lux',
                rainfall: ' mm/year',
            };

            const newRecord = new Record({ measurementId, condition, location: { type: 'Point', coordinates: [lon, lat] } });
            await newRecord.save();
            res.json(newRecord);
        } catch (err) {
            console.log(err);
            res.status(500).send({ error: 'Internal server error', message: err });
        }
    },
};