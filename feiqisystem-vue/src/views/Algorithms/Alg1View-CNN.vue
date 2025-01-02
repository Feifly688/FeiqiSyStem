<!--
*@Alg1View
*@author Feiqi
*@date 2024/10/05 下午 8:35
-->
<template>
    <div>
        <div align="center"><span style="font-size: 20px">使用VGG16的CNN算法</span></div>
        <el-card shadow="hover" class="my-card">
            <div class="chart-container" style="width: 2000px;">
                <div id="loss" style="width: 30%;height: 500px;margin-left: 130px;"></div>
                <div id="acc" style="width: 30%;height: 500px;margin-left: 260px;"></div>
                <div id="report" style="width: 30%;height: 500px;margin-left: 135px;margin-top: 30px"></div>
                <img src="/vgg16/confusion_matrix.png" alt="" style="width: 30%;height: 500px;margin-left: 300px">
            </div>
        </el-card>
    </div>
</template>

<script>
import * as echarts from "echarts";

export default {
    name: "CNN",
    props: {},
    components: {},
    data() {
        return {
            data1: [],
            data2: {
                precision: [],
                recall: [],
                f1score: []
            }
        }
    },
    mounted() {
        this.readDataFromFile1();
        this.readDataFromFile2();
    },
    methods: {
        readDataFromFile1() {
            // 使用fetch API来读取本地文本数据
            fetch('/vgg16/training_log.txt')
                    .then(response => response.text())
                    .then(textData => {
                        let lines = textData.split('\n');
                        lines.shift(); // 跳过标题行

                        lines.forEach(line => {
                            let values = line.split('\t');
                            let epoch = parseInt(values[0]);
                            let trainLoss = parseFloat(values[1]);
                            let valLoss = parseFloat(values[2]);
                            let testLoss = parseFloat(values[3]);
                            let trainAcc = parseFloat(values[4]);
                            let valAcc = parseFloat(values[5]);
                            let testAcc = parseFloat(values[6]);

                            this.data1.push({
                                epoch: epoch,
                                trainLoss: trainLoss,
                                valLoss: valLoss,
                                testLoss: testLoss,
                                trainAcc: trainAcc,
                                valAcc: valAcc,
                                testAcc: testAcc
                            });
                        });
                        // 数据读取完成后，调用绘制图表的方法
                        this.drawLossChart();
                        this.drawAccChart();
                    });
        },
        drawLossChart() {
            let chartDom = document.getElementById('loss');
            let myChart = echarts.init(chartDom);
            let option;

            option = {
                title: {
                    text: '模型训练损失值曲线',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'axis'
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                toolbox: {
                    feature: {
                        saveAsImage: {}
                    }
                },
                legend: {
                    data: ['Train Loss', 'Val Loss', 'Test Loss'],
                    top: '5%'
                },
                xAxis: {
                    type: 'category',
                    name: '轮数',
                    nameLocation: 'middle',
                    nameGap: 20,
                    nameTextStyle: {
                        fontSize: 17, // 设置名称字体大小
                        color: '#333', // 设置名称字体颜色
                    },
                    boundaryGap: false,
                    data: this.data1.map(item => item.epoch)
                },
                yAxis: {
                    type: 'value',
                },
                series: [
                    {
                        name: 'Train Loss',
                        type: 'line',
                        data: this.data1.map(item => item.trainLoss)
                    },
                    {
                        name: 'Val Loss',
                        type: 'line',
                        data: this.data1.map(item => item.valLoss)
                    },
                    {
                        name: 'Test Loss',
                        type: 'line',
                        data: this.data1.map(item => item.testLoss)
                    },
                ]
            };
            option && myChart.setOption(option);
        },

        drawAccChart() {
            let chartDom = document.getElementById('acc');
            let myChart = echarts.init(chartDom);
            let option;

            option = {
                title: {
                    text: '模型训练精确度曲线',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'axis',
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                toolbox: {
                    feature: {
                        saveAsImage: {}
                    }
                },
                legend: {
                    data: ['Train Acc', 'Val Acc', 'Test Acc'],
                    top: '5%'
                },
                xAxis: {
                    type: 'category',
                    name: '轮数',
                    nameLocation: 'middle',
                    nameGap: 20,
                    nameTextStyle: {
                        fontSize: 17, // 设置名称字体大小
                        color: '#333' // 设置名称字体颜色
                    },
                    boundaryGap: false,
                    data: this.data1.map(item => item.epoch)
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name: 'Train Acc',
                        type: 'line',
                        data: this.data1.map(item => item.trainAcc)
                    },
                    {
                        name: 'Val Acc',
                        type: 'line',
                        data: this.data1.map(item => item.valAcc)
                    },
                    {
                        name: 'Test Acc',
                        type: 'line',
                        data: this.data1.map(item => item.testAcc)
                    }
                ]
            };
            option && myChart.setOption(option);
        },

        readDataFromFile2() {
            // 使用fetch API来读取本地文本数据
            fetch('/vgg16/Vgg16.txt')
                    .then(response => response.text())
                    .then(textData => {
                        let lines = textData.split('\n');
                        lines.shift(); // 跳过标题行

                        lines.forEach(line => {
                            let values = line.split(/\s+/);
                            if (values.length > 3) {
                                this.data2.precision.push(parseFloat(values[2]));
                                this.data2.recall.push(parseFloat(values[3]));
                                this.data2.f1score.push(parseFloat(values[4]));
                            }
                        });
                        this.drawReport();
                    });
        },
        drawReport() {
            var chartDom = document.getElementById('report');
            var myChart = echarts.init(chartDom);
            var option;

            option = {
                title: {
                    text: '分类报告',
                    left: 'center'
                },
                legend: {
                    data: ['Precision', 'Recall', 'F1-Score'],
                    top: '3.5%',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item'
                },
                radar: {
                    indicator: [
                        {name: 'angry', max: 1},
                        {name: 'contempt', max: 1},
                        {name: 'disgust', max: 1},
                        {name: 'fear', max: 1},
                        {name: 'happy', max: 1},
                        {name: 'neutral', max: 1},
                        {name: 'sad', max: 1},
                        {name: 'surprise', max: 1}
                    ],
                },
                series: [
                    {

                        name: 'Report',
                        type: 'radar',
                        data: [
                            {
                                value: this.data2.precision,
                                name: 'Precision'
                            },
                            {
                                value: this.data2.recall,
                                name: 'Recall'
                            },
                            {
                                value: this.data2.f1score,
                                name: 'F1-Score'
                            }
                        ]
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
    left: 50%;
    transform: translate(-50%, -50%);
    width: 95%;
    height: 1px;
    background-color: #ddd; /* 设置分割线的颜色 */
}
</style>
