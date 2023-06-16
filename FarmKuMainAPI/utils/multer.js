import multer from 'multer';
import path from 'path';

// Function to create a multer instance with a custom destination folder
export const createMulter = () => {
  const storage = multer.diskStorage({
    filename: (req, file, callback) => {
      const ext = path.extname(file.originalname);
      const name = file.originalname.replace(ext, '');
      callback(null, `${name}-${Date.now()}${ext}`);
    },
  });

  return multer({
    storage: storage,
    limits: { fileSize: 1000000 }, // 1 MB
  });
};
