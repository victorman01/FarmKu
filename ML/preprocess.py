from flask import Flask, request, jsonify, send_file
import numpy as np
import tensorflow as tf
from PIL import Image
import io

app = Flask(__name__)

# Serve the index.html file
# @app.route('/')
# def index():
#     return send_file("path_to_index.html")

# Serve the script.js file
# @app.route('/script.js')
# def script():
#     return send_file("path_to_script.js")

# Load model
leaf_model = tf.keras.models.load_model("C:/xampp2/htdocs/Flask/Leaf_Model.h5")
corn_model = tf.keras.models.load_model("C:/xampp2/htdocs/Flask/Corn_Model.h5")
mango_model = tf.keras.models.load_model("C:/xampp2/htdocs/Flask/Mango_Model.h5")
potato_model = tf.keras.models.load_model("C:/xampp2/htdocs/Flask/Potato_Model.h5")
rice_model = tf.keras.models.load_model("C:/xampp2/htdocs/Flask/Rice_Model.hdf5")
tomato_model = tf.keras.models.load_model("C:/xampp2/htdocs/Flask/Tomato_Model.h5")

image = []
prediction_list = [0,0]
disease_list = {# Corn
                0: {0: "Cercospora",
                    1 : "Common Rust",
                    2 : "Healthy",
                    3 : "Northern Leaf Blight"},
                
                # Mango
                1: {0 : "Anthracnose",
                    1 : "Bacterial Canker",
                    2 : "Cutting Weevil",
                    3 : "Die Back",
                    4 : "Gall Midge",
                    5 : "Healthy",
                    6 : "Powdery Mildew",
                    7 : "Sooty Mould"},
                
                # Potato
                2: {0 : "Early Blight",
                    1 : "Healthy",
                    2 : "Late Blight"},
                
                # Rice
                3: {0: "Brown Spot",
                    1 : "Healthy",
                    2 : "Hispa",
                    3 : "Leaf Blast"},
                
                # Tomato
                4: {0 : "Bacterial Spot",
                    1 : "Early Blight",
                    2 : "Healthy",
                    3 : "Late Blight",
                    4 : "Leaf Mold",
                    5 : "Septoria Leaf Spot",
                    6 : "Spider Mites",
                    7 : "Target Spot",
                    8 : "Mosaic Virus",
                    9 : "Yellow Leaf Curl Virus"}}

@app.route("/preprocess", methods=["POST"])
def preprocess():
    image_file = request.files["photo"]
    image = Image.open(image_file.stream)
    image = image.resize((224, 224))
    image = np.array(image.convert('RGB')) / 255.0
    image = np.expand_dims(image, axis=0)

    # Leaf Prediction
    leaf_prediction = leaf_model.predict(image)
    leaf_index = np.argmax(leaf_prediction)
    if leaf_prediction[0][leaf_index] >= 0.8:
        prediction_list[0] = leaf_index
        
        # Choosing disease model
        if prediction_list[0] == 0:
            disease_model = corn_model
        elif prediction_list[0] == 1:
            disease_model = mango_model
        elif prediction_list[0] == 2:
            disease_model = potato_model
        elif prediction_list[0] == 3:
            disease_model = rice_model
        elif prediction_list[0] == 4:
            disease_model = tomato_model

        # Disease Prediction
        disease_prediction = disease_model.predict(image)
        disease_index = np.argmax(disease_prediction)

        # Result
        prediction = disease_list[leaf_index][disease_index]
        prediction_confidence = disease_prediction[0][disease_index]
        return jsonify(result=str(prediction), confidence=str(prediction_confidence))
    else:
        return jsonify(result="failed", confidence="Not included in dataset")

if __name__ == "__main__":
    app.run(debug=True)