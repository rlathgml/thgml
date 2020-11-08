from django.shortcuts import get_object_or_404

from rest_framework import status
from rest_framework.response import Response
from rest_framework.decorators import api_view

from .models import Menu
from .models import Nutritional

from accounts.models import Member

from .serializers import MenuSerializer, NutritionalSerializer

from . import nutrient
from . import tests
from PIL import Image
import os
import datetime

from predict import get_predict
from object_detection import localize_objects

from etc.models import Checklist
from etc.models import Disease 


import random

nutri = ['tan', 'dan', 'ji', 'dang', 'na', 'cal', 'col']
nutriKorean = ['탄수화물', '단백질', '지방', '당', '나트륨', '칼로리', '콜레스테롤']
recommendation = {
    '당뇨': [
        '과체중이나 비만의 경우 "체중감량"을하면 혈당조절에 도움이 돼요!',
        '혈당 상승폭을 줄이기 위해 세끼를 "규칙적"으로 먹어요~',
        '식사는 20분이상 천천히 하는 것이 좋아요~',
        '"과일"을 과량섭취 시 "혈당상승"의 원인이 돼요 ㅠ.ㅠ',
        '설탕 대신 인공감미료를, 크림 대신 우유를 사용하세요. (진지)'
    ],
    '비만': [
        '영양제를 고를 때는 성분 함량을 잘 살펴봐요. 설탕이나 나트륨이 많을수도...',
        '장기간 다이어트에 애를 먹고 있다면, 전문의를 찾아보세요 ㅠ.ㅠ',
        '칼로리는 줄이되 균형잡힌 식사를 해야해요!',
        '일상 생활에서 조금만 더 움직여도 큰 효과를 볼 수 있어요.',
        '필수 영양소가 골고루 포함되어 있으면서 칼로리가 비교적 적은 대체식을 먹어봐요~'
    ],
    '고혈압': [
        '비만하거나 체중이 과다하다면 혈압감소를 위해 체중을 줄여야 해요...',
        '알코올 섭취는 일반적으로 남자는 1일 2잔, 여자는 1잔 이하로 제한합시다!!',
        '무기질이 많은 야채와 과일을 충분히 섭취하면 혈압을 감소시켜요~',
        '불필요한 외식이나 간식을 먼저 줄이세요. (단호)',
        '고혈압 환자가 염분 섭취를 1/3정도로 줄이면 수축기 혈압4.9mmHg이 감소돼요 '
    ],
    '고지혈증': [
        '포화지방 섭취를 줄여야해요! 동물성 지방과 식물성 지방을 조심하세요!!',
        '콜레스테롤이 많은 음식은 주 2회 정도로 제한합니다. (실시)',
        '식물성 기름은 다양하게 섭취하고, 특히 오메가 3지방은 주 2회 이상 섭취하세요~',
        '식이 섬유소는 혈액 내 콜레스테롤 및 중성지방을 감소시켜요!!',
        '알코올과 단순당 (사탕, 과자, 단 음료수 등)은 혈액 내 중성지방을 증가시켜요. ㅠ.ㅠ'
    ],
    '저체중': [
        '규칙적으로 1일 3회의 식사를 해야해요!',
        '적당량의 간식을 하면서 고열량 식사를 하도록 해요~',
        '단백질이 부족하면 신체 조직 형성에 영향을 미칠 수 있어요 ㅠ.ㅠ',
        '위에 부담이 적고 에너지가 높은 케이크, 요구르트, 아이스크림, 푸딩 등의 음식을 먹어요',
        '비타민과 무기질 섭취를 위해 1일 1회 이상 신선한 과일을 먹도록 해봅시다~'
    ],
    '신부전증': [
        '나트륨은 부종을 일으키고 혈압을 올려 신장에 부담을 줍니다...',
        '신장기능에 맞는 적정량의 단백질을 섭취해야해요!',
        '열량섭취가 부족하면 체내 단백질이 분해되어 사용되기 때문에 영양불량의 원인이 되어요!',
        '소변량이 줄고 심한 부종이 있을때는 수분을 조절해야해요!!!',
        '무기질 함량이 높은 잡곡밥보다는 쌀밥을 섭취하는 것이 좋아요~~'
    ],
    '정상': [
        '이대로 쭉 유지해요~',
        '밥이 보약이죠~',
        '오늘도 맛있는 식사 하세요~',
        '으쌰으쌰!!! 즐거운 하루~~~',
        '오늘 하루도 건강하세요~'
    ]

}

