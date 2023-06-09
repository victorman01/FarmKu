from flask import Flask, request, jsonify, send_file
import numpy as np
import tensorflow as tf
from PIL import Image
import io

app = Flask(__name__)

# Serve the index.html file
# @app.route('/')
# def index():
#     return send_file("E:/Kuliah/Bangkit/Capstone/Flask/index.html")

# Serve the script.js file
# @app.route('/script.js')
# def script():
#     return send_file("E:/Kuliah/Bangkit/Capstone/Flask/script.js")

# Serve the model.h5 file
# @app.route('/model.h5')
# def model():
#     return send_file("E:/Kuliah/Bangkit/Capstone/Flask/Corn_Model.h5")

model = tf.keras.models.load_model("Corn_Model.h5")
preprocessed_image = []

@app.route("/preprocess", methods=["POST"])
def preprocess():
    image_file = request.files["photo"]
    # image_file = request.get_json()
    image = Image.open(image_file.stream)
    image = image.resize((224, 224))
    image = np.array(image.convert('RGB')) / 255.0
    image = np.expand_dims(image, axis=0)
    preprocessed_image=image.tolist()
    preprocessed_image = np.array(preprocessed_image)

    # Make predictions using the preprocessed image
    prediction = model.predict(preprocessed_image)
    index_class = np.argmax(prediction)

    if index_class == 0:
        predicted_class = 'Cercospora'
    elif index_class == 1:
        predicted_class = 'Common Rust'
    elif index_class == 2:
        predicted_class = 'Healthy'
    elif index_class == 3:
        predicted_class = 'Northern Leaf Blight'

    prediction_confidence = prediction[0][index_class]
    # Return the prediction result
    return jsonify(result=str(predicted_class), confidence=str(prediction_confidence))

if __name__ == "__main__":
    app.run(debug=True)
