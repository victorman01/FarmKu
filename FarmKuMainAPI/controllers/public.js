import path from 'path';
import fs from 'fs';

export const getFile = (req, res) => {
  const filename = req.params.filename;
  const type = req.params.type;
  const dir = req.params.dir;

  if (type == 'uploads' || type == 'assets') {
    const imagePath = path.join('public', type, dir, filename);
    console.log(imagePath);
    // Check if file exists
    fs.access(imagePath, fs.constants.R_OK, (err) => {
      if (err) {
        res.status(404).send('File not found');
      } else {
        // Set content type header to image/jpeg
        res.setHeader('Content-Type', 'image/jpeg');

        // Send file as response
        fs.createReadStream(imagePath).pipe(res);
      }
    });
  } else {
    return res.status(401).send({ error: `Your request isn't authorized` });
  }
};
