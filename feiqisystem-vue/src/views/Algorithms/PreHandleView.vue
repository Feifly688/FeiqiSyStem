<!--
*@VisualView
*@author Feiqi
*@date 2024/10/05 下午 8:13
-->
<template>
    <div>
        <div align="center">
            <span style="font-size: 20px">基于CK+的人脸情绪识别</span>
        </div>
        <el-card shadow="hover" class="my-card">
            <div class="chart-container" style="width: 2000px;">
                <div id="polar" style="width: 30%;height: 600px;margin-left: 130px;"></div>
                <img src="/pre-img/merged_image_with_labels.jpg" alt="" style="width: 30%;height: 600px;margin-left: 300px">
            </div>
        </el-card>
    </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
    name: "PreHandleView",
    props: {},
    components: {},
    data() {
        return {
            counts: [],
        }
    },
    mounted() {
        this.readDataFromFile();
    },
    methods: {
        readDataFromFile() {
            // 使用fetch API来读取本地文本数据
            fetch('/pre-img/emotion_image_counts.txt')
                    .then(response => response.text())
                    .then(textData => {
                        let lines = textData.split('\n');
                        lines.forEach(line => {
                            if (line.trim() !== "") {
                                let values = line.split(": ");
                                this.counts.push({
                                    emotion: values[0],
                                    count: parseInt(values[1])
                                })
                            }
                        });
                        // 数据读取完成后，调用绘制图表的方法
                        this.initPolar()
                    });
        },
        initPolar() {
            let chartDom = document.getElementById('polar');
            let myChart = echarts.init(chartDom);
            let option;

            option = {
                title: [
                    {
                        text: '八种情绪样本统计',
                        left: 'center'
                    }
                ],
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    data: this.counts.map(item => item.emotion),
                    axisTick: {
                        alignWithLabel: true
                    },
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        data: this.counts.map(item => item.count),
                        type: 'bar',
                        showBackground: true,
                        backgroundStyle: {
                            color: 'rgba(180, 180, 180, 0.2)'
                        }
                    }
                ]
            };
            option && myChart.setOption(option);
        }
    },
}
</script>

<style scoped>
.chart-container {
    display: flex;
    flex-wrap: wrap;
}

.my-card {
    border: 1px solid #ddd; /* 设置卡片的边框 */
    border-radius: 5px;
    padding: 10px;
    position: relative;
}

/*纵向分割线*/
.my-card::before {
    content: "";
    position: absolute;
    top: 2.5%;
    left: 50%;
    transform: translateX(-50%);
    height: 95%;
    width: 1px;
    background-color: #ddd;
}

/*横向分割线*/
.my-card::after {
    content: "";
    position: absolute;
    top: 50%;
    left: 100%;
    transform: translate(-50%, -50%);
    width: 100%;
    height: 1px;
    background-color: #ddd; /* 设置分割线的颜色 */
}
</style>
