//获取试卷并跳转到添加题库
<template>
<div class="panlt">
  <div class="player videoplayer">
    <video-player
      class="video-player vjs-custom-skin"
      ref="videoPlayer"
      :playsinline="true"
      style="object-fit: fill;width:1000px;height:570px;"
      :options="playerOptions"
      :x5-video-player-fullscreen="true"
      @pause="onPlayerPause($event)"
      @play="onPlayerPlay($event)"
      @fullscreenchange="onFullscreenChange($event)"
      @click="fullScreen"
    >
    </video-player>
    <div class="critics">
      <div class="title">评论</div>
    <div class="wrapper">

      <div class="content">
        <el-input
          type="textarea"
          :rows="3"
          placeholder="评论内容"
          v-model="content"
          clearable>
        </el-input>
      </div>
      <div class="btn">
        <el-button type="primary" @click="submit()">提交评论</el-button>
      </div>
      <div class="all">
        <ul class="msglist">
          <li class="list"
          v-for="(data,index) in critics" :key="index"
          >
            <p class="title"> <i class="iconfont icon-untitled33"></i>{{data.title}}</p>
            <p class="content">{{data.content}}</p>
            <p class="date"><i class="iconfont icon-date"></i>{{data.time}}</p>

          </li>
        </ul>
      </div>
      <div class="my-pagination">
        <paging @handleCurrentChange="criticsignal" ref="cripaging" ></paging>
      </div>
    </div>
    </div>

  </div>
  <!-- table列表 -->
  <div class="tab">
    <el-table
    :data="tableData"
    style="width: 480px">
      <el-table-column
        label="图片"
        width="160px">
         <template slot-scope="scope">
            <img @click="playVideo(scope.row.mvPath)" class="sub-img" :src="scope.row.imgPath" alt="">
          </template>
      </el-table-column>
      <el-table-column
        prop="drama"
        label="课程信息"
        width="320">
        <template slot-scope="scope">
          <p>{{ scope.row.subject }}</p>
          <p>{{ scope.row.drama }}</p>
          <p>{{ scope.row.teacherName }}</p>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <div class="my-pagination">
      <paging @handleCurrentChange="signal" ref="paging" ></paging>
    </div>

  </div>

  </div>
</template>
<script>
import {videoPlayer} from 'vue-video-player';
import paging from '../common/page'
export default {

  data() {
    return {
      // pictureImg: test,
      tableData:[],
      playerOptions: {
        playbackRates: [0.7, 1.0, 1.5, 2.0], //播放速度
        autoplay: false, //如果true,浏览器准备好时开始回放。
        muted: false, // 默认情况下将会消除任何音频。
        loop: false, // 导致视频一结束就重新开始。
        preload: 'auto', // 建议浏览器在<video>加载元素后是否应该开始下载视频数据。auto浏览器选择最佳行为,立即开始加载视频（如果浏览器支持）
        language: 'zh-CN',
        aspectRatio: '16:9', // 将播放器置于流畅模式，并在计算播放器的动态大小时使用该值。值应该代表一个比例 - 用冒号分隔的两个数字（例如"16:9"或"4:3"）
        fluid: true, // 当true时，Video.js player将拥有流体大小。换句话说，它将按比例缩放以适应其容器。
        poster: "/static/img/icon.png",   //你的封面地址
        width: document.documentElement.clientWidth,
        notSupportedMessage: '此视频暂无法播放，请稍后再试', //允许覆盖Video.js无法播放媒体源时显示的默认信息。
        controlBar: {
          // timeDivider: true,
          // durationDisplay: true,
          // remainingTimeDisplay: false,
          fullscreenToggle: true  //全屏按钮
        },
        sources: [],
      },
      subject:"",
      videoId:"",
      page:1,
      pagesize:5,
      content:"",
      critics:[]

    }
  },
  created(){
    this.subject = this.$route.query.subject
    this.videoId = this.$route.query.id
    console.log(this.$route.query);
    this.getList();
    this.getMsg()
  },
  components: {
    videoPlayer,
    paging
  },
  methods: {

    fullScreen() {
      const player = this.$refs.videoPlayer.player
      player.requestFullscreen()//调用全屏api方法
      player.isFullscreen(true)
      player.play()
    },
    onPlayerPlay(player) {
      player.play()
    },
    onPlayerPause(player) {
      // alert("pause");
    },
    getList(){
      let params = {
        page:this.page,
        size:this.pagesize,
        subject:this.subject,
        id:this.videoId
      }
      params = JSON.stringify(params)
      let that = this
      this.$axios({
        url:"/api/mv/listAllMv",
        method: "post",
        headers: { "content-type": "application/json" },
        data:params,
      }).then(res => {

        if(res.data.code == 200){
          res.data.data.records.forEach(item => {
            // that.playerOptions.sources.push({type: "video/mp4",src: item.mvPath})
          });
          that.tableData = res.data.data.records

        }
        let pageObj = {
          currentPage:res.data.data.page,
          total:res.data.data.total,
          pagesize:res.data.data.size
        }
        that.$refs.paging.init(pageObj);
      })
    },
    signal(code){
      this.page = code;
      this.getList()
    },
    criticsignal(code){
      this.page = code;
      this.getMsg()
    }
    ,
    playVideo(data){
      this.playerOptions.sources.push({type: "video/mp4",src: data})
      this.playerOptions.autoplay = true
    },
    submit(){
      let date = new Date().getTime()
      let params = {
        studentId:localStorage.getItem('studentId'),
        content:this.content,
        mvId:"123456",
        // time:date
      }
      this.$axios({
        url: "/api/message/add",
        method: "post",
        data:params
      }).then(res => {
        let code = res.data.code
        if(code == 200) {
          this.$message({
            type: "success",
            message: "留言成功"
          })
        }
        this.getMsg()
      })

    },
    // 评论列表
    getMsg() {
      let params = {
        page:this.page,
        size:this.pagesize,
        mvId:'123456'
      }
      params = JSON.stringify(params)
      let that = this
      this.$axios({
        url:"/api/messages/findAll",
        method: "post",
        headers: { "content-type": "application/json" },
        data:params,
      }).then(res => {
        if(res.data.code == 200){
          res.data.data.records.map(item=>{
            item.time = that.utils.getDate(item.time)
          })
          that.critics = res.data.data.records
        }
        let pageObj = {
          currentPage:res.data.data.page,
          total:res.data.data.total,
          pagesize:res.data.data.size
        }
        that.$refs.cripaging.init(pageObj);
      })
    },

  },
  computed: {
    player() {
      return this.$refs.videoPlayer.player
    }
  }
}
</script>
<style>
.video-js .vjs-big-play-button {
  width: 72px;
  height: 72px;
  border-radius: 100%;
  z-index: 100;
  background-color: #ffffff;
  border: solid 1px #979797;
}
.videoplayer{
  /* display: flex; */
  /* height: 80vh; */
  /* justify-content: center; */
  /* align-items: center; */
  margin: 100px 0px 100px 74px;
}
.panlt{
  width: 100vh;
  display: flex;
  margin-left: 10px;
}
.tab{
  margin: 100px 0px 100px 20px;
}
.sub-img{
  width: 160px;
  height: 90px;
  object-fit:cover;
}
.my-pagination{
  height: 50px;display: flex;justify-content: flex-end;align-items: center;padding: 0 20px;
}

.title {
  margin-top: 20px;
}
.content {
  padding: 20px 0px;
}
</style>
