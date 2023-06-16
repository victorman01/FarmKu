const DataCollection = require('../models/dataCollection');
const multer = require('multer');

// Mendapatkan semua data dari tabel
exports.getAllData = async (req, res) => {
  try {
    const data = await DataCollection.findAll();
    res.json(data);
  } catch (error) {
    res.status(500).json({ error: 'Internal server error' });
  }
};


// Handler untuk membuat data baru dengan file gambar
exports.createData = async (req, res) => {
//  console.log(req.body);
//  console.log(req.files);
 
  try {
    const { nama_varietas, N, P, K, PH, Longitude, Latitude, Description } = req.body;

    // Mengambil nama file gambar dari req.file
    const imageName = req.files;

    // Membuat data baru dengan menyimpan nama file gambar
    const newData = await DataCollection.create({
      nama_varietas,
      N,
      P,
      K,
      PH,
      Longitude,
      Latitude,
      Image: imageName, // Menyimpan nama file gambar
      Description
    });
    console.log(newData);
    res.json(newData);

  } catch (error) {
    console.log(error);
    res.status(500).json({ error: 'Internal server error' });
  }
};