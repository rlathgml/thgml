import io
import os

# Imports the Google Cloud client library
from google.cloud import vision
from google.cloud.vision import types
from PIL import Image, ImageDraw

foods = ['Pizza', 'Food', 'Sushi', 'Baked goods', 'Snack', 'Cake', 'Dessert']

def localize_objects(path, member):
    
    credential_path = "/home/ubuntu/s03p23d205/lastmeal/obj-detection-291309-c7936c347a02.json"
    # credential_path = "./obj-detection-291309-c7936c347a02.json"
    os.environ['GOOGLE_APPLICATION_CREDENTIALS'] = credential_path
    """Localize objects in the local image.

    Args:
    path: The path to the local file.
    """
    client = vision.ImageAnnotatorClient()

    with open(path, 'rb') as image_file:
        content = image_file.read()
    image = vision.types.Image(content=content)

    objects = client.object_localization(
        image=image).localized_object_annotations

    j = 0

    paths = []
    for object_ in objects:
        if object_.name not in foods:
            continue

        img = Image.open(path)
        img_w, img_h = img.size

        i = 0
        
        x = []
        for vertex in object_.bounding_poly.normalized_vertices:
        
            if i%2 == 0:
                x.append((img_w*vertex.x, img_h*vertex.y))


            i += 1

        bbox = (x[0][0], x[0][1], x[1][0], x[1][1])
        # 가로 세로 시작, 가로 세로 끝
        crop_img = img.crop(bbox)

        fname = f"{j}.jpg"
        savename = './media/temp/'+ member + fname
        crop_img.save(savename)
        j += 1
        paths.append(savename)

    return paths




# localize_objects(os.path.abspath('test_image/1.jpg'), member)

##  crop 잘라서 저장

# import argparse
# import io

# from google.cloud import vision
# from google.cloud.vision import types
# from PIL import Image, ImageDraw

# def get_crop_hint(path):
#     """Detect crop hints on a single image and return the first result."""
#     client = vision.ImageAnnotatorClient()

#     with io.open(path, 'rb') as image_file:
#         content = image_file.read()

#     image = types.Image(content=content)

#     crop_hints_params = types.CropHintsParams(aspect_ratios=[1.77])
#     image_context = types.ImageContext(crop_hints_params=crop_hints_params)

#     response = client.crop_hints(image=image, image_context=image_context)
#     hints = response.crop_hints_annotation.crop_hints

#     # Get bounds for the first crop hint using an aspect ratio of 1.77.
#     vertices = hints[0].bounding_poly.vertices

#     return vertices

# def crop_to_hint(image_file):
#     """Crop the image using the hints in the vector list."""
#     vects = get_crop_hint(image_file)

#     im = Image.open(image_file)
#     im2 = im.crop([vects[0].x, vects[0].y,
#                   vects[2].x - 1, vects[2].y - 1])
#     im2.save('output-crop.jpg', 'JPEG')

# if __name__ == '__main__':
#     parser = argparse.ArgumentParser()
#     parser.add_argument('image_file', help='The image you\'d like to crop.')
#     parser.add_argument('mode', help='Set to "crop" or "draw".')
#     args = parser.parse_args()

#     if args.mode == 'crop':
#         crop_to_hint(args.image_file)
#     elif args.mode == 'draw':
#         draw_hint(args.image_file)