//获取试卷并跳转到添加题库
<template>
<div>
  <!-- <video
    id="myPlayer"
    controls
    playsInline
    webkit-playsinline
    style="width: 100vw; height: 100vh"
  >
    <source ref="videoplay" :src="gdurl" type="video/webm" />
  </video> -->
  <video style="width: 100vw; height: 100vh" src="https://vd3.bdstatic.com/mda-mdje21y7g3fpib8b/fhd/cae_h264_nowatermark/1618884000/mda-mdje21y7g3fpib8b.mp4?v_from_s=gz_haokan_4469&auth_key=1618914589-0-0-93b09a901a122bddb0d0b57c116223ae&bcevod_channel=searchbox_feed&pd=1&pt=3&abtest=3000165_1"></video>
  </div>
</template>

<script>
export default {
    data() {
        return {
			gdurl:"",
        };
    },
    computed: {
        
    },
    methods: {
	
    },
    mounted() {
			 this.$refs.videoplay.src = "http://vd3.bdstatic.com/mda-mdje21y7g3fpib8b/fhd/cae_h264_nowatermark/1618884000/mda-mdje21y7g3fpib8b.mp4?v_from_s=gz_haokan_4469&auth_key=1618914589-0-0-93b09a901a122bddb0d0b57c116223ae&bcevod_channel=searchbox_feed&pd=1&pt=3&abtest=3000165_1"
			//  this.$refs.videoplay.src = this.gdurl
				// setTimeout(function(){
				// 	var player = new EZuikit.EZUIPlayer('myPlayer');
				// },500)
    },
	created(){

	}
};
</script>
<style>
</style>