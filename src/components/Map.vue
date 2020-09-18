<template>
  <div>
    <div id="map" style="width:500px;height:400px;"></div>
    <button @click="setCenter">click</button>
    <button @click="plus">+</button>
    <button @click="minus">-</button>
    <button @click="getLocation">gps</button>

    <div>
      <b-form-select v-model="selected" :options="options"></b-form-select>
      <b-form-select
        v-model="selected"
        :options="options"
        size="sm"
        class="mt-3"
      ></b-form-select>
      <div class="mt-3">
        Selected:
        <strong>{{ selected }}</strong>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Map",
  data() {
    return {
      map: null,
      lat: 36.1,
      lng: 128.4145,
      marker: null,
      selected: null,
      options: [
        { value: null, text: "Please select an option" },
        { value: "a", text: "This is First option" },
        { value: "b", text: "Selected Option" },
        { value: { C: "3PO" }, text: "This is an option with object value" },
        { value: "d", text: "This one is disabled", disabled: true }
      ]
    };
  },
  mounted() {
    window.kakao && window.kakao.maps ? this.initMap() : this.addScript();
  },
  methods: {
    initMap() {
      var container = document.getElementById("map"); //지도를 담을 영역의 DOM 레퍼런스
      var options = {
        //지도를 생성할 때 필요한 기본 옵션
        center: new kakao.maps.LatLng(this.lat, this.lng), //지도의 중심좌표.
        level: 3 //지도의 레벨(확대, 축소 정도)
      };
      this.map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴

      this.marker = new kakao.maps.Marker({
        position: this.map.getCenter()
      });
      this.marker.setMap(this.map);
    },

    addScript() {
      const script = document.createElement("script");
      /*global kakao*/
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?appkey=e9479dc5e1683c7f8120dce98e74d972&autoload=false";
      document.head.appendChild(script);
    },
    setCenter(lat, lng) {
      var moveLatLon = new kakao.maps.LatLng(lat, lng);

      // 지도 중심을 이동 시킵니다
      this.map.panTo(moveLatLon);
    },
    plus() {
      this.map.setLevel(this.map.getLevel() + 1);
    },
    minus() {
      this.map.setLevel(this.map.getLevel() - 1);
    },
    getLocation() {
      if (navigator.geolocation) {
        // GPS를 지원하면
        var self = this;
        navigator.geolocation.getCurrentPosition(
          function(position) {
            self.lat = position.coords.latitude;
            self.lng = position.coords.longitude;
            console.dir(typeof position.coords.longitude);
            self.setCenter(self.lat, self.lng);
          },
          function(error) {
            console.error(error);
          },
          {
            enableHighAccuracy: false,
            maximumAge: 0,
            timeout: Infinity
          }
        );
      } else {
        alert("GPS를 지원하지 않습니다");
      }
    },
    range(start, end) {
      let list = [];
      for (let i = start; i <= end; i++) list.push(i);
      return list;
    }
  }
};
</script>

<style></style>
