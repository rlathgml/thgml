<template>
  <d-card class="card-small mb-4">
    <!-- Card Header -->
    <d-card-header class="border-bottom">
      <h6 class="m-0">{{ title }}</h6>
    </d-card-header>

      <d-list-group flush>
        <d-list-group-item class="p-3">
            <!-- -->
            <d-row>
              <d-col>
                  <d-col md="12" class="form-group">
                    <div class="pretty p-default p-curve p-thick p-smooth">
                      <input type="checkbox" onclick="return false;" v-model="diseases" value="3" />
                      <div class="state p-danger-o">
                        <label>당뇨</label>
                      </div>
                    </div>
                  </d-col>
              </d-col>
              <d-col>
                  <d-col md="12" class="form-group">
                    <div class="pretty p-default p-curve p-thick p-smooth">
                      <input type="checkbox" onclick="return false;" v-model="diseases" value="2" />
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
                      <input type="checkbox" onclick="return false;" v-model="diseases" value="4" />
                      <div class="state p-danger-o">
                        <label>비만</label>
                      </div>
                    </div>
                  </d-col>
              </d-col>
              <d-col>
                  <d-col md="12" class="form-group">
                    <div class="pretty p-default p-curve p-thick p-smooth">
                      <input type="checkbox" onclick="return false;" v-model="diseases" value="6"/>
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
                      <input type="checkbox" onclick="return false;" v-model="diseases" value="1" />
                      <div class="state p-danger-o">
                        <label>고지혈증</label>
                      </div>
                    </div>
                  </d-col>
              </d-col>
              <d-col>
                  <d-col md="12" class="form-group">
                    <div class="pretty p-default p-curve p-thick p-smooth">
                      <input type="checkbox" onclick="return false;" v-model="diseases" value="5" />
                      <div class="state p-danger-o">
                        <label>신부전증</label>
                      </div>
                    </div>
                  </d-col>
              </d-col>
            </d-row>
        </d-list-group-item>
      </d-list-group>
  </d-card>
</template>

<script>
import http from "@/utils/axios-common.js";

export default {
  name: 'user-condition-details',
  props: {
    title: {
      type: String,
      default: '건강상태',
    },
  },
  data(){
    return{
      user: this.$cookies.get("auth-token"),  
      diseases: [],
    }
  },
  created(){
    this.getData();
  },
  beforeUpdated(){
    this.getData();
  },
  methods: {
    getData(){
      http
        .get(`/accounts/read/${this.user.memberno}`)
        .then((data)=>{ 
          this.diseases = (data.data.checklists == null? new Array() : data.data.checklists);
        })
    }
  }
};
</script>
