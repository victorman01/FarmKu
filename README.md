
# Bangkit Capstone Project C22-PS152 Documentation C23-PS179

This repository contains the documentation for FarmKu Project.


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
