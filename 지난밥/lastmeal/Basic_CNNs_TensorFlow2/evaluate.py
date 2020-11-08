import tensorflow as tf
from configuration import save_model_dir
from prepare_data import generate_datasets
from train import get_model, process_features
import os

dir_path = os.path.dirname(os.path.realpath(__file__))

if __name__ == '__main__':

    # GPU settings
    gpus = tf.config.list_physical_devices('GPU')
    if gpus:
        for gpu in gpus:
            tf.config.experimental.set_memory_growth(gpu, True)

    # get the original_dataset
    train_dataset, valid_dataset, test_dataset, train_count, valid_count, test_count = generate_datasets()
    # load the model
    model = get_model()
    real_path = dir_path+"/"+save_model_dir
    model.load_weights(filepath=real_path+"model")
    # model = tf.saved_model.load(save_model_dir)

    # Get the accuracy on the test set
    loss_object = tf.keras.metrics.SparseCategoricalCrossentropy()
    test_loss = tf.keras.metrics.Mean()
    test_accuracy = tf.keras.metrics.SparseCategoricalAccuracy()

    # @tf.function
    def test_step(images, labels):
        predictions = model(images, training=False)
        t_loss = loss_object(labels, predictions)
        test_loss(t_loss)
        test_accuracy(labels, predictions)

    for features in test_dataset:
        test_images, test_labels = process_features(features, data_augmentation=False)
        test_step(test_images, test_labels)