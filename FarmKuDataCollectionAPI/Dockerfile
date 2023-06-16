# Ambil node.js versi terbaru sebagai dasar
FROM node:14

# Pindahkan ke direktori kerja
WORKDIR /api-farmku

# Salin package.json dan package-lock.json (jika ada) ke direktori kerja
COPY package*.json ./

# Install dependensi aplikasi
RUN npm install

# Salin seluruh kode aplikasi ke direktori kerja
COPY . .

# Eksekusi perintah "npm start" ketika kontainer berjalan
CMD [ "node", "app.js" ]