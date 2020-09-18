<template>
  <div>
    <div class="menu">
      <b-button variant="outline-light" block @click="mode = 'shop'">상가정보</b-button>
      <!-- <b-button variant="outline-light" block>병원정보</b-button> -->
    </div>
    <div class="wrapper">
      <div id="map" class="map" :class="{ halfmap: roadview }"></div>
      <div id="roadview" v-show="roadview">
        <div class="roadviewclose" @click="roadview = false"></div>
      </div>
    </div>
    <div id="list">
      <b-list-group>
        <template v-if="mode === 'shop'">
          <b-list-group-item v-for="(item, index) in items" :key="index">
            <div :id="'list' + index" class="list" @click="showOverlay(index)">
              <div class="shopinfo">
                <h4>{{ item.shopName }}</h4>
                <h6>{{ item.codeName3 }}</h6>
              </div>
              <div class="alt" style="float: right" @click="showRoadview(index)"></div>
            </div>
          </b-list-group-item>
        </template>
      </b-list-group>
      <div>
        <h1>
          <b-icon-arrow-left-circle @click="prevList" class="prev" />
          <b-icon-arrow-right-circle @click="nextList" class="next" />
        </h1>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import Vue from 'vue';
import { BootstrapVue, BootstrapVueIcons } from 'bootstrap-vue';

Vue.use(BootstrapVue);
Vue.use(BootstrapVueIcons);

const shopurl = 'http://localhost:9999/happyhouse/api/shop/all';

