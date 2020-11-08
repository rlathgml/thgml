from django.urls import path, include
from . import views
from django.conf.urls import url

from allauth.account.views import confirm_email

app_name = 'accounts'

urlpatterns = [
    path('memberjoin/', views.create),
    path('memberinfo/<int:memberno>/', views.detail),
    path('memberupdate/<int:memberno>/', views.update),
    path('memberdelete/<int:memberno>/', views.delete),
    path('read/<int:memberno>/', views.read),

    
    path('account/kakao/', views.kakao),
    # -- 카카오 로그인 --
    path('account/test/', include('rest_auth.urls')),
    path('account/registration/', include('rest_auth.registration.urls')),
    path('account/test2/', include('allauth.urls')),
    url(r'account/registration/confirm-email/(?P<key>.+)/$', confirm_email, name='account_confirm_email'),
    path('', include('django.contrib.auth.urls')),
    # -- 카카오 로그인 끝 -- 
]

