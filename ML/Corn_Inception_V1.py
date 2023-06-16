# -*- coding: utf-8 -*-
"""Corn_InceptionV3_Renew.ipynb

Automatically generated by Colaboratory.

Original file is located at
    https://colab.research.google.com/drive/1eRmcVQQwkD47l8lGFw7YZ3VBT5E6m6Vx

# Library
"""

import os
import shutil
import zipfile
import random
import math
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import tensorflow as tf
from tensorflow.keras.preprocessing.image import ImageDataGenerator
import tensorflow_hub as hub
import cv2
from PIL import Image
import cv2
from rembg import remove
from sklearn.utils.class_weight import compute_class_weight

"""# Data"""

def remove_background(image_path):
    image = cv2.imread(image_path)
    mask = np.zeros(image.shape[:2], np.uint8)
    background_model = np.zeros((1, 65), np.float64)
    foreground_model = np.zeros((1, 65), np.float64)
    rectangle = (50, 50, image.shape[1] - 50, image.shape[0] - 50)
    cv2.grabCut(image, mask, rectangle, background_model, foreground_model, 5, cv2.GC_INIT_WITH_RECT)
    mask = np.where((mask == 2) | (mask == 0), 0, 1).astype('uint8')
    image = image * mask[:, :, np.newaxis]
    return image

def remove_background_from_directory(directory):
  diseases = os.listdir(directory)
  for disease in diseases:
    disease_dir = os.path.join(directory, disease)
    ind = 0
    for filename in os.listdir(disease_dir):
      ind += 1
      if filename.endswith(".jpg") or filename.endswith(".png") or filename.endswith(".JPG"):
            input_path = os.path.join(disease_dir, filename)
            output_filename = "masked_" + str(ind) + '.jpg'
            output_path = os.path.join(disease_dir, output_filename)

            with open(input_path, 'rb') as i:
              with open(output_path, 'wb') as o:
                  input = i.read()
                  output = remove(input)
                  o.write(output)

    print(f'All images in {disease} folder is done')
  return('Done')

source_dir = '/Users/bened/Python/Farmku/Corn'

# Training
training_path = os.path.join(source_dir, 'training')
cercospora_training_path = os.path.join(training_path, 'Cercospora')
healthy_training_path = os.path.join(training_path, 'Healthy')
commonrust_training_path = os.path.join(training_path, 'CommonRust')
leafblight_training_path = os.path.join(training_path, 'NorthernLeafBlight')

if not os.path.isdir(training_path):
  os.makedirs(training_path)
if not os.path.isdir(cercospora_training_path):
  os.makedirs(cercospora_training_path)
if not os.path.isdir(healthy_training_path):
  os.makedirs(healthy_training_path)
if not os.path.isdir(commonrust_training_path):
  os.makedirs(commonrust_training_path)
if not os.path.isdir(leafblight_training_path):
  os.makedirs(leafblight_training_path)

# Validation
validation_path = os.path.join(source_dir, 'validation')
cercospora_validation_path = os.path.join(validation_path, 'Cercospora')
healthy_validation_path = os.path.join(validation_path, 'Healthy')
commonrust_validation_path = os.path.join(validation_path, 'CommonRust')
leafblight_validation_path = os.path.join(validation_path, 'NorthernLeafBlight')

if not os.path.isdir(validation_path):
  os.makedirs(validation_path)
if not os.path.isdir(cercospora_validation_path):
  os.makedirs(cercospora_validation_path)
if not os.path.isdir(healthy_validation_path):
  os.makedirs(healthy_validation_path)
if not os.path.isdir(commonrust_validation_path):
  os.makedirs(commonrust_validation_path)
if not os.path.isdir(leafblight_validation_path):
  os.makedirs(leafblight_validation_path)

remove_background_from_directory(training_path)
remove_background_from_directory(validation_path)

remove_background_from_directory('/Users/bened/Python/Farmku/Corn_NoBG/testing')

