from datetime import datetime
import os
import matplotlib.pyplot as plt
import numpy as np
import tensorflow as tf
import sys
#sys.path.append(os.path.dirname(os.path.dirname(os.path.dirname(os.path.abspath(os.path.dirname(__file__))))))
import config
import matplotlib.image as img

# Req. 2-2	세팅 값 저장


def save_config():
    np.save('.\\utils\\data.npy', config.args)


# Req. 4-1	이미지와 캡션 시각화
def visualize_img_caption(img_paths, captions):
    image = img.imread('.\\datasets\\images\\' + img_paths)
    plt.title(captions)
    plt.imshow(image)
    plt.show()
