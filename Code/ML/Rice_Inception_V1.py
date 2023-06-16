# -*- coding: utf-8 -*-
"""Rice_InceptionV3._Renew.ipynb

Automatically generated by Colaboratory.

Original file is located at
    https://colab.research.google.com/drive/1EHBLSchv6vHL09PRXTU59ao89PQy-cQZ

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

"""# Data"""

source_dir = '/Users/bened/Python/Farmku/Rice'

# Training
training_path = os.path.join(source_dir, 'Training')
brownspot_training_path = os.path.join(training_path, 'BrownSpot')
healthy_training_path = os.path.join(training_path, 'Healthy')
hispa_training_path = os.path.join(training_path, 'Hispa')
leafblast_training_path = os.path.join(training_path, 'LeafBlast')

if not os.path.isdir(training_path):
  os.makedirs(training_path)
if not os.path.isdir(brownspot_training_path):
  os.makedirs(brownspot_training_path)
if not os.path.isdir(healthy_training_path):
  os.makedirs(healthy_training_path)
if not os.path.isdir(hispa_training_path):
  os.makedirs(hispa_training_path)
if not os.path.isdir(leafblast_training_path):
  os.makedirs(leafblast_training_path)

# Validation
validation_path = os.path.join(source_dir, 'Validation')
brownspot_validation_path = os.path.join(validation_path, 'BrownSpot')
healthy_validation_path = os.path.join(validation_path, 'Healthy')
hispa_validation_path = os.path.join(validation_path, 'Hispa')
leafblast_validation_path = os.path.join(validation_path, 'LeafBlast')


if not os.path.isdir(validation_path):
  os.makedirs(validation_path)
if not os.path.isdir(brownspot_validation_path):
  os.makedirs(brownspot_validation_path)
if not os.path.isdir(healthy_validation_path):
  os.makedirs(healthy_validation_path)
if not os.path.isdir(hispa_validation_path):
  os.makedirs(hispa_validation_path)
if not os.path.isdir(leafblast_validation_path):
  os.makedirs(leafblast_validation_path)

# Testing
testing_path = os.path.join(source_dir, 'testing')
brownspot_testing_path = os.path.join(testing_path, 'BrownSpot')
healthy_testing_path = os.path.join(testing_path, 'Healthy')
hispa_testing_path = os.path.join(testing_path, 'Hispa')
leafblast_testing_path = os.path.join(testing_path, 'LeafBlast')

if not os.path.isdir(testing_path):
  os.makedirs(testing_path)
if not os.path.isdir(brownspot_testing_path):
  os.makedirs(brownspot_testing_path)
if not os.path.isdir(healthy_testing_path):
  os.makedirs(healthy_testing_path)
if not os.path.isdir(hispa_testing_path):
  os.makedirs(hispa_testing_path)
if not os.path.isdir(leafblast_testing_path):
  os.makedirs(leafblast_testing_path)

"""# Dont Run This"""

diseases = ['BrownSpot', 'Healthy', 'Hispa', 'LeafBlast']
num_image_disease = [len(os.listdir(os.path.join(image_dir, x))) for x in diseases]
num_image_disease

for disease in diseases:
  disease_path = os.path.join(image_dir, disease)
  image_list = os.listdir(disease_path)
  shuffle_image_list = random.sample(image_list, len(image_list))
  if len(image_list) > 800:
    remove_list = shuffle_image_list[800:]
    for image in remove_list:
      image_path = os.path.join(disease_path, image)
      os.remove(image_path)
  else:
    n_new = 800 - len(image_list)
    add_list = shuffle_image_list[:n_new]
    for image in add_list:
      image_path = os.path.join(disease_path, image)
      image_name = image + '_1.jpg'
      new_image_path = os.path.join(disease_path, image_name)
      shutil.copy(image_path, new_image_path)

diseases = ['BrownSpot', 'Healthy', 'Hispa', 'LeafBlast']
num_image_disease = [len(os.listdir(os.path.join(image_dir, x))) for x in diseases]
num_image_disease

