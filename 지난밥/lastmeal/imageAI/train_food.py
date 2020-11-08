import tensorflow as tf
from tensorflow import keras
import os
import numpy as np
import matplotlib.pyplot as plt
from tensorflow.keras.callbacks import EarlyStopping
from tensorflow.keras.layers import Dense, Dropout, Flatten, Conv2D, MaxPooling2D
from tqdm.notebook import tqdm

class_names=[
    'samgupsal',
    'churros',
    'bulgogi',
    'ojingeo_bokkeum',
    'samosa',
    'dakbokkeumtang',
    'spaghetti_bolognese',
    'galchijorim',
    'sashimi',
    'pork_chop',
    'spring_rolls',
    'panna_cotta',
    'jeyuk_bokkeum',
    'beef_tartare',
    'cannoli',
    'foie_gras',
    'tacos',
    'pad_thai',
    'poutine',
    'ramen',',',
    'pulled_pork_sandwich',
    'bibimbap',
    'beignets',
    'crab_cakes',
    'apple_pie',
    'risotto',
    'galbijjim',
    'paella',
    'soondubu_jjigae',
    'baby_back_ribs',
    'miso_soup',
    'frozen_yogurt',
    'club_sandwich',
    'carrot_cake',
    'falafel',
    'bread_pudding',
    'kimchi',
    'chicken_wings',
    'gnocchi',
    'caprese_salad',
    'creme_brulee',
    'escargots',
    'chocolate_cake',
    'tiramisu',
    'samgyetang',
    'garlic_bread',
    'scallops',
    'baklava',
    'edamame',
    'macaroni_and_cheese',
    'pancakes',
    'mussels',
    'beet_salad',
    'onion_rings',
    'red_velvet_cake',
    'steak',
    'grilled_salmon',
    'daegaejjim',
    'tuna_tartare',
    'deviled_eggs',
    'caesar_salad',
    'hummus',
    'fish_and_chips',
    'lasagna',
    'peking_duck',
    'guacamole',
    'strawberry_shortcake',
    'clam_chowder',
    'croque_madame',
    'french_onion_soup',
    'beef_carpaccio',
    'fried_rice',
    'donuts',
    'gyoza',
    'soondae',
    'ravioli',
    'fried_calamari',
    'spaghetti_carbonara',
    'omelette',
    'french_toast',
    'lobster_bisque',
    'ceviche',
    'bruschetta',
    'french_fries',
    'nangmyeon',
    'shrimp_and_grits',
    'gimbob',
    'filet_mignon',
    'hamburger',
    'dumplings',
    'chicken_curry',
    'sushi',
    'cheese_plate',
    'eggs_benedict',
    'cup_cakes',
    'takoyaki',
    'chocolate_mousse',
    'bossam',
    'breakfast_burrito',
    'hot_dog',
    'macarons',
    'waffles',
    'seaweed_salad',
    'greek_salad',
    'huevos_rancheros',
    'doenjang_chigae',
    'pizza',
    'chicken_quesadilla',
    'hot_and_sour_soup',
    'prime_rib',
    'cheesecake',
    'ice_cream',
    'ganjang_gejang',
    'jjajangmyeon',
    'pajeon',
    'grilled_cheese_sandwich',
    'pho',
    'lobster_roll_sandwich',
    'nachos',
    'oysters'
]

FLAGS = tf.app.flags.FLAGS
BINARY_FILES_DIR = './data'

tf.app.flags.DEFINE_integer('width',  227, '')
tf.app.flags.DEFINE_integer('height', 227, '')
tf.app.flags.DEFINE_integer('depth', 3, '')


FLAGS = tf.app.flags.FLAGS


def list_binary_files(folder):
    return [folder + '/' + d for d in os.listdir(folder)
            if os.path.isfile(os.path.join(folder, d))]


def read_raw_images(data_set):
    dirs = './data/' + data_set + '/'
    filename = list_binary_files(dirs)
    filename_queue = tf.train.string_input_producer(filename)

    if data_set is 'train':
        image_bytes = FLAGS.height * FLAGS.width * FLAGS.depth
        record_bytes = image_bytes + 1
        reader = tf.FixedLengthRecordReader(record_bytes=record_bytes)
        key, value = reader.read(filename_queue)
        record_bytes = tf.decode_raw(value, tf.uint8)
        label = tf.cast(tf.slice(record_bytes, [0], [1]), tf.int32)
        depth_major = tf.reshape(tf.slice(record_bytes, [1], [image_bytes]), [
                                 FLAGS.depth, FLAGS.height, FLAGS.width])
        uint8image = tf.transpose(depth_major, [1, 2, 0])
        return label, uint8image
    elif data_set is 'test':
        image_bytes = FLAGS.height * FLAGS.width * FLAGS.depth
        record_bytes = image_bytes + 1
        reader = tf.FixedLengthRecordReader(record_bytes=record_bytes)
        key, value = reader.read(filename_queue)
        record_bytes = tf.decode_raw(value, tf.uint8)
        depth_major = tf.reshape(tf.slice(record_bytes, [0], [image_bytes]),
                                 [FLAGS.depth, FLAGS.height, FLAGS.width])
        uint8image = tf.transpose(depth_major, [1, 2, 0])
        return uint8image
    elif data_set is 'validation':
        image_bytes = FLAGS.height * FLAGS.width * FLAGS.depth
        record_bytes = image_bytes + 1
        reader = tf.FixedLengthRecordReader(record_bytes=record_bytes)
        key, value = reader.read(filename_queue)
        record_bytes = tf.decode_raw(value, tf.uint8)
        label = tf.cast(tf.slice(record_bytes, [0], [1]), tf.int32)
        depth_major = tf.reshape(tf.slice(record_bytes, [1], [image_bytes]), [
                                 FLAGS.depth, FLAGS.height, FLAGS.width])
        uint8image = tf.transpose(depth_major, [1, 2, 0])
        return label, uint8image