# menu 만들기(C)
@api_view(['POST'])
def image(request):
    # 이미지를 받아서 이미지 캡셔닝 수행. 이미지는 임시로 가지고 있을 것
    # -> 1. vue에서 가지고 있다가 다시 보낸다.
    # -> 2. media 폴더에 저장했다가 일정 시간 create 요청이 안오면 폐기한다.
    # -> 3. views.py의 변수로 tmp image 데이터를 가지고 있다가 create 시 그 데이터를 저장한다.
    # (create함수에서 이미지 저장)
    # 사용자가 먹은 식단 리스트를 반환함

    # image 캡셔닝도 이런식으로 함수 호출해서 사용 (이미지 데이터를 넘겨준다)
    # 저장 확인
    im = Image.open(request.data['Image'].file)
    im = im.resize((224, 224), Image.ANTIALIAS)
    image_path_string = '/home/ubuntu/s03p23d205/lastmeal/media/temp/'+request.data['member'] + '.jpg'
    # image_path_string = './media/temp/'+ request.data['member'] + '.jpg' 
    im = im.convert("RGB")
    im.save(image_path_string)

    paths = localize_objects(image_path_string, request.data['member'])

    # type(request.data): django.http.request.QueryDict
    # type(request.data['Image']): django.core.files.uploadedfile.InMemoryUploadedFile

    # image_list = request.FILES.getlist('Image')
    # for item in image_list:
    #     menuimgpath = 
    menulistname = {}
    for p in paths:
        tmp = get_predict(p)
        if tmp in menulistname:
            menulistname[tmp] += 1
        else:
            menulistname[tmp] = 1
        os.remove(p)
    os.remove(image_path_string)

    menulist = []
    for key, val in menulistname.items():
        menulist.append([key, val])

    context = {
        'menulist': menulist
    }

    return Response(context)

@api_view(['POST'])
def create(request):
    # request.data는 dict type -> data로 들어갈 부분과 user(member) pk를 가져갈 부분을 따로 지정했다.
    serializer = MenuSerializer(data=request.data)
    memNo = get_object_or_404(Member, pk=request.data['memberno'])
    
    if serializer.is_valid(raise_exception=True):
        serializer.save(memberno=memNo)

        context = {
            'message': True,
        }
        return Response(context)


# menu 일별 조회(R)
@api_view(['GET'])
def daily(request, memberno, day):
    # 받은 day가 string 값이므로 eattime의 type과 같은지 확인할 것

    # 해당 유저의 메뉴 목록을 받아옴(fk) -> 해당 일자의 메뉴만 필터링 -> pk 기준으로 오름차순 정렬
    menus = Member.objects.get(pk=memberno).menu_set.all().filter(
        eattime__startswith=day).order_by('pk')
    # eattime은 yyyy-mm-dd 시간 의 값 -> day로 시작하는 값을 가져오려면 __startswith / day가 포함된 걸 가져오려면 __contains
    serializer = MenuSerializer(menus, many=True)
    
    return Response(serializer.data, status=status.HTTP_200_OK)


@api_view(['POST', 'GET'])
def update(request, meal_pk):
    # medel에는 menuno라고 저장되어 있음 -> pk, memberno 둘 다 됨
    menu = get_object_or_404(Menu, pk=meal_pk)
    if request.method == "POST":
        serializer = MenuSerializer(instance=menu, data=request.data['data'])
        if serializer.is_valid(raise_exception=True):
            serializer.save()
            return Response(serializer.data)
    else:
        serializer = MenuSerializer(menu)
        return Response(serializer.data)


@api_view(['POST'])
def delete(request, meal_pk):
    menu = get_object_or_404(Menu, pk=meal_pk)
    menu.delete()
    return HttpResponse("DELETED")
    

@api_view(['GET'])
def detail(request, menu_name):
    # 해당 메뉴의 nutritional을 가져오기
    try:
        nutritional = Nutritional.objects.get(nutritionalname=menu_name)
        serializer = NutritionalSerializer(nutritional)
        return Response(serializer.data, status=status.HTTP_200_OK)
    except:
        try:
            nutrient_data = nutrient.search_nutrient(menu_name)
            serializer = NutritionalSerializer(data=nutrient_data)
            if serializer.is_valid(raise_exception=True):
                serializer.save()
            
            return Response(serializer.data, status=status.HTTP_200_OK)
        except:
            context = {
                'data': None,
                'message': '해당 음식의 정보를 찾을 수 없습니다.'
            }
            return Response(context)


