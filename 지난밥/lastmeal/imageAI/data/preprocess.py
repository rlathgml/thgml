from sklearn.model_selection import train_test_split
from PIL import Image
import config
import os
import csv
import numpy as np
import sys
#sys.path.append(os.path.dirname(os.path.dirname(os.path.dirname(os.path.abspath(os.path.dirname(__file__))))))


# Req. 3-1	이미지 경로 및 캡션 불러오기
def get_path_caption():
    f = open(config.args.caption_file_path, 'r', encoding='UTF-8')
    rdr = csv.reader(f)
    image_path = []
    caption = []

    for line in rdr:
        linesplit = ''.join(line)
        word = linesplit.split('|')
        image_path.append(word[0])
        caption.append(word[2])
    f.close()
    return image_path, caption


# Req. 3-2 훈련시킬 데이터와 테스트 데이터 분리
def dataset_split_save(image_path, caption):
    X_train, X_test, Y_train, Y_test = train_test_split(image_path, caption, test_size=0.2, random_state=0)
    
    np.savez('./datasets/train', x=X_train, y=Y_train)  # 훈련시키는 데이터
    np.savez('./datasets/test', X_test, Y_test)  # 테스트 시키는 데이터

    return './datasets/train.npz', './datasets/test.npz'


# Req. 3-3   저장된 데이터셋 불러오기
def get_data_file(train_dataset_path, val_dataset_path):
    data = np.load(train_dataset_path)

    img_paths = data['x'][0]
    captions = data['y'][0]

    return img_paths, captions


# Req. 3-4   데이터 샘플링
def sampling_data():
    pass