"""# Dont Run This"""

diseases = ['Cercospora', 'CommonRust', 'Healthy', 'NorthernLeafBlight']
num_image_disease = [len(os.listdir(os.path.join(source_dir, x))) for x in diseases]
num_image_disease

for disease in diseases:
  disease_path = os.path.join(source_dir, disease)
  image_list = os.listdir(disease_path)
  shuffle_image_list = random.sample(image_list, len(image_list))
  if len(image_list) >= 1000:
    remove_list = shuffle_image_list[1000:]
    for image in remove_list:
      image_path = os.path.join(disease_path, image)
      os.remove(image_path)

diseases = ['Cercospora', 'CommonRust', 'Healthy', 'NorthernLeafBlight']
num_image_disease = [len(os.listdir(os.path.join(source_dir, x))) for x in diseases]
num_image_disease

train_size = 0.8
for disease in diseases:
  data_path = os.path.join(source_dir, disease)
  data = os.listdir(data_path)
  shuffle = random.sample(data, len(data))

  n_train = math.ceil(len(shuffle) * train_size)
  n_val = math.ceil(len(shuffle) * (1-train_size))

  train_list = shuffle[:n_train]
  validation_list = shuffle[n_train:(n_train + n_val)]

  move_training_path = os.path.join(training_path, disease)
  move_validation_path = os.path.join(validation_path, disease)

  for image_name in train_list:
    image_path = os.path.join(data_path, image_name)
    shutil.move(image_path, move_training_path)
  for image_name in validation_list:
    image_path = os.path.join(data_path, image_name)
    shutil.move(image_path, move_validation_path)

"""# Next"""

source_dir = '/Users/bened/Python/Farmku/Corn'

# Training
training_path = os.path.join(source_dir, 'training')
cercospora_training_path = os.path.join(training_path, 'Cercospora')
healthy_training_path = os.path.join(training_path, 'Healthy')
commonrust_training_path = os.path.join(training_path, 'CommonRust')
leafblight_training_path = os.path.join(training_path, 'NorthernLeafBlight')

if not os.path.isdir(training_path):
  os.makedirs(training_path)
if not os.path.isdir(cercospora_training_path):
  os.makedirs(cercospora_training_path)
if not os.path.isdir(healthy_training_path):
  os.makedirs(healthy_training_path)
if not os.path.isdir(commonrust_training_path):
  os.makedirs(commonrust_training_path)
if not os.path.isdir(leafblight_training_path):
  os.makedirs(leafblight_training_path)

# Validation
validation_path = os.path.join(source_dir, 'validation')
cercospora_validation_path = os.path.join(validation_path, 'Cercospora')
healthy_validation_path = os.path.join(validation_path, 'Healthy')
commonrust_validation_path = os.path.join(validation_path, 'CommonRust')
leafblight_validation_path = os.path.join(validation_path, 'NorthernLeafBlight')

if not os.path.isdir(validation_path):
  os.makedirs(validation_path)
if not os.path.isdir(cercospora_validation_path):
  os.makedirs(cercospora_validation_path)
if not os.path.isdir(healthy_validation_path):
  os.makedirs(healthy_validation_path)
if not os.path.isdir(commonrust_validation_path):
  os.makedirs(commonrust_validation_path)
if not os.path.isdir(leafblight_validation_path):
  os.makedirs(leafblight_validation_path)

# Number of training, validation, and testing
print(f'Number of Cercospora data: \n Training data: {len(os.listdir(cercospora_training_path))} \n Validation data: {len(os.listdir(cercospora_validation_path))}')
print(f'Number of Healthy data: \n Training data: {len(os.listdir(healthy_training_path))} \n Validation data: {len(os.listdir(healthy_validation_path))}')
print(f'Number of Common Rust data: \n Training data: {len(os.listdir(commonrust_training_path))} \n Validation data: {len(os.listdir(commonrust_validation_path))}')
print(f'Number of Northern Leaf Blight data: \n Training data: {len(os.listdir(leafblight_training_path))} \n Validation data: {len(os.listdir(leafblight_validation_path))}')