def week_data(request, memberno):
    # 1. 일주일 받아오기
    now = datetime.datetime.now()
    weekdays = []
    menu_weekdays = []
    count = 0       # 식단 등록한 기간

    for i in range(-6, 0+1, 1):
        now_before = str(now + datetime.timedelta(days=i))
        weekdays.append(now_before[:10])

    defaultDay = {
        'day': '0000-00-00',
        'tan': 0,
        'dan': 0,
        'ji': 0,
        'dang': 0,
        'na': 0,
        'cal': 0,
        'col': 0,
    }

    # 2. 각 날짜 별 nutritional 정보 가져오기
    for day in weekdays:
        menus = Member.objects.get(pk=memberno).menu_set.all().filter(
            eattime__startswith=day).order_by('pk')
        dict_day = defaultDay.copy()
        dict_day['day'] = day

        if len(list(menus)) != 0:
            count += 1
            for menu in menus:
                dict_day['tan'] += menu.tan
                dict_day['dan'] += menu.dan
                dict_day['ji'] += menu.ji
                dict_day['dang'] += menu.dang
                dict_day['na'] += menu.na
                dict_day['cal'] += menu.cal
                dict_day['col'] += menu.col

        menu_weekdays.append(dict_day)
    
    # 3. 값 넣어서 보내기
    context = {
        'menus': menu_weekdays,
        'count': count,
    }

    return context

@api_view(['GET'])
def week_report(request, memberno):
    context = week_data(request, memberno)
    return Response(context, status=status.HTTP_200_OK)


@api_view(['POST'])
def recommend(request, memberno):
    # 1. 일주일치 섭취 영양소 호출  
    # 데이터 순서: tan, dan, ji, dang, na, cal, col
    context = week_data(request, memberno)
    menudata = [0] * 7

     # 7 영양소
    for i in range(7):   
        # 일주일치 데이터
        for j in range(7):   
            menudata[i] += context['menus'][j][nutri[i]]


    # 2. 1일 권장 섭취량 받아오기  
    # 데이터 순서: cal, tan, dan, ji, dang, na, col
    recommend = request.data['recommendData'].split(',')
    for i in range(len(recommend)):
        recommend[i] = float(recommend[i]) * 7
    recommend = [
        recommend[1], 
        recommend[2], 
        recommend[3], 
        recommend[4], 
        recommend[5], 
        recommend[0], 
        recommend[6]
    ]

    # 3. 사용자 질병 정보 받아오기
    me = Member.objects.get(memberno=memberno)
    checklists = me.checklist_set.all()

    diseasenutri = set()
    diseases = {}
    for c in checklists:
        tmp = c.diseaseno.diseaseinfo.split(',')
        diseases[c.diseaseno.diseasename] = tmp
        for d in tmp:
            diseasenutri.add(d)

    # 4. 사용자 조언 선별
    # 데이터 순서: tan, dan, ji, dang, na, cal, col
    per = [0] * 7
    mymax = 0

    # 권장 영양소에서 얼마나 떨어져있는지 계산. 
    # 가장 떨어져있는 영양소 idx가 mymax
    for i in range(7):
        per[i] = ((menudata[i]-recommend[i])/recommend[i])*100
        if abs(per[i]) > per[mymax]:
            mymax = i

    idx = random.choice(range(5))
    # 질병 있음
    if nutri[mymax] in diseasenutri:
        # 해당 영양소를 조심해야하는 사용자의 질병
        for key, val in diseases.items():
            if nutri[mymax] in val:
                name = key
        # 기준치 초과
        if per[mymax] >= 30:
            text1 = f"'{nutriKorean[mymax]}' 섭취를 줄이세요!"
            text2 = recommendation[name][idx]
        # 기준치 미달
        elif per[mymax] < -30:
            nustr = []
            for k in range(7):
                if per[k] < -30:
                    nustr.append("'"+str(nutriKorean[k])+"'")
            text1 = f"{','.join(nustr)} 섭취가 너무 적어요!!"
            text2 = "적정량의 영양소를 균형있게 먹어야해요~~"
        else:
            text1 = "잘 먹고 있어요!"
            text2 = recommendation[name][idx]
    # 질병 없음
    else:
        # 해당 영양소를 주의해야하는 질병
        names = Disease.objects.filter(diseaseinfo__contains=nutri[mymax])
        name = names[0].diseasename
        # 기준치 초과
        if per[mymax] >= 30:
            text1 = f"이러다가 {name} 걸리겠어요!"
            text2 = recommendation[name][idx]
        # 기준치 미달
        elif per[mymax] < -30:
            nustr = []
            for k in range(7):
                if per[k] < -30:
                    nustr.append("'"+str(nutriKorean[k])+"'")
            text1 = f"{','.join(nustr)} 섭취가 너무 적어요!!"
            text2 = "적정량의 영양소를 균형있게 먹어야해요~~"
        else:
            text1 = "잘 먹고 있어요!"
            text2 = recommendation['정상'][idx]
            

    # 5. 사용자 조언 전송
    context = {
        "text1" : text1,
        "text2" : text2,
    }

    return Response(context, status=status.HTTP_200_OK)