train_size = 0.8
diseases = ['BrownSpot', 'Healthy', 'Hispa', 'LeafBlast']
for disease in diseases:
  data_path = os.path.join(image_dir, disease)
  data = os.listdir(data_path)
  shuffle = random.sample(data, len(data))

  n_train = math.ceil(len(shuffle) * train_size)
  n_val = math.ceil(len(shuffle) * (1-train_size) / 2)
  n_test = len(shuffle) - n_train - n_val

  train_list = shuffle[:n_train]
  validation_list = shuffle[n_train:(n_train + n_val)]
  test_list = shuffle[(n_train + n_val):(n_train + n_val + n_test)]

  move_training_path = os.path.join(training_path, disease)
  move_validation_path = os.path.join(validation_path, disease)
  move_testing_path = os.path.join(testing_path, disease)

  for image_name in train_list:
    image_path = os.path.join(data_path, image_name)
    shutil.move(image_path, move_training_path)
  for image_name in validation_list:
    image_path = os.path.join(data_path, image_name)
    shutil.move(image_path, move_validation_path)
  for image_name in test_list:
    image_path = os.path.join(data_path, image_name)
    shutil.move(image_path, move_testing_path)

"""# Next"""

# Number of training, validation, and testing
print(f'Number of BrownSpot data: \n Training data: {len(os.listdir(brownspot_training_path))} \n Validation data: {len(os.listdir(brownspot_validation_path))}')
print(f'Number of Healthy data: \n Training data: {len(os.listdir(healthy_training_path))} \n Validation data: {len(os.listdir(healthy_validation_path))}')
print(f'Number of Hispa data: \n Training data: {len(os.listdir(hispa_training_path))} \n Validation data: {len(os.listdir(hispa_validation_path))}')
print(f'Number of LeafBlast data: \n Training data: {len(os.listdir(leafblast_training_path))} \n Validation data: {len(os.listdir(leafblast_validation_path))}')

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

def custom_model(last_layer = base_model.output):
  x = tf.keras.layers.Flatten()(last_layer)
  x = tf.keras.layers.Dense(128, activation='relu')(x)
  x = tf.keras.layers.BatchNormalization()(x)
  x = tf.keras.layers.Dropout(0.2)(x)
  x = tf.keras.layers.Dense(16, activation='relu')(x)
  x = tf.keras.layers.Dense(4, activation='softmax')(x)

  model = tf.keras.Model(inputs=base_model.input, outputs=x)

  model.compile(optimizer=tf.keras.optimizers.Adam(),
                loss='categorical_crossentropy',
                metrics=['accuracy'])
  return model

custom_model = custom_model()
custom_model.summary()

acc_ckp_path = '/Users/bened/Python/Farmku/Rice/saved_model/model_InceptionV3_new/best_acc.h5'
acc_checkpoint = tf.keras.callbacks.ModelCheckpoint(acc_ckp_path, monitor='accuracy', verbose=2,
    save_best_only=True, mode='auto', save_freq='epoch')

val_acc_ckp_path = '/Users/bened/Python/Farmku/Rice/saved_model/model_InceptionV3_new/best_val_acc.h5'
val_acc_checkpoint = tf.keras.callbacks.ModelCheckpoint(val_acc_ckp_path, monitor='val_accuracy', verbose=2,
    save_best_only=True, mode='auto', save_freq='epoch')

class stopEpoch(tf.keras.callbacks.Callback):
  def on_epoch_end(self, epoch, logs={}):
    if(logs.get('accuracy') >= 0.95 or (logs.get('accuracy') > 0.92 and logs.get('val_accuracy') > 0.90)):
      print("\nThreshold achieve!\n")
      self.model.stop_training = True

current_model_weights = tf.keras.models.load_model('/Users/bened/Python/Farmku/Rice/saved_model/model_InceptionV3_new/best_acc.h5')
custom_model.set_weights = current_model_weights.get_weights

history = custom_model.fit(training_dataset,
                               validation_data = validation_dataset,
                               epochs = 100,
                               verbose = 1,
                               callbacks=[acc_checkpoint, val_acc_checkpoint, stopEpoch()])

custom_model_7.save('/Users/bened/Python/Farmku/Rice/saved_model/latest_further_model.h5')

"""# Hosted"""

import os
import zipfile
import shutil
import zipfile
import random
import math
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import tensorflow as tf
from tensorflow.keras.preprocessing.image import ImageDataGenerator

from google.colab import drive
drive.mount('/content/drive')

source_dir = '/content/drive/MyDrive/Well Played'

# Training
training_path = os.path.join(source_dir, 'Training')
brownspot_training_path = os.path.join(training_path, 'BrownSpot')
healthy_training_path = os.path.join(training_path, 'Healthy')
hispa_training_path = os.path.join(training_path, 'Hispa')
leafblast_training_path = os.path.join(training_path, 'LeafBlast')

if not os.path.isdir(training_path):
  os.makedirs(training_path)
