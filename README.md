
# Bangkit Capstone Project C22-PS152 Documentation C23-PS179

This repository contains the documentation for FarmKu Project.
<img src="https://github.com/victorman01/FarmKu/blob/main/images/farmku.png" alt="" data-canonical-src="[https://gyazo.com/eb5c5741b6a9a16c692170a41a49c858.png](https://github.com/victorman01/FarmKu/blob/main/images/farmku.png)" width="100" height="100" />
<br>


**_FarmKu - A platform that focuses on the collection, management, and utilization of farmer activity data as a recommendation for parameters of farming activities from upstream to downstream_**

## Table Of Content
1. Overview
2. How to run?
    - Machine Learning Documentation
    - Mobile Development Documentation
    - Cloud Computing Documentation
3. Resource
4. Team Member of C23-PS179

## 1. Overview
    FarmKu is a project to address challenges faced by farmers in Indonesia regarding soil conditions and plant diseases. The project offers plant disease detection and fertilization recommendations through a mobile application. The plant disease detection feature allows farmers to take photos of leaves that indicate certain diseases. The application uses machine learning models created with TensorFlow to predict the type of disease and provide appropriate treatment options. The models have high accuracy rates, exceeding 90% for most plants. The fertilizer recommendation feature collects soil nutrients data periodically using an IoT device and predicts the soil condition in the near future. If the soil is predicted to be suboptimal, farmers receive immediate notifications to apply fertilizers, helping optimize crop growth. But FarmKu is temporarily pivoting this feature into data collection due to a lack of data.

    The project involves three key teams: machine learning, mobile development, and cloud computing.

    The machine learning team focuses on developing plant disease detection models using TensorFlow. They leverage transfer learning for leaf detection and disease identification based on plant types. The models achieve high accuracy rates, contributing to accurate disease detection. The models are deployed on a server using Flask, ensuring seamless integration and interaction.

    The mobile development team handles the entire application development lifecycle. They start by wireframing and defining the project scope, goals, and user needs. Detailed wireframes and high-fidelity mockups are created, adhering to branding guidelines and UX best practices. The team ensures a user-friendly and visually appealing mobile application.

    The cloud computing team plays a significant role in integrating the cloud infrastructure with machine learning capabilities. They establish a robust environment for deploying and scaling the ML models using advanced cloud technologies. Well-defined APIs and service endpoints enable seamless communication and real-time data exchange between the mobile application and the ML backend, enhancing performance and the user experience.

## 2. How To Run
This API has been deployed using google cloud using cloud run and compute engine you can access it here:
    ML-Image Process API = ```http://34.101.59.1:3003/preprocess```
	Main API = ```https://farmku-backend-hwmomiroxq-et.a.run.app/``` 
	Data Collection API = ```https://farmku-api-hwmomiroxq-uc.a.run.app/```

## Machine Learning Documentation
If you want to run it locally, u need to do these kind of things:
1. Clone this repository, use ML Folder
2. Open terminal and create flask virtual environment using ``` python -m venv <name of environment>```
3. Install all the requiredment with  ``` pip install -r requiredments.txt ``` 
4. Run full_preprocess.py in terminal to run flask
5. Use url http://127.0.0.1:500/ to access api endpoint.

Machine Learning Documentation:
The process of All Data Preparation and Modelling can be accessed in this github repository (you can accessed it after cloning)

1. Load Dataset
The Leaf Dataset and our final model already upload in this link (bit.ly/DatasetAndModel).
The rest dataset could be downloaded here:
Rice Diseases Dataset ``` https://www.kaggle.com/datasets/minhhuy2810/rice-diseases-image-dataset ``` 
Potato and Corn Diseases Dataset ``` https://data.mendeley.com/datasets/tywbtsjrjv/1 ``` 
Tomato Diseases Datase ``` https://www.kaggle.com/datasets/kaustubhb999/tomatoleaf ``` 
Mango Diseases Dataset ``` https://data.mendeley.com/datasets/hxsnvwty3r/1 ``` 

2. Do Data Preprocessing for All Image Dataset
We used ImageDataGenerator library to do data preprocessing, such as  ``` rescale, rotation_range, width_shift_range, height_shift_range, shear_range, zoom_range, horizontal_flip, and fill_mode ``` . Training data and testing data have their own preprocessing needs. We only need to rescale data testing into the size that we need.

3. Modelling
We use transfer learning to build the model. The Major Dataset is modelled by Inception Architecture, while Leaf Dataset using VGG16 for better result and size.

4. Testing Prediction
After the model is ready, we have to test the model to evaluate the result.

