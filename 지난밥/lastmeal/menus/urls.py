from django.urls import path
from . import views

app_name = 'menus'

urlpatterns = [
    path('image/', views.image, name="image"),
    path('create/', views.create, name='create'),
    path('daily/<int:memberno>/<str:day>/', views.daily, name='daily'),
    path('update/<int:meal_pk>/', views.update, name='update'),
    path('delete/<int:meal_pk>/', views.delete, name='delete'),
    path('detail/<str:menu_name>/', views.detail, name='detail'),
    path('recommend/<int:memberno>/', views.recommend, name='recommend'),
    path('report/<int:memberno>/', views.week_report, name='report'),
]