if not os.path.isdir(brownspot_training_path):
  os.makedirs(brownspot_training_path)
if not os.path.isdir(healthy_training_path):
  os.makedirs(healthy_training_path)
if not os.path.isdir(hispa_training_path):
  os.makedirs(hispa_training_path)
if not os.path.isdir(leafblast_training_path):
  os.makedirs(leafblast_training_path)

# Validation
validation_path = os.path.join(source_dir, 'Validation')
brownspot_validation_path = os.path.join(validation_path, 'BrownSpot')
healthy_validation_path = os.path.join(validation_path, 'Healthy')
hispa_validation_path = os.path.join(validation_path, 'Hispa')
leafblast_validation_path = os.path.join(validation_path, 'LeafBlast')


if not os.path.isdir(validation_path):
  os.makedirs(validation_path)
if not os.path.isdir(brownspot_validation_path):
  os.makedirs(brownspot_validation_path)
if not os.path.isdir(healthy_validation_path):
  os.makedirs(healthy_validation_path)
if not os.path.isdir(hispa_validation_path):
  os.makedirs(hispa_validation_path)
if not os.path.isdir(leafblast_validation_path):
  os.makedirs(leafblast_validation_path)

# Number of training, validation, and testing
print(f'Number of BrownSpot data: \n Training data: {len(os.listdir(brownspot_training_path))} \n Validation data: {len(os.listdir(brownspot_validation_path))}')
print(f'Number of Healthy data: \n Training data: {len(os.listdir(healthy_training_path))} \n Validation data: {len(os.listdir(healthy_validation_path))}')
print(f'Number of Hispa data: \n Training data: {len(os.listdir(hispa_training_path))} \n Validation data: {len(os.listdir(hispa_validation_path))}')
print(f'Number of LeafBlast data: \n Training data: {len(os.listdir(leafblast_training_path))} \n Validation data: {len(os.listdir(leafblast_validation_path))}')

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

img_shape = img_size + (3,)
base_model = tf.keras.applications.InceptionV3(input_shape=img_shape,
                                               include_top=False,
                                               weights='imagenet')

for layer in base_model.layers:
  layer.trainable = False

def custom_model(last_layer = base_model.output):
  x = tf.keras.layers.Flatten()(last_layer)
  x = tf.keras.layers.Dense(128, kernel_regularizer=tf.keras.regularizers.l1_l2(l1=0.001, l2=0.001))(x)
  x = tf.keras.layers.BatchNormalization()(x)
  x = tf.keras.layers.Activation('relu')(x)
  x = tf.keras.layers.Dropout(0.25)(x)
  x = tf.keras.layers.Dense(16, kernel_regularizer=tf.keras.regularizers.l1_l2(l1=0.001, l2=0.001))(x)
  x = tf.keras.layers.BatchNormalization()(x)
  x = tf.keras.layers.Activation('relu')(x)
  x = tf.keras.layers.Dropout(0.25)(x)
  x = tf.keras.layers.Dense(4, activation='softmax')(x)

  model = tf.keras.Model(inputs=base_model.input, outputs=x)

  model.compile(optimizer=tf.keras.optimizers.Adam(),
                loss='categorical_crossentropy',
                metrics=['accuracy'])
  return model

custom_model = custom_model()
custom_model.summary()

acc_ckp_path = '/content/drive/MyDrive/Well Played/Rice_Best_Acc.h5'
acc_checkpoint = tf.keras.callbacks.ModelCheckpoint(acc_ckp_path, monitor='accuracy', verbose=2,
    save_best_only=True, mode='auto', save_freq='epoch')

val_acc_ckp_path = '/content/drive/MyDrive/Well Played/Rice_Best_Val_Acc.h5'
val_acc_checkpoint = tf.keras.callbacks.ModelCheckpoint(val_acc_ckp_path, monitor='val_accuracy', verbose=2,
    save_best_only=True, mode='auto', save_freq='epoch')

class stopEpoch(tf.keras.callbacks.Callback):
  def on_epoch_end(self, epoch, logs={}):
    if(logs.get('accuracy') >= 0.95 or (logs.get('accuracy') > 0.92 and logs.get('val_accuracy') > 0.90)):
      print("\nThreshold achieve!\n")
      self.model.stop_training = True

history = custom_model.fit(training_dataset,
                               validation_data = validation_dataset,
                               epochs = 100,
                               verbose = 1,
                               callbacks=[acc_checkpoint, val_acc_checkpoint, stopEpoch()])