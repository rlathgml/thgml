from django.db import models
from accounts.models import Member


# 메뉴 모델
class Menu(models.Model):
    menuno = models.AutoField(db_column='menuNo', primary_key=True)  # Field name made lowercase.
    memberno = models.ForeignKey(Member, models.DO_NOTHING, db_column='memberNo')  # Field name made lowercase.
    menuimgpath = models.ImageField(db_column='menuImgPath', upload_to="menuimages/%Y/%m/", blank=True)  # Field name made lowercase.
    menulist = models.CharField(db_column='menuList', max_length=255)  # Field name made lowercase.
    eattime = models.DateTimeField(db_column='eatTime')  # Field name made lowercase.
    cal = models.IntegerField(db_column='cal', blank=True, null=True) 
    tan = models.IntegerField(db_column= 'tan', blank=True, null=True)
    dan = models.IntegerField(db_column= 'dan',blank=True, null=True)
    ji = models.IntegerField(db_column= 'ji',blank=True, null=True)
    dang = models.IntegerField(db_column= 'dang',blank=True, null=True)
    na = models.IntegerField(db_column= 'na',blank=True, null=True)
    cal = models.IntegerField(db_column= 'cal',blank=True, null=True)
    col = models.IntegerField(db_column= 'col',blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'menu'



# 영양소 모델
class Nutritional(models.Model):
   nutritionalno = models.AutoField(db_column='nutritionalNo', primary_key=True)  # Field name made lowercase.
   nutritionalname = models.CharField(db_column='nutritionalName', max_length=255, blank=True, null=True)  # Field name made lowercase.
   tan = models.IntegerField(blank=True, null=True)
   dan = models.IntegerField(blank=True, null=True)
   ji = models.IntegerField(blank=True, null=True)
   dang = models.IntegerField(blank=True, null=True)
   na = models.IntegerField(blank=True, null=True)
   cal = models.IntegerField(blank=True, null=True)
   col = models.IntegerField(blank=True, null=True)

   class Meta:
       managed = False
       db_table = 'nutritional'
