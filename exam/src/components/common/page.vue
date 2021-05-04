<template>
  <div>
    <div></div>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-size="pagesize"
      :page-sizes="pagesizes"
      :layout="layout"
      :total="total">
    </el-pagination>
  </div>
</template>

<script>
export default {
  data() {
    return {
      currentPage:1,
      pagesize:5,
      pagesizes:[5, 10, 15, 20],
      layout:'total, sizes, prev, pager, next, jumper',
      total:0,
    }
  },
  methods: {
    init(data){
      this.$nextTick(()=>{
        this.currentPage = data.currentPage||1;
        this.pagesize = data.pagesize||5;
        this.pagesizes = data.pagesizes||[5, 10, 15, 20];
        this.layout = data.layout||'total, sizes, prev, pager, next, jumper';
        this.total = data.total||0;
      })
    },
    // 当前页改变时触发
    handleCurrentChange(val) {

      this.$emit('handleCurrentChange',val);
    },
    // 当每页条数改变时触发
    handleSizeChange(val) {
      this.currentPage = 1;
      this.$emit('sizeChange',val);
    },
  }
}
</script>

<style scoped>
</style>
