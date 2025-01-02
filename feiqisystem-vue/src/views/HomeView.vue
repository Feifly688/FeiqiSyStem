<template>
    <div>

        <div align="center">
            <el-card class="box-card" shadow="hover" style="background-color: #42b983;margin-top: 20px;width: 460px;">
                <span style="font-size: 25px; font-family: 楷体,serif;color: #fff">欢迎进入Feiqi人脸情绪识别系统！</span>
            </el-card>

            <el-card class="box-card" shadow="hover" style="background-color: #d9320d;margin-top: 35px;width: 200px;">
                <span style="margin-left: -5px;font-size: 25px; font-family: 楷体,serif;color: #fff">
                    <a href="/admin" style="color: #fff; text-decoration: none">用户管理</a>
                </span>
            </el-card>
        </div>

        <div style="margin-left: 100px;width: 2000px;margin-top: 100px">
            <el-card class="box-card" shadow="hover" style="margin-top: 35px;width: 300px;float: left">
                <span style="margin-left: 30px;font-size: 25px; font-family: 楷体,serif;color: #fff">
                    <a href="/prehandle" style="color: #fff; text-decoration: none">查看数据集可视化</a>
                </span>
            </el-card>

            <el-card class="box-card" shadow="hover" style="margin-left: 280px;width: 350px;float: left">
                <span style="margin-left: 55px;font-size: 25px; font-family: 楷体,serif;color: #fff">
                    <a href="/alg1" style="color: #fff; text-decoration: none">查看算法1-CNN</a>
                </span>
                <br/>
                <span style="margin-left: 55px;font-size: 25px; font-family: 楷体,serif;color: #fff">
                    <a href="/alg2" style="color: #fff; text-decoration: none">查看算法2-K近邻</a>
                </span>
                <br/>
                <span style="margin-left: 20px;font-size: 25px; font-family: 楷体,serif;color: #fff">
                    <a href="/alg3" style="color: #fff; text-decoration: none">查看算法3-Bayes贝叶斯</a>
                </span>
                <br/>
                <span style="margin-left: 47px;font-size: 25px; font-family: 楷体,serif;color: #fff">
                    <a href="/alg4" style="color: #fff; text-decoration: none">查看算法4-决策树</a>
                </span>
            </el-card>

            <el-card class="box-card" shadow="hover" style="margin-left: 270px;margin-top: 35px;width: 260px;float: left">
                <span style="margin-left: 35px;font-size: 25px; font-family: 楷体,serif;color: #fff">
                    <a href="/analysis" style="color: #fff; text-decoration: none">查看结果分析</a>
                </span>
            </el-card>

        </div>
    </div>
</template>
<script>
export default {
    name: 'HomeView',
    data() {
        return {}
    },
    methods: {}
}
</script>

<style scoped>
a {
    transition: background-color 0.5s;
}

a:hover {
    background-color: #67C23A;
}

.box-card {
    background-color: #409EFF;
    border-radius: 10px;
}

</style>