img_size = (224, 224)

training_datagen = ImageDataGenerator(rescale=1/255,
                                      rotation_range = 40,
                                      width_shift_range = 0.2,
                                      height_shift_range = 0.2,
                                      shear_range = 0.2,
                                      zoom_range = 0.2,
                                      horizontal_flip = True)
validation_datagen = ImageDataGenerator(rescale=1/255)

training_dataset = training_datagen.flow_from_directory(training_path,
                                                          shuffle=True,
                                                          batch_size=64,
                                                          target_size=img_size)
validation_dataset = validation_datagen.flow_from_directory(validation_path,
                                                            shuffle=True,
                                                            batch_size=32,
                                                            target_size=img_size)

"""# Model"""

img_shape = img_size + (3,)
base_model = tf.keras.applications.InceptionV3(input_shape=img_shape,
                                               include_top=False,
                                               weights='imagenet')

for layer in base_model.layers:
  layer.trainable = False

disease = ['Cercospora', 'CommonRust', 'Healthy', 'NorthernLeafBlight']
num_class_labels = [len(os.listdir(cercospora_training_path)),
                    len(os.listdir(commonrust_training_path)),
                    len(os.listdir(healthy_training_path)),
                    len(os.listdir(leafblight_training_path))]
num_class_labels = num_class_labels / np.sum(num_class_labels)
class_weights_dict = {i: num_class_labels[i] for i in range(len(disease))}

class_weights_dict

def custom_model(last_layer = base_model.output):
  x = tf.keras.layers.Flatten()(last_layer)
  x = tf.keras.layers.Dense(128, activation='relu')(x)
  x = tf.keras.layers.BatchNormalization()(x)
  x = tf.keras.layers.Dropout(0.25)(x)
  x = tf.keras.layers.Dense(32, activation='relu', kernel_regularizer=tf.keras.regularizers.l1_l2(l1=0.01, l2=0.01))(x)
  x = tf.keras.layers.Dense(16, activation='relu', kernel_regularizer=tf.keras.regularizers.l1_l2(l1=0.01, l2=0.01))(x)
  x = tf.keras.layers.Dense(4, activation='softmax')(x)

  model = tf.keras.Model(inputs=base_model.input, outputs=x)

  model.compile(optimizer=tf.keras.optimizers.Adam(),
                loss='categorical_crossentropy',
                metrics=['accuracy'])
  return model

custom_model = custom_model()
custom_model.summary()

acc_ckp_path = '/Users/bened/Python/Farmku/Corn/saved_model/model_InceptionV3/best_acc_new.h5'
acc_checkpoint = tf.keras.callbacks.ModelCheckpoint(acc_ckp_path, monitor='accuracy', verbose=2,
    save_best_only=True, mode='auto', save_freq='epoch')

val_acc_ckp_path = '/Users/bened/Python/Farmku/Corn/saved_model/model_InceptionV3/best_val_acc_new.h5'
val_acc_checkpoint = tf.keras.callbacks.ModelCheckpoint(val_acc_ckp_path, monitor='val_accuracy', verbose=2,
    save_best_only=True, mode='auto', save_freq='epoch')

class stopEpoch(tf.keras.callbacks.Callback):
  def on_epoch_end(self, epoch, logs={}):
    if(logs.get('accuracy') >= 0.975 or (logs.get('accuracy') > 0.95 and logs.get('val_accuracy') > 0.92)):
      print("\nThreshold achieve!\n")
      self.model.stop_training = True

history = custom_model.fit(training_dataset,
                           validation_data = validation_dataset,
                           epochs = 200,
                           verbose = 1,
                           class_weight=class_weights_dict,
                           callbacks=[acc_checkpoint, val_acc_checkpoint, stopEpoch()])

