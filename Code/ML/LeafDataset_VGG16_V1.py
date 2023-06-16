# -*- coding: utf-8 -*-
"""Leaf_Dataset_VGG16_Done.ipynb

Automatically generated by Colaboratory.

Original file is located at
    https://colab.research.google.com/drive/1leJMjMWN4K2rb7YDA2bfrW5ycCFafJ5C
"""

import os
import zipfile
import matplotlib.pyplot as plt
import tensorflow as tf
from tensorflow.keras import layers
from tensorflow.keras import Model
from tensorflow.keras.optimizers import RMSprop
from tensorflow.keras.preprocessing.image import ImageDataGenerator
from tensorflow.keras.utils import img_to_array, load_img

import zipfile
from google.colab import drive
drive.mount('/content/gdrive')

local_zip = '/content/gdrive/MyDrive/Capstone/Leaf/LeafDataset3k.zip'
zip_ref = zipfile.ZipFile(local_zip, 'r')
zip_ref.extractall()

zip_ref.close()

def train_val_generators(TRAINING_DIR, VALIDATION_DIR):
  train_datagen = ImageDataGenerator(rescale=1.0/255.,
                                     rotation_range=180,
                                     width_shift_range=0.2,
                                     height_shift_range=0.2,
                                     shear_range=0.2,
                                     zoom_range=0.2,
                                     horizontal_flip=True,
                                     fill_mode='nearest')

  train_generator = train_datagen.flow_from_directory(directory=TRAINING_DIR,
                                                      batch_size=64,
                                                      class_mode='categorical',
                                                      target_size=(224, 224))

  validation_datagen = ImageDataGenerator( rescale = 1.0/255.)
  validation_generator = validation_datagen.flow_from_directory(directory=VALIDATION_DIR,
                                                                batch_size=32,
                                                                class_mode='categorical',
                                                                target_size=(224, 224))
  return train_generator, validation_generator

train_dir = '/content/LeafDataset3k/train'
val_dir = '/content/LeafDataset3k/test'
train_generator, validation_generator = train_val_generators(train_dir, val_dir)

# from tensorflow.keras.applications.inception_v3 import InceptionV3
# def create_pre_trained_model():
#   pre_trained_model = InceptionV3(input_shape = (224, 224, 3),
#                                   include_top = False,
#                                   weights = 'imagenet')

#   # Make all the layers in the pre-trained model non-trainable
#   for layers in pre_trained_model.layers:
#     layers.trainable = False

#   return pre_trained_model

from keras.applications.vgg16 import VGG16
def create_pre_trained_model():
  pre_trained_model = VGG16(input_shape = (224, 224, 3),
                                  include_top = False,
                                  weights = 'imagenet')

  # Make all the layers in the pre-trained model non-trainable
  for layers in pre_trained_model.layers:
    layers.trainable = False

  return pre_trained_model

pre_trained_model = create_pre_trained_model()

# Print the model summary
pre_trained_model.summary()

acc_ckp_path = '/content/gdrive/MyDrive/Capstone/Leaf/Leaf_VGG16_v0_changeDataset.h5'
acc_checkpoint = tf.keras.callbacks.ModelCheckpoint(acc_ckp_path, monitor='accuracy', verbose=2,
    save_best_only=True, mode='auto', save_freq='epoch')

val_acc_ckp_path = '/content/gdrive/MyDrive/Capstone/Leaf/Leaf_VGG16_v0_changeDataset.h5'
val_acc_checkpoint = tf.keras.callbacks.ModelCheckpoint(val_acc_ckp_path, monitor='val_accuracy', verbose=2,
    save_best_only=True, mode='auto', save_freq='epoch')

class stopEpoch(tf.keras.callbacks.Callback):
  def on_epoch_end(self, epoch, logs={}):
    if(logs.get('accuracy') >= 0.95 and logs.get('val_accuracy') > 0.90):
      print("\nThreshold achieve!\n")
      self.model.stop_training = True

# def create_final_model(pre_trained_model, last_output):
#   x = layers.Flatten()(last_output)

#   x = layers.MaxPooling2D()(x)
#   x = layers.Dense(256, activation='relu')(x)
#   x = layers.Dropout(0.5)(x)
#   x = layers.MaxPooling2D()(x)
#   x = layers.Dense(32, activation='relu')(x)
#   x = layers.Dropout(0.3)(x)
#   x = tf.keras.layers.BatchNormalization()(x)
#   x = layers.Dense(5, activation='softmax')(x)

#   # Create the complete model by using the Model class
#   model = Model(inputs=pre_trained_model.input, outputs=x)
#   model.compile(optimizer = RMSprop(learning_rate=0.0001),
#                 loss = 'categorical_crossentropy',
#                 metrics = ['accuracy'])

#   return model

def create_final_model(pre_trained_model):
  x = layers.MaxPooling2D()(pre_trained_model.output)
  x = layers.Flatten()(x)
  x = layers.Dense(256, activation='relu')(x)
  x = layers.Dropout(0.5)(x)
  x = layers.Dense(128, activation='relu')(x)
  x = layers.Dropout(0.5)(x)
  x = layers.BatchNormalization()(x)
  x = layers.Dense(5, activation='softmax')(x)

  # Create the complete model by using the Model class
  model = Model(inputs=pre_trained_model.input, outputs=x)
  model.compile(optimizer = RMSprop(learning_rate=0.0001),
                loss = 'categorical_crossentropy',
                metrics = ['accuracy'])

  return model

last_layer = pre_trained_model.get_layer('mixed7')
print('last layer output shape: ', last_layer.output_shape)
last_output = last_layer.output

model = create_final_model(pre_trained_model)
history = model.fit(train_generator,
                    validation_data = validation_generator,
                    epochs = 100,
                    verbose = 2,
                    callbacks=[acc_checkpoint, val_acc_checkpoint, stopEpoch()])

from PIL import Image
import numpy as np
model = tf.keras.models.load_model('/content/gdrive/MyDrive/Capstone/Leaf/Leaf_VGG16_v0_changeDataset.h5')

def predict_disease(test_dir):
  list_test_image = os.listdir(test_dir)
  value=[]

  for i in range (len(list_test_image)-500):
    image = os.path.join(test_dir, list_test_image[i])
    image = np.array(Image.open(image).resize((224, 224)).convert("RGB"))/255.0
    image = np.expand_dims(image, axis=0)
    predict = model.predict(image)
    print(predict)
    max_index = np.argmax(predict)
    if(max_index == 0):
        pred = "Corn"
    elif(max_index == 1):
      pred = "Mango"
    elif(max_index == 2):
      pred = "Potato"
    elif(max_index == 3):
      pred = "Rice"
    elif(max_index == 4):
      pred = "Tomato"

    true_pred = test_dir.split("/")[-1]

    if(pred == true_pred):
      value.append(1)
    else:
      value.append(0)
  result = print(f'Accuracy of predict {true_pred} was ' + str(np.mean(value)))
  return result

predict_disease('/content/LeafDataset3k/test/Corn')