export default {
  name: 'List',
  data() {
    return {
      map: null,
      lat: null,
      lng: null,
      marker: null, //내위치
      markers: [], //상가위치
      mode: 'shop', //상가정보 or 병원정보
      items: [], //상가리스트 or 병원리스트
      pagination: {},
      overlaies: [],
      curOverlay: null,
      roadview: false,
    };
  },
  created() {
    this.getShops(shopurl);
  },
  mounted() {
    this.getLocation();
    window.kakao && window.kakao.maps ? this.initMap() : this.addScript();
  },
  methods: {
    initMap() {
      var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
      var options = {
        //지도를 생성할 때 필요한 기본 옵션
        center: new kakao.maps.LatLng(this.lat, this.lng), //지도의 중심좌표.
        level: 12, //지도의 레벨(확대, 축소 정도)
      };
      this.map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴

      this.marker = new kakao.maps.Marker({
        position: this.map.getCenter(),
      });
      this.marker.setMap(this.map);
    },
    addScript() {
      const script = document.createElement('script');
      /*global kakao*/
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        '//dapi.kakao.com/v2/maps/sdk.js?appkey=e9479dc5e1683c7f8120dce98e74d972&autoload=false';
      document.head.appendChild(script);
    },
    setCenter(lat, lng) {
      // 지도 중심을 이동 시킵니다
      this.map.panTo(new kakao.maps.LatLng(lat, lng));
    },
    getLocation() {
      // GPS를 지원하면
      if (navigator.geolocation) {
        var self = this;
        navigator.geolocation.getCurrentPosition(
          function(position) {
            self.lat = position.coords.latitude;
            self.lng = position.coords.longitude;
            self.setCenter(self.lat, self.lng);
          },
          function(error) {
            console.error(error);
          },
          {
            enableHighAccuracy: false,
            maximumAge: 0,
            timeout: Infinity,
          }
        );
      } else {
        alert('GPS를 지원하지 않습니다');
      }
    },
    getShops(shopurl) {
      axios
        .get(shopurl)
        .then((response) => {
          this.items = response.data.list;
          this.pagination = response.data.pagination;
          this.removeMarkers();
          this.createMarkers();
          this.removeOverlay();
          this.createOverlay();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    makeOverListener(map, customOverlay, index, self) {
      return function() {
        console.log(self.curOverlay);
        if (self.curOverlay) {
          self.overlaies[self.curOverlay].setMap(null);
        }
        customOverlay.setMap(map);
        self.curOverlay = index;
      };
    },
    makeOutListener(customOverlay) {
      return function() {
        this.curOverlay = null;
        customOverlay.setMap(null);
      };
    },
    prevList() {
      this.roadview = false;
      let page = this.pagination.page - 1;
      let range = this.pagination.range;
      let url = shopurl + '?keyword=shop&page=' + page + '&range=' + range;

      this.getShops(url);
    },
    nextList() {
      this.roadview = false;
      let page = this.pagination.page + 1;
      let range = this.pagination.range;
      let url = shopurl + '?keyword=shop&page=' + page + '&range=' + range;

      this.getShops(url);
    },
    removeMarkers() {
      let size = this.markers.length;
      if (size > 0) {
        for (let i = 0; i < size; i++) {
          //기존 마커 지우기
          this.markers.pop().setMap(null);
        }
      }
    },
    createMarkers() {
      let imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png';
      for (let i = 0; i < this.items.length; i++) {
        // 1. 마커 생성
        // 마커 이미지의 이미지 크기 입니다
        let imageSize = new kakao.maps.Size(24, 35);

        // 마커 이미지를 생성합니다
        let markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

        // 마커를 생성합니다
        let marker = new kakao.maps.Marker({
          map: this.map, // 마커를 표시할 지도
          position: new kakao.maps.LatLng(this.items[i].lat, this.items[i].lng), // 마커를 표시할 위치
          title: this.items[i].shopName, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
          image: markerImage, // 마커 이미지
        });
        this.markers.push(marker);
      }
    },
    removeOverlay() {
      let size = this.overlaies.length;
      if (size > 0) {
        for (let i = 0; i < size; i++) {
          //기존 마커 지우기
          this.overlaies.pop().setMap(null);
        }
      }
    },
    createOverlay() {
      for (let i = 0; i < this.items.length; i++) {
        let content = `
              <div class="overlaybox">
                <div class="shopinfo">
                  <h4>${this.items[i].shopName} <font style="font-size:12px">${this.items[i].codeName3}</font></h4>
                  <h6>${this.items[i].address}</h6>
                  </div>
                </div>
              </div>
            `;

        var customOverlay = new kakao.maps.CustomOverlay({
          position: this.markers[i].getPosition(),
          content: content,
          xAnchor: 0.3,
          yAnchor: 0.91,
        });

        kakao.maps.event.addListener(
          //마커 클릭하면 오버레이 띄우기
          this.markers[i],
          'click',
          this.makeOverListener(this.map, customOverlay, i, this)
        );
        kakao.maps.event.addListener(
          // 지도 클릭하면 오버레이 지우기
          this.map,
          'click',
          this.makeOutListener(customOverlay)
        );

        this.overlaies.push(customOverlay);
      }
    },
    showOverlay(index) {
      console.log(this.curOverlay);
      if (this.curOverlay) {
        this.overlaies[this.curOverlay].setMap(null);
      }
      this.overlaies[index].setMap(this.map);
      this.curOverlay = index;
    },
    showRoadview(index) {
      this.roadview = true;

      var roadviewContainer = document.getElementById('roadview'); //로드뷰를 표시할 div
      var roadview = new kakao.maps.Roadview(roadviewContainer); //로드뷰 객체
      var roadviewClient = new kakao.maps.RoadviewClient(); //좌표로부터 로드뷰 파노ID를 가져올 로드뷰 helper객체

      var position = this.markers[index].getPosition();
      roadviewClient.getNearestPanoId(position, 50, function(panoId) {
        roadview.setPanoId(panoId, position); //panoId와 중심좌표를 통해 로드뷰 실행
      });
    },
  },
};
</script>

<style>
.wrapper {
  width: 60%;
  height: 100vh;
  float: left;
}
.map {
  height: 100%;
  width: 100%;
}
.halfmap {
  height: 50%;
}
#roadview {
  width: 100%;
  height: 50%;
  background-color: rebeccapurple;
}
.menu {
  height: 100vh;
  width: 15%;
  background-color: #222222;
  float: left;
}
.prev {
  float: left;
}
.next {
  float: right;
}
.overlaybox {
  border-radius: 10px;
  /* width: auto; */
  color: white;
  background-color: #222222;
  padding: 5px;
}
.shopinfo {
  display: inline-block;
}
.alt {
  width: 50px;
  height: 50px;
  background: url('../../assets/pinicon.png') no-repeat;
  background-size: contain;
  display: inline-block;
}
.roadviewclose {
  z-index: 1;
  position: absolute;
  right: 0;
  background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png')
    no-repeat;
  width: 20px;
  height: 20px;
}
</style>
