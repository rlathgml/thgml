import tensorflow as tf
from configuration import save_model_dir, test_image_dir
from prepare_data import load_and_preprocess_image
from train import get_model
import os

dir_path = os.path.dirname(os.path.realpath(__file__))

def get_single_picture_prediction(model, picture_dir):
    image_tensor = load_and_preprocess_image(tf.io.read_file(filename=picture_dir), data_augmentation=False)
    image = tf.expand_dims(image_tensor, axis=0)
    prediction = model(image, training=False)
    pred_class = tf.math.argmax(prediction, axis=-1)
    return pred_class

class_name=[
    '비빔밥',
    '만두',
    '된장국',
    '피자',
    '레드벨벳',
    '초밥',
    '티라미수'
]
    
if __name__ == '__main__':
    # GPU settings
    gpus = tf.config.list_physical_devices('GPU')
    if gpus:
        for gpu in gpus:
            tf.config.experimental.set_memory_growth(gpu, True)

    # load the model
    model = get_model()
    model.load_weights(filepath=save_model_dir+"model")
    num_rows=1
    num_cols=5
    for i in range(5):
        real_path =dir_path+test_image_dir+f"{i}"+".jpg" 
        pred_class = get_single_picture_prediction(model, real_path)