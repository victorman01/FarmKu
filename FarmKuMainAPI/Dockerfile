# Use the official lightweight Node.js 12 image.
# https://hub.docker.com/_/node
FROM node:17-alpine as dependencies

# Create and change to the app directory.
WORKDIR /index

# Copy application dependency manifests to the container image.
# A wildcard is used to ensure both package.json AND package-lock.json are 
copied.
# Copying this separately prevents re-running npm install on every code 
change.
COPY package*.json ./

# Install
RUN npm i

# Copy local code to the container image.
COPY . ./

# Run the web service on container startup.
CMD [ "npm", "start" ]

