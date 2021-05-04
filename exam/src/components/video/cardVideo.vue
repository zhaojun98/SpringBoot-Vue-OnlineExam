<template>
  <div class="cards">

      <div
        v-for="(data,index) in videoList"
        :key="index"
        :offset="index > 0 ? 2 : 0"
        style="margin:20px 20px 0 0;"
      >
        <el-card>
          <img
            :src="data.imgPath"
            class="image"
          />
            <div class="bottom">
              <!-- <el-button type="text" class="button" ><router-link to="/video">介绍:{{ data.drama }}</router-link></el-button><br/> -->
              <el-button type="text" class="button" @click="toVideo()">介绍:{{ data.drama }}</el-button><br/>
              <el-button type="text" class="button" @click="toVideo()">课程：{{ data.subject }}</el-button><br/>
              <el-button type="text" class="button" @click="toVideo()">讲师：{{ data.teacherName }}</el-button>
            </div>
        </el-card>
      </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      currentDate: new Date(),
      //id，科目，讲师，备注，时间，
      //分表：视频地址，id,时间，标题，pid
      videoList:[],
      subject:"",
      indexId:""
    };
  },
  created(){
    this.getList()
  },
  methods:{
    getList(){
      let params = {
        page:1,
        size:5
      }
      params = JSON.stringify(params)
      let that = this
      this.$axios({
        url:"/api/mv/list",
        method: "post",
        headers: { "content-type": "application/json" },
        data:params,
      }).then(res => {
        if(res.data.code == 200){
          res.data.data.records.forEach(data => {
            that.subject = data.subject
            that.indexId = data.id
          });
          that.videoList = res.data.data.records
          console.log(res.data.data);
        }

      })
    },
    toVideo(){

      this.$router.push({
        path: '/video',
        query: {subject: this.subject,id:this.indexId}
      })
    }
  }
}
</script>
<style>
.cards {
    width: 100vw;
    margin-top: 10px;
    margin-left: 10px;
    display: flex;
    flex-wrap: wrap;
}
.bottom {
  margin-top: 13px;
  /* line-height: 12px; */
}
.image {
  width: 220px;
  display: block;
}
</style>
