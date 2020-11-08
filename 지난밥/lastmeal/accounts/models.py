# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#   * Rearrange models' order
#   * Make sure each model has one field with primary_key=True
#   * Make sure each ForeignKey and OneToOneField has `on_delete` set to the desired behavior
#   * Remove `managed = False` lines if you wish to allow Django to create, modify, and delete the table
# Feel free to rename the models, but don't rename db_table values or field names.
from django.db import models
from django.contrib.auth.models import AbstractUser

# class Checklist(models.Model):
#    # Field name made lowercase.
#    checkno = models.AutoField(db_column='checkNo', primary_key=True)
#    # Field name made lowercase.
#    memberno = models.ForeignKey(
#        'Member', models.DO_NOTHING, db_column='memberNo')
#    # diseaseno = models.ForeignKey('Disease', models.DO_NOTHING, db_column='diseaseNo')  # Field name made lowercase.
#
#    class Meta:
#        managed = False
#        db_table = 'checkList'


class Member(models.Model):
    # Field name made lowercase.
    memberno = models.AutoField(db_column='memberNo', primary_key=True)
    email = models.CharField(max_length=255)
    password = models.CharField(max_length=255, null=True)
    # Field name made lowercase.
    profilepath = models.CharField(
        db_column='profilePath', max_length=255, blank=True, null=True)
    nickname = models.CharField(max_length=255)
    gender = models.CharField(max_length=1, null=True)
    height = models.IntegerField(blank=True, null=True)
    weight = models.IntegerField(blank=True, null=True)
    age = models.IntegerField(blank=True, null=True)
    pinfo = models.IntegerField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'member'
