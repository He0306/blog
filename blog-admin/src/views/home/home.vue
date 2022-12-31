<template>
  <div>
    <el-row :gutter="10" style="margin-bottom: 70px">
      <el-col :span="6">
        <el-card style="color: #E6A23C">
          <div><i class="el-icon-help"></i>访问量</div>
          <div style="padding: 10px 0;text-align: center;font-weight: bold">
            100000
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #409EFF">
          <div><i class="el-icon-user-solid"></i>用户量</div>
          <div style="padding: 10px 0;text-align: center;font-weight: bold">
            {{ userCount }}
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #F56C6C">
          <div><i class="el-icon-document"></i>文章量</div>
          <div style="padding: 10px 0;text-align: center;font-weight: bold">
            {{ articleCount }}
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #67C23A">
          <div><i class="el-icon-view"></i>浏览量</div>
          <div style="padding: 10px 0;text-align: center;font-weight: bold">
            {{ viewsCount }}
          </div>
        </el-card>
      </el-col>
      <!-- 文章浏览量排行 -->
      <el-col :span="24" style="margin-top: 20px">
        <el-card>
          <div class="e-title">文章浏览量排行</div>
          <div style="height:420px">
            <v-chart :options="ariticleRank" v-loading="loading"/>
          </div>
        </el-card>
      </el-col>
      <!-- 分类数据统计 -->
      <el-col :span="12" style="margin-top: 20px">
        <el-card>
          <div class="e-title">文章分类统计</div>
          <div style="height:375px">
            <v-chart :options="category" v-loading="loading"/>
          </div>
        </el-card>
      </el-col>
      <!-- 文章标签统计 -->
      <el-col :span="12" style="margin-top: 20px">
        <el-card>
          <div class="e-title">文章标签统计</div>
          <div style="height:350px;" v-loading="loading">
            <tag-cloud style="margin-top:1.5rem" :data="tagDTOList"/>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import homeApi from "@/api/home/home";


export default {
  name: "home",
  data() {
    return {
      loading: false,
      userCount: '',
      viewsCount: '',
      articleCount: '',
      tagDTOList: [],
      category: {
        color: [
          "#7EC0EE",
          "#FF9F7F",
          "#FFD700",
          "#a91c1c",
          "#E066FF",
          "#C0FF3E"
        ],
        legend: {
          data: [],
          bottom: "bottom"
        },
        tooltip: {
          trigger: "item"
        },
        series: [
          {
            name: "文章分类",
            type: "pie",
            roseType: "radius",
            data: []
          }
        ]
      },
      ariticleRank: {
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "cross"
          }
        },
        color: ["#1f66a8"],
        grid: {
          left: "0%",
          right: "0%",
          bottom: "0%",
          top: "10%",
          containLabel: true
        },
        xAxis: {
          data: []
        },
        yAxis: {},
        series: [
          {
            name: ["浏览量"],
            type: "bar",
            data: []
          }
        ]
      }
    }
  },
  created() {
    this.getIndex()
  },
  methods: {
    getIndex() {
      this.loading = true
      homeApi.home().then(res => {
        this.userCount = res.data.userCount
        this.viewsCount = res.data.viewsCount
        this.articleCount = res.data.articleCount

        res.data.categoryList.forEach(item => {
          this.category.series[0].data.push({
            value: item.articleCount,
            name: item.categoryName
          });
          this.category.legend.data.push(item.categoryName);
        })

        res.data.articleList.forEach(item => {
          this.ariticleRank.series[0].data.push(item.viewCount);
          this.ariticleRank.xAxis.data.push(item.title)
        })

        res.data.tagList.forEach(item => {
          this.tagDTOList.push({
            id: item.id,
            name: item.tagName
          })
        })
        this.loading = false
      })
    }
  }
}
</script>

<style>
.card-icon-container {
  display: inline-block;
  font-size: 3rem;
}
.area-wrapper {
  display: flex;
  justify-content: center;
}
.card-desc {
  font-weight: bold;
  float: right;
}
.card-title {
  margin-top: 0.3rem;
  line-height: 18px;
  color: rgba(0, 0, 0, 0.45);
  font-size: 1rem;
}
.card-count {
  margin-top: 0.75rem;
  color: #666;
  font-size: 1.25rem;
}
.echarts {
  width: 100%;
  height: 100%;
}
.e-title {
  font-size: 13px;
  color: #202a34;
  font-weight: 700;
}
</style>