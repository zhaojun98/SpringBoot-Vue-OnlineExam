// 教师管理页面
<template>
  <div class="all">

    <el-table :data="tableData" border>
      <el-table-column fixed="left" prop="teacherId" label="教师ID" width="180"></el-table-column>
      <el-table-column prop="subject" label="视频标题" width="180"></el-table-column>
      <el-table-column label="视频封面" width="120">
        <template slot-scope="scope">
          <img class="sub-img" :src="scope.row.imgPath" alt="">
        </template>
      </el-table-column>
      <el-table-column prop="mvPath" label="视频地址" width="200"></el-table-column>
      <el-table-column prop="pid" label="视频归类" width="120"></el-table-column>
      <el-table-column prop="drama" label="剧集" width="120"></el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="200"></el-table-column>
      <!-- <el-table-column fixed="right" label="操作" width="150">
        <template slot-scope="scope">
          <el-button @click="checkGrade(scope.row.teacherId)" type="primary" size="small">编辑</el-button>
          <el-button @click="deleteById(scope.row.teacherId)" type="danger" size="small">删除</el-button>
        </template>
      </el-table-column> -->
    </el-table>
    <div class="my-pagination">
      <paging @handleCurrentChange="criticsignal" ref="cripaging" ></paging>
    </div>


  </div>
</template>

<script>
import paging from '../common/page'
export default {
  data() {
    return {
      page:1,
      pagesize:5,
      tableData:[]
    };
  },
  created() {
    this.getResource();
  },
  components:{
    paging
  },
  methods: {
    getResource() {
      //分页查询所有试卷信息
      let params = {
        page:this.page,
        size:this.pagesize,
        mvId:'123456'
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
          res.data.data.records.map(item=>{
          })
          that.tableData = res.data.data.records
        }
        let pageObj = {
          currentPage:res.data.data.page,
          total:res.data.data.total,
          pagesize:res.data.data.size
        }
        that.$refs.cripaging.init(pageObj);
      })
    },
    criticsignal(code){
      this.page = code;
      this.getResource()
    }

  }
};
</script>
<style lang="scss" scoped>
.all {
  padding: 0px 40px;
  .page {
    margin-top: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .edit {
    margin-left: 20px;
  }
  .el-table tr {
    background-color: #dd5862 !important;
  }
}
.el-table .warning-row {
  background: #000 !important;
}

.el-table .success-row {
  background: #dd5862;
}
.sub-img{
  width: 98px;
  height: 90px;
  object-fit:cover;
}
</style>
