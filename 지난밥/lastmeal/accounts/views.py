from django.shortcuts import get_object_or_404

from rest_framework.response import Response
from django.http import JsonResponse
from rest_framework.decorators import api_view

from .models import Member
from etc.models import Checklist
from etc.models import Disease

from .serializers import MemberSerializer
import requests
from django.http import QueryDict

from django.views.decorators.csrf import csrf_exempt
from django.utils.decorators import method_decorator



@api_view(['POST']) # insert
def create(request):
   serializer = MemberSerializer(data=request.data)
   if serializer.is_valid(raise_exception=True):
       serializer.save()
       # 유저 정보 받아와서 정제해서 넣기
       return Response(serializer.data)


@api_view(['GET']) # select
def detail(request, memberno):
   me = Member.objects.get(memberno=memberno)
   serializer = MemberSerializer(me)
   return JsonResponse(serializer.data)

@api_view(['GET']) # read
def read(request, memberno):
    me = Member.objects.get(memberno=memberno)
    checklists = me.checklist_set.all()
    lists = []
    for i in checklists:
        lists.append(i.diseaseno.diseaseno)

    if not lists:
        lists = None
    context = {
        'checklists': lists
    }
    return Response(context)


@api_view(['PUT']) # update
def update(request, memberno):
    me = Member.objects.get(memberno=memberno)

    serializer = MemberSerializer(instance=me, data=request.data['user'])
    if serializer.is_valid(raise_exception=True):
        serializer.save()
        checklists = me.checklist_set.all()
        for i in checklists:
            i.delete()
        lists = []
        if request.data['disease']:
            for i in request.data['disease'].split(','):
                disease = Disease.objects.get(diseaseno=i)
                checklist = Checklist()
                checklist.memberno = me
                checklist.diseaseno = disease
                checklist.save()
                lists.append(i)

        if not lists:
            lists = None

        context = {
            'user': serializer.data,
            'disease' : lists
        }
        return JsonResponse(context)
    return JsonResponse({'message':'FAIL'})


@api_view(['DELETE']) # delete
def delete(request, memberno):
   me = get_object_or_404(Member, memberno = memberno)
   me.delete()
   return JsonResponse({'message':'SUCCESS'})

API_HOST = 'https://kapi.kakao.com'

@api_view(['POST'])
def kakao(request):
    headers = {'Authorization': 'Bearer '+ request.data["access_token"]}
    resp = req('/v2/user/me', '', 'GET', headers).json()

    try:
        me = Member.objects.get(email=resp['kakao_account']['email'])
        me = MemberSerializer(me).data
    except:
        me = None
    
    requestType = request.data["requestType"]
    jr = None
    if me is not None and requestType == 1:     # 회원인 사람이 로그인 시도
        jr = JsonResponse({'message':'SUCCESS', 'member':me})
    elif me is None and requestType == 1:       # 회원이 아닌 사람이 로그인 시도
        jr = JsonResponse({'message':'JOIN'})
    elif me is not None and requestType == 2:   # 회원이 회원가입 시도
        jr = JsonResponse({'message':'GO LOGIN'})
    elif me is None and requestType == 2:       # 회원이 아닌 사람이 회원가입
        try: # 카카오 로그인 선택항목 동의 안하면 가입 안됨 
            email = resp['kakao_account']['email']
            gender = resp['kakao_account']['gender']
        except:
            return JsonResponse({'message':'NOAGREE'})
        
        try: # 프로필사진 없으면 디폴트
            # profilepath = resp['kakao_account']['profile']['profile_image_url']
            profilepath = resp['properties']['profile_image']
        except:     
            profilepath = "https://cdn.icon-icons.com/icons2/317/PNG/512/profile-icon_34378.png"

        try: # 닉네임 없으면 디폴트 근데 닉네임 없을일은 진짜 절대 없지 않나 
            nickname = resp['properties']['nickname']
        except:
            nickname = "닉네임을 설정해주세요"

        if gender is 'female':
            gender = '여'
        else:
            gender = '남'

        data = QueryDict(f"email={email}&profilepath={profilepath}&nickname={nickname}&gender={gender}")
        serializer = MemberSerializer(data=data)
        if serializer.is_valid(raise_exception=True):
            serializer.save()
        jr = JsonResponse({'message':'JOIN SUCCESS'})
    return jr

def req(path, query, method, headers, data={}):
    url = API_HOST + path

    if method == 'GET':
        return requests.get(url, headers=headers)
    elif method == 'POST':
        return requests.post(url, headers=headers, data=data)

