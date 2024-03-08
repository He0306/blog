<template>
  <div>
    <h-nav></h-nav>
    <div class="container">
      <el-row :gutter="30">
        <el-col :sm="24" :md="16" style="transition:all .5s ease-out;margin-bottom:30px;">
          <h-tagList></h-tagList>
        </el-col>
        <el-col :sm="24" :md="8">
          <h-rightlist></h-rightlist>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import header from "../components/header";
import rightlist from "../components/rightlist";
import tagList from "../components/tagList";

export default {
  name: "tagList",
  data(){
    return{

    }
  },
  created() {

  },
  components: {
    'h-nav': header,
    'h-tagList': tagList,
    'h-rightlist': rightlist,
  },
  methods:{

  }
}
</script>

<style scoped>

</style>