5. Save and Convert Model Into H5 Format
After the model is ready and evaluated correctly, we could save the model into H5 format. It will enable the model to load by  ``` Tensorflow load_model ```  and store it in variable. It will allow us to integrated the model with the API.

## Mobile Development Documentation
1. Splash Screen: A captivating screen that welcomes users with an animated logo and a sleek transition into the app.
2. Onboarding: A visually stunning introduction that guides users through the key features of our app using eye-catching graphics and smooth animations.
3. Login Page: A stylish login interface with a modern design and intuitive user experience. Users can securely sign in using their credentials or social media accounts.
4. Register Page: An attractive and user-friendly registration form that allows new users to create an account effortlessly. We've added cool animations to make the process more engaging.
5. Forgot Password Page: A sleek and user-centric interface where users can reset their forgotten passwords easily. We've implemented a secure verification process for account recovery.
6. OTP Page: A cutting-edge one-time password verification page with a minimalist design and smooth transitions. Users receive a unique code via email or SMS for secure authentication.
7. Main Page: The heart of our app, featuring four dynamic fragments:

        a. Home Page: A visually appealing and interactive interface where users can explore personalized content, latest updates, and important announcements.
        b. News Page: A captivating news feed with stunning images and smooth scrolling. Users can stay up-to-date with the latest industry news and trends.
        c. List Land Page: A comprehensive list of land properties with detailed information and high-quality images. Users can easily browse and search for their desired properties.
        d. Profile: A sleek and customizable profile page where users can manage their personal information, profile picture, and app preferences.
8. Disease Detection Page: An innovative feature that uses advanced algorithms and image recognition to detect diseases in plants. Users can simply upload a photo of the affected plant for instant analysis and recommendations.
9. Data Soil Land Collection Page: An efficient and user-friendly interface for collecting and analyzing soil data. Users can input relevant parameters and generate detailed reports for land assessment.
10. Record Page: A convenient page for users to keep track of their activities, such as plant care, fertilization, and watering schedules. We've added a visually pleasing calendar view for better organization.
11. Measurement Page: A smart and intuitive tool for measuring various aspects of plants, such as height, width, and growth rate. Users can easily record and track their plant's progress.

**Dependencies used during development:**
1. Retrofit: A powerful networking library for seamless communication with APIs, enabling smooth data exchange between the app and server.
2. OkHttp3: A reliable and efficient HTTP client that optimizes network requests, ensuring fast and secure data transmission.
3. LiveData: A lifecycle-aware component from the Android Architecture Components, providing real-time data updates and automatic UI synchronization.
4. Glide: An impressive image loading and caching library that optimizes image rendering and improves overall app performance.
5. Navigation Component: A feature-rich navigation framework that simplifies the implementation of app navigation and enhances user flow.

**Tools used in the development process:**

1. Postman: A versatile API testing and documentation tool that helps developers analyze and debug API requests and responses effectively.

2. Android Studio: The official Integrated Development Environment (IDE) for Android app development, providing a comprehensive set of tools, emulators, and debugging features to streamline the development process.

With these enhancements, our Android Application delivers an even more captivating user experience, combining stunning visuals, smooth animations, and cutting-edge features.

## Cloud Computing Documentation

## 3. Resource
## 4. Team Member of C23-PS179
| ID | Name | University | Learning Path |
| ------ | ------ | ------ | ------ |
| C340DKX3998 | Rayhan Emillul Fata | Universitas Negeri Sebelas Maret | Cloud Computing 
| C340DKX4157 | Samuel Steven P. H. | Universitas Negeri Sebelas Maret | Cloud Computing
| M038DKX4066 | Benedictus Kenny T. | Institut Teknologi Sepuluh Nopember | Machine Learning
| M351DKX4183 | Alvin Fernando S. | Universitas Surabaya | Machine Learning | 
| A351DKX4108 | Victor Manuel S. | Universitas Surabaya | Mobile Development |
| A351DKX4106 | Michael Andreas | Universitas Surabaya | Mobile Development |

## Features

- Import a HTML file and watch it magically convert to Markdown
- Drag and drop images (requires your Dropbox account be linked)
- Import and save files from GitHub, Dropbox, Google Drive and One Drive
- Drag and drop markdown and HTML files into Dillinger
- Export documents as Markdown, HTML and PDF

Markdown is a lightweight markup language based on the formatting conventions
that people naturally use in email.
As [John Gruber] writes on the [Markdown site][df1]

> The overriding design goal for Markdown's
> formatting syntax is to make it as readable
> as possible. The idea is that a
> Markdown-formatted document should be
> publishable as-is, as plain text, without
> looking like it's been marked up with tags
> or formatting instructions.

