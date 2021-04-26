<template>
  <div class="cards">
    
      <div
        v-for="(o, index) in 12"
        :key="o"
        :offset="index > 0 ? 2 : 0"
        style="margin:20px 20px 0 0;"
      >
        <el-card>
          <img
            src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png"
            class="image"
          />
            <div class="bottom">
              <el-button type="text" class="button" ><router-link to="/video">数据结构</router-link></el-button><br/>
              <el-button type="text" class="button" to="/cardVideo"><router-link to="/video">讲师：张三</router-link></el-button>
            </div>
        </el-card>
      </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      currentDate: new Date()
      //id，科目，讲师，备注，时间，
      //分表：视频地址，id,时间，标题，pid
    };
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