def generate_image_and_label_batch(
        label,
        image,
        min_queue_examples,
        batch_size):
    num_preprocess_threads = 4
    if label is not None:
        label_batch, images = tf.train.batch(
            [label, image],
            batch_size=batch_size,
            num_threads=num_preprocess_threads,
            capacity=min_queue_examples * batch_size)
        return tf.reshape(label_batch, [batch_size]), images
    else:
        images = tf.train.batch(
            [image],
            batch_size=batch_size,
            num_threads=1,
            capacity=min_queue_examples * batch_size)
        return images


def distorted_inputs(batch_size):
    label, image = read_raw_images('train')
    reshaped_image = tf.cast(image, tf.float32)
    distorted_image = tf.random_crop(reshaped_image, [227, 227, FLAGS.depth])
    distorted_image = tf.image.random_flip_left_right(distorted_image)
    distorted_image = tf.image.random_brightness(distorted_image, max_delta=63)
    distorted_image = tf.image.random_contrast(distorted_image, lower=0.2, upper=1.8)
    float_image = tf.image.per_image_standardization(distorted_image)

    return generate_image_and_label_batch(label, float_image, 100, batch_size)

def validation_inputs(batch_size):
    label, image = read_raw_images('validation')
    reshaped_image = tf.cast(image, tf.float32)
    resized_image = tf.image.resize_image_with_crop_or_pad(reshaped_image, 227, 227)
    float_image = tf.image.per_image_standardization(resized_image)

    return generate_image_and_label_batch(label, float_image, 100, batch_size)

def inputs(batch_size):
    image = read_raw_images('test')
    reshaped_image = tf.cast(image, tf.float32)
    float_image = tf.image.per_image_standardization(reshaped_image)

    return generate_image_and_label_batch(None, float_image, 10, batch_size)


def get_data(data_set, batch_size):
    if data_set is 'train':
        return distorted_inputs(batch_size)
    elif data_set is 'test':
        return inputs(batch_size)
    elif data_set is 'validation':
        return validation_inputs(batch_size) 


train_labels, train_images = get_data('train', 20)
test_images = get_data('test',20)
validation_label, validation_image = get_data('validation', 20)

batch_size = 64
num_classes = 120
epochs = 1

############################모델구성############################
model = keras.Sequential([
    Conv2D(32,kernel_size=(3,3),padding='same', input_shape=train_images.shape[1:],
    activation=tf.nn.relu),
    MaxPooling2D(pool_size=(2,2)),
    Dropout(0.25),

    Conv2D(64,kernel_size=(3,3),padding='same', activation=tf.nn.relu),
    MaxPooling2D(pool_size=(2,2)),
    Dropout(0.25),

    Flatten(),
    Dense(64,activation=tf.nn.relu),
    Dropout(0.25),
    Dense(num_classes, activation=tf.nn.softmax)
])
model.summary()


model.compile(
    loss='categorical_crossentropy',
    optimizer='adam',
    metrics=['accuracy']
)

############################모델훈련############################ 과대적합을 막기위해 설정
#early_stopping = EarlyStopping(monitor='val_loss', patience=2)
## 이 밑으로 뻑나는데 도대체 왜뻑나는거징?
#history = model.fit(
#    train_images, train_labels,
#    epochs=epochs,
#    validation_data=(validation_image, validation_label),
#    shuffle=True,
#    steps_per_epoch=1,
#    callbacks=[early_stopping] 
#)

############################예측############################
predictions = model.predict(test_images, steps = 120) # 에러

def plot_image(i, predictions_array, true_label, img):
    predictions_array, true_label, img = predictions_array[i], true_label[i], img[i]
    plt.grid(False)
    plt.xticks([])
    plt.yticks([])

    plt.imshow(img, cmap=plt.cm.binary)

    predicted_label = np.argmax(predictions_array)
    if predicted_label == np.argmax(true_label):
        color = 'blue'
    else:
        color = 'red'

    plt.xlabel("{} {:2.0f}% ({})".format(class_names[predicted_label],100*np.max(predictions_array),class_names[np.argmax(true_label)]),color=color)

def plot_value_array(i, predictions_array, true_label):
    predictions_array, true_label = predictions_array[i], true_label[i] #
    plt.grid(False)
    plt.xticks([])
    plt.yticks([])
    thisplot = plt.bar(range(10), predictions_array, color="#777777")
    plt.ylim([0,1])
    predicted_label = np.argmax(predictions_array)

    thisplot[predicted_label].set_color('red')
    thisplot[np.argmax(true_label)].set_color('blue')

num_rows=5
num_cols=3
num_images = 3# num_rows*num_cols

plt.figure(figsize=1)
plt.subplot(1, 1, 1)
plot_image(1, predictions, " ", test_images)
plt.subplot(1, 1, 1)
plot_value_array(1, predictions, " ") #
plt.show()