saved = tf.keras.models.load_model('/Users/bened/Python/Farmku/Corn/saved_model/model_InceptionV3/best_acc_new.h5')
custom_model.set_weights = saved.get_weights

history = custom_model.fit(training_dataset,
                           validation_data = validation_dataset,
                           epochs = 200,
                           verbose = 1,
                           class_weight=class_weights_dict,
                           callbacks=[acc_checkpoint, val_acc_checkpoint, stopEpoch()])

custom_model.save('/Users/bened/Python/Farmku/Corn/saved_model/model_InceptionV3/best_regularized_model_3.h5')

corn_model = custom_model
def corn_predict(dir):
  list_image = os.listdir(dir)
  correct = []
  for element in list_image:
    image_dir = os.path.join(dir, element)
    image = Image.open(image_dir).resize((224, 224))
    image = np.array(image.convert('RGB')) / 255.0
    image = np.expand_dims(image, axis=0)

    pred = corn_model.predict(image, verbose=0)
    if np.argmax(pred[0]) == 0:
      cat = 'Cercospora'
    elif np.argmax(pred[0]) == 1:
      cat = 'CommonRust'
    elif np.argmax(pred[0]) == 2:
      cat = 'Healthy'
    elif np.argmax(pred[0]) == 3:
      cat = 'NorthernLeafBlight'

    disease = dir.split('/')[-1]
    if cat == disease:
      correct.append(1)
    else:
      correct.append(0)
  return print(f'Model accuracy to predict {disease}: {np.mean(correct)}')

print('This is testing! \n')
corn_predict('/Users/bened/Python/Farmku/Corn/testing/Healthy')
corn_predict('/Users/bened/Python/Farmku/Corn/testing/Cercospora')
corn_predict('/Users/bened/Python/Farmku/Corn/testing/CommonRust')
corn_predict('/Users/bened/Python/Farmku/Corn/testing/NorthernLeafBlight')

datagen = ImageDataGenerator(rescale=1/255,
                                      rotation_range = 40,
                                      width_shift_range = 0.2,
                                      height_shift_range = 0.2,
                                      shear_range = 0.2,
                                      zoom_range = 0.2,
                                      horizontal_flip = True)
dataset = datagen.flow_from_directory('/Users/bened/Python/Farmku/Corn/testing',
                                                          shuffle=True,
                                                          batch_size=64,
                                                          target_size=(224,224))
corn_model.evaluate(dataset)

"""# No Background"""

testing_path = '/Users/bened/Python/Farmku/Corn_NoBG/testing'
remove_background_from_directory(testing_path)

corn_model = custom_model

# With remove bg
def corn_predict(dir):
  list_image = os.listdir(dir)
  correct = []
  for element in list_image:
    image_dir = os.path.join(dir, element)
    image = remove_background(image_dir)

    pred = corn_model.predict(image, verbose=0)
    if np.argmax(pred[0]) == 0:
      cat = 'Cercospora'
    elif np.argmax(pred[0]) == 1:
      cat = 'CommonRust'
    elif np.argmax(pred[0]) == 2:
      cat = 'Healthy'
    elif np.argmax(pred[0]) == 3:
      cat = 'NorthernLeafBlight'

    disease = dir.split('/')[-1]
    if cat == disease:
      correct.append(1)
    else:
      correct.append(0)
  return print(f'Model accuracy to predict {disease}: {np.mean(correct)}')

print('This is testing! \n')
corn_predict('/Users/bened/Python/Farmku/Corn_NoBG/testing/Healthy')
corn_predict('/Users/bened/Python/Farmku/Corn_NoBG/testing/Cercospora')
corn_predict('/Users/bened/Python/Farmku/Corn_NoBG/testing/CommonRust')
corn_predict('/Users/bened/Python/Farmku/Corn_NoBG/testing/NorthernLeafBlight')