This text you see here is *actually- written in Markdown! To get a feel
for Markdown's syntax, type some text into the left window and
watch the results in the right.

## Tech

Dillinger uses a number of open source projects to work properly:

- [AngularJS] - HTML enhanced for web apps!
- [Ace Editor] - awesome web-based text editor
- [markdown-it] - Markdown parser done right. Fast and easy to extend.
- [Twitter Bootstrap] - great UI boilerplate for modern web apps
- [node.js] - evented I/O for the backend
- [Express] - fast node.js network app framework [@tjholowaychuk]
- [Gulp] - the streaming build system
- [Breakdance](https://breakdance.github.io/breakdance/) - HTML
to Markdown converter
- [jQuery] - duh

And of course Dillinger itself is open source with a [public repository][dill]
 on GitHub.

## Installation

Dillinger requires [Node.js](https://nodejs.org/) v10+ to run.

Install the dependencies and devDependencies and start the server.

```sh
cd dillinger
npm i
node app
```

For production environments...

```sh
npm install --production
NODE_ENV=production node app
```

## Plugins

Dillinger is currently extended with the following plugins.
Instructions on how to use them in your own application are linked below.

| Plugin | README |
| ------ | ------ |
| Dropbox | [plugins/dropbox/README.md][PlDb] |
| GitHub | [plugins/github/README.md][PlGh] |
| Google Drive | [plugins/googledrive/README.md][PlGd] |
| OneDrive | [plugins/onedrive/README.md][PlOd] |
| Medium | [plugins/medium/README.md][PlMe] |
| Google Analytics | [plugins/googleanalytics/README.md][PlGa] |

## Development

Want to contribute? Great!

Dillinger uses Gulp + Webpack for fast developing.
Make a change in your file and instantaneously see your updates!

Open your favorite Terminal and run these commands.

First Tab:

```sh
node app
```

Second Tab:

```sh
gulp watch
```

(optional) Third:

```sh
karma test
```

#### Building for source

For production release:

```sh
gulp build --prod
```

Generating pre-built zip archives for distribution:

```sh
gulp build dist --prod
```

## Docker

Dillinger is very easy to install and deploy in a Docker container.

By default, the Docker will expose port 8080, so change this within the
Dockerfile if necessary. When ready, simply use the Dockerfile to
build the image.

```sh
cd dillinger
docker build -t <youruser>/dillinger:${package.json.version} .
```

This will create the dillinger image and pull in the necessary dependencies.
Be sure to swap out `${package.json.version}` with the actual
version of Dillinger.

Once done, run the Docker image and map the port to whatever you wish on
your host. In this example, we simply map port 8000 of the host to
port 8080 of the Docker (or whatever port was exposed in the Dockerfile):

```sh
docker run -d -p 8000:8080 --restart=always --cap-add=SYS_ADMIN --name=dillinger <youruser>/dillinger:${package.json.version}
```

> Note: `--capt-add=SYS-ADMIN` is required for PDF rendering.

Verify the deployment by navigating to your server address in
your preferred browser.

```sh
127.0.0.1:8000
```

## License

MIT

**Free Software, Hell Yeah!**

[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)

   [dill]: <https://github.com/joemccann/dillinger>
   [git-repo-url]: <https://github.com/joemccann/dillinger.git>
   [john gruber]: <http://daringfireball.net>
   [df1]: <http://daringfireball.net/projects/markdown/>
   [markdown-it]: <https://github.com/markdown-it/markdown-it>
   [Ace Editor]: <http://ace.ajax.org>
   [node.js]: <http://nodejs.org>
   [Twitter Bootstrap]: <http://twitter.github.com/bootstrap/>
   [jQuery]: <http://jquery.com>
   [@tjholowaychuk]: <http://twitter.com/tjholowaychuk>
   [express]: <http://expressjs.com>
   [AngularJS]: <http://angularjs.org>
   [Gulp]: <http://gulpjs.com>

   [PlDb]: <https://github.com/joemccann/dillinger/tree/master/plugins/dropbox/README.md>
   [PlGh]: <https://github.com/joemccann/dillinger/tree/master/plugins/github/README.md>
   [PlGd]: <https://github.com/joemccann/dillinger/tree/master/plugins/googledrive/README.md>
   [PlOd]: <https://github.com/joemccann/dillinger/tree/master/plugins/onedrive/README.md>
   [PlMe]: <https://github.com/joemccann/dillinger/tree/master/plugins/medium/README.md>
   [PlGa]: <https://github.com/RahulHP/dillinger/blob/master/plugins/googleanalytics/README.md>
