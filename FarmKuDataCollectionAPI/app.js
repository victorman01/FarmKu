const express = require('express');
const bodyParser = require('body-parser');
const dataController = require('./controller/dataCollectionController');
const { sequelize } = require('./config/dbConfig.js')
const multer = require('multer');

const app = express();
// const storage = multer.memoryStorage();

// Konfigurasi multer untuk menyimpan file gambar
const storage = multer.diskStorage({
  destination: function (req, file, cb) {
    cb(null, 'uploads/'); // Menentukan folder penyimpanan gambar
  },
  filename: function (req, file, cb) {
    const uniqueSuffix = Date.now() + '-' + Math.round(Math.random() * 1e9);
    cb(null,  uniqueSuffix + file.originalname); // Menentukan nama file
  }
});

const upload = multer({ storage });


// Menggunakan body-parser untuk parsing JSON dan URL-encoded
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

// Mengambil semua data
app.get('/data-collection', dataController.getAllData);

app.use('/uploads', express.static('uploads'));

app.get('/uploads/:filename', (req, res) => {
  res.set('Content-Type', 'image/jpeg');
  res.sendFile(imagePath, { root: __dirname });
});

// Menambahkan data baru
// app.post('/data-collection', upload.any('Image'), dataController.createData);
app.post('/data-collection', upload.any(''), dataController.createData);

// Menjalankan server
app.listen(8000, () => {
  console.log('Server berjalan di port 8000');
});