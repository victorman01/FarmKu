# -*- coding: utf-8 -*-
"""Potato_InceptionV3_Renew.ipynb

Automatically generated by Colaboratory.

Original file is located at
    https://colab.research.google.com/drive/1BWMM_Nh2i_8eN7by2pwp1pjyp6-LZ387

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

source_dir = '/Users/bened/Python/Farmku/Potato'

# Training
training_path = os.path.join(source_dir, 'training')
earlyblight_training_path = os.path.join(training_path, 'EarlyBlight')
healthy_training_path = os.path.join(training_path, 'Healthy')
lateblight_training_path = os.path.join(training_path, 'LateBlight')

if not os.path.isdir(training_path):
  os.makedirs(training_path)
if not os.path.isdir(earlyblight_training_path):
  os.makedirs(earlyblight_training_path)
if not os.path.isdir(healthy_training_path):
  os.makedirs(healthy_training_path)
if not os.path.isdir(lateblight_training_path):
  os.makedirs(lateblight_training_path)

# Validation
validation_path = os.path.join(source_dir, 'validation')
earlyblight_validation_path = os.path.join(validation_path, 'EarlyBlight')
healthy_validation_path = os.path.join(validation_path, 'Healthy')
lateblight_validation_path = os.path.join(validation_path, 'LateBlight')

if not os.path.isdir(validation_path):
  os.makedirs(validation_path)
if not os.path.isdir(earlyblight_validation_path):
  os.makedirs(earlyblight_validation_path)
if not os.path.isdir(healthy_validation_path):
  os.makedirs(healthy_validation_path)
if not os.path.isdir(lateblight_validation_path):
  os.makedirs(lateblight_validation_path)

# Number of training, validation, and testing
print(f'Number of Early Blight data: \n Training data: {len(os.listdir(earlyblight_training_path))} \n Validation data: {len(os.listdir(earlyblight_validation_path))}')
print(f'Number of Healthy data: \n Training data: {len(os.listdir(healthy_training_path))} \n Validation data: {len(os.listdir(healthy_validation_path))}')
print(f'Number of Late Blight data: \n Training data: {len(os.listdir(lateblight_training_path))} \n Validation data: {len(os.listdir(lateblight_validation_path))}')

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

base_model.summary()

for layer in base_model.layers:
  layer.trainable = False

disease = ['EarlyBlight', 'Healthy', 'LateBlight']
num_class_labels = [len(os.listdir(earlyblight_training_path)),
                    len(os.listdir(healthy_training_path)),
                    len(os.listdir(lateblight_training_path))]
num_class_labels = num_class_labels / np.sum(num_class_labels)
class_weights_dict = {i: num_class_labels[i] for i in range(len(disease))}

class_weights_dict

def custom_model(last_layer = base_model.output):
  x = tf.keras.layers.Flatten()(last_layer)
  x = tf.keras.layers.Dense(128, activation='relu')(x)
  x = tf.keras.layers.BatchNormalization()(x)
  x = tf.keras.layers.Dropout(0.2)(x)
  x = tf.keras.layers.Dense(16, activation='softmax')(x)
  x = tf.keras.layers.Dense(3, activation='softmax')(x)

  model = tf.keras.Model(inputs=base_model.input, outputs=x)

  model.compile(optimizer=tf.keras.optimizers.Adam(),
                loss='categorical_crossentropy',
                metrics=['accuracy'])
  return model

custom_model = custom_model()
custom_model.summary()

acc_ckp_path = '/Users/bened/Python/Farmku/Potato/saved_model/model_InceptionV3/best_acc_new.h5'
acc_checkpoint = tf.keras.callbacks.ModelCheckpoint(acc_ckp_path, monitor='accuracy', verbose=2,
    save_best_only=True, mode='auto', save_freq='epoch')

val_acc_ckp_path = '/Users/bened/Python/Farmku/Potato/saved_model/model_InceptionV3/best_val_acc_new.h5'
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
                           class_weight = class_weights_dict,
                           callbacks=[acc_checkpoint, val_acc_checkpoint, stopEpoch()])

class ExtendStopEpoch(tf.keras.callbacks.Callback):
  def on_epoch_end(self, epoch, logs={}):
    if(logs.get('accuracy') >= 0.975 or (logs.get('accuracy') > 0.95 and logs.get('val_accuracy') > 0.92)):
      print("\nThreshold achieve!\n")
      self.model.stop_training = True

history2 = custom_model.fit(training_dataset,
                            validation_data = validation_dataset,
                               epochs = 200,
                               verbose = 1,
                            class_weight = class_weights_dict,
                               callbacks=[acc_checkpoint, val_acc_checkpoint, ExtendStopEpoch()])

custom_model.save('/Users/bened/Python/Farmku/Potato/saved_model/model_InceptionV3/best_model_Renew.h5')