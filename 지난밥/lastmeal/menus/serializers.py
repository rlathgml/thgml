from rest_framework import serializers
from .models import Menu
from .models import Nutritional


class MenuSerializer(serializers.ModelSerializer):
    # use_url 을 True 로 선언해 주면 output으로 URL String 이 내려오고 False 이면 File Name 이 output 으로 나온다
    menuimgpath = serializers.ImageField(use_url=True)
    
    class Meta:
        model = Menu
        fields = ('menuimgpath', 'menulist', 'eattime', 'tan', 'dan', 'ji', 'dang', 'na', 'cal', 'col', )


class NutritionalSerializer(serializers.ModelSerializer):
    class Meta:
        model = Nutritional
        fields = ('nutritionalname', 'tan', 'dan', 'ji', 'dang', 'na', 'cal', 'col', )

