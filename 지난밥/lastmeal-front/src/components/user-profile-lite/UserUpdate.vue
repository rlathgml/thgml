<template>
  <div>
    <Modal>
      <h4 slot="header">회원 정보 수정</h4>
      <div slot="body">
        <div v-show="msg!=''">{{ msg }}</div>
        <div v-show="show==true">
          <div class="form-group row">
            <label for="nickname">닉네임</label> <input type="text" id="nickname" class="form-control" v-model.lazy="user.nickname">
          </div>
          <div class="form-group row">
          <label for="gender">성별</label>
          <select id="inputState" class="form-control" value="teS" v-model.lazy="user.gender">
              <option value="-" v-if="user.gender == null" disabled selected>선택안함</option>
              <option value="남" v-if="user.gender == '남'" selected>남자</option>
              <option value="남" v-else>남자</option>
              <option value="여" v-if="user.gender == '남'" selected>여자</option>
              <option value="여" v-else>여자</option>
          </select>
          </div>
          <div class="form-group row">
          <label for="height">나이</label> <input type="text" id="age" class="form-control" v-model="user.age">
          </div>
          <div class="form-group row">
          <label for="height">키</label> <input type="text" id="height" class="form-control" v-model="user.height">
          </div>
          <div class="form-group row">
          <label for="weight">몸무게</label> <input type="text" id="weight" class="form-control" v-model.lazy="user.weight">
          </div>
        </div>

        <div v-show="show==false">
          <d-list-group flush>
            <d-list-group-item class="p-3">
                <!-- -->
                <d-row>
                  <d-col>
                      <d-col md="12" class="form-group">
                        <div class="pretty p-default p-curve p-thick p-smooth">
                          <input type="checkbox" v-model="diseases" value="3" />
                          <div class="state p-danger-o">
                            <label>당뇨</label>
                          </div>
                        </div>
                      </d-col>
                  </d-col>
                  <d-col>
                      <d-col md="12" class="form-group">
                        <div class="pretty p-default p-curve p-thick p-smooth">
                          <input type="checkbox" v-model="diseases" value="2" />
                          <div class="state p-danger-o">
                            <label>고혈압</label>
                          </div>
                        </div>
                      </d-col>
                  </d-col>
                </d-row>
                <!-- -->
                <d-row>
                  <d-col>
                      <d-col md="12" class="form-group">
                        <div class="pretty p-default p-curve p-thick p-smooth">
                          <input type="checkbox" v-model="diseases" value="4" />
                          <div class="state p-danger-o">
                            <label>비만</label>
                          </div>
                        </div>
                      </d-col>
                  </d-col>
                  <d-col>
                      <d-col md="12" class="form-group">
                        <div class="pretty p-default p-curve p-thick p-smooth">
                          <input type="checkbox" v-model="diseases" value="6"/>
                          <div class="state p-danger-o">
                            <label>저체중</label>
                          </div>
                        </div>
                      </d-col>
                  </d-col>
                </d-row>
                <!-- -->
                <d-row>
                  <d-col>
                      <d-col md="12" class="form-group">
                        <div class="pretty p-default p-curve p-thick p-smooth">
                          <input type="checkbox" v-model="diseases" value="1" />
                          <div class="state p-danger-o">
                            <label>고지혈증</label>
                          </div>
                        </div>
                      </d-col>
                  </d-col>
                  <d-col>
                      <d-col md="12" class="form-group">
                        <div class="pretty p-default p-curve p-thick p-smooth">
                          <input type="checkbox" v-model="diseases" value="5" />
                          <div class="state p-danger-o">
                            <label>신부전증</label>
                          </div>
                        </div>
                      </d-col>
                  </d-col>
                </d-row>
            </d-list-group-item>
          </d-list-group>
        </div>
      </div>
      
      <div slot="footer" class="footer">
          <div style="float: left" >
            <d-button class="btn-accent" @click="show = false" v-if="show == true">다음</d-button>
            <d-button class="btn-accent" type="submit" @click="modify" v-if="show == false">수정</d-button>
          </div>
          <div style="float: right;" >
            <d-button theme="secondary" class="mb-2 mr-1" @click="closeModal">취소</d-button>
          </div>
      </div>
    </Modal>
  </div>
</template>

<script>
import Modal from '@/components/common/Modal.vue'
import http from "@/utils/axios-common.js";

export default {
  components:{
    Modal,
  },
  data() {
    return {
      user: this.$cookies.get("auth-token"),
      show: true,   //true => 개인정보 바꾸는 부분 , false => 건강상태 바꾸는 부분
      diseases: [],
    }
  },
  props: {
    showModal: Boolean,
    msg: {
      type: String,
      default: ''
    }
  },
  created() {
    http
      .get(`/accounts/read/${this.user.memberno}`)
      .then((data)=>{ 
        this.diseases = (data.data.checklists == null? new Array() : data.data.checklists);
      })
  },
  methods:{
    modify(){
      let senddata ;
      if(this.diseases == null) {
        senddata = new Array();  
      } else {
        senddata = this.diseases.join(',');
      }
      console.log(senddata)
      http.put(`/accounts/memberupdate/${this.user.memberno}/`, {
        user:{
          memberno: this.user.memberno,
          email: this.user.email,
          password: this.user.password,
          profilePath: this.user.profilepath,
          nickname: this.user.nickname,
          gender: this.user.gender,
          height: this.user.height,
          weight: this.user.weight,
          age: this.user.age,
        },
        disease: senddata,
      }).then(() => {
        window.$cookies.set("auth-token", this.user);
        this.$emit('showModal', false);
        window.location.href = "/user-profile-lite";
      })
    },
    closeModal(){
      this.$emit('showModal', false);
    }
  }
}
</script>

<style>
.footer{
  display: flex;
  width: 100%;
  justify-content: space-between;
}
</style>