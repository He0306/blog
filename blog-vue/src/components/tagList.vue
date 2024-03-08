<template>
  <div class="tRewardBox tcommonBox">
    <header>
      <h1>标签</h1>
    </header>
    <h2 style="margin-left: 50px">
      <el-row>
        <el-button type="success" v-for="item in tagName" :key="item">{{item}}</el-button>
      </el-row>
    </h2>
  </div>
</template>

<script>
import {selectTagName} from '../api/tag'

export default {
  name: "tagList",
  data(){
    return{
      tagName: []
    }
  },
  created() {
    this.getTagName()
  },
  methods:{
    getTagName(){
      selectTagName().then((res)=>{
        this.tagName = res
        console.log(res)
      })
    }
  }
}
</script>

<style scoped>

</style>
