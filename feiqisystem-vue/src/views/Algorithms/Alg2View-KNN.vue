<!--
*@Alg1View
*@author Feiqi
*@date 2024/10/05 下午 8:35
-->
<template>
    <div>
        <div align="center"><span style="font-size: 20px">k近邻算法</span></div>
        <el-card shadow="hover" class="my-card">
            <div class="chart-container" style="width: 2000px;">
                <div id="valreport" style="width: 30%;height: 500px;margin-left: 135px;margin-top: 30px"></div>
                <img src="/knn/val_confusion_matrix.png" alt="" style="width: 30%;height: 500px;margin-left: 300px">
                <div id="testreport" style="width: 30%;height: 500px;margin-left: 135px;margin-top: 30px"></div>
                <img src="/knn/test_confusion_matrix.png" alt="" style="width: 30%;height: 500px;margin-left: 300px">
            </div>
        </el-card>
    </div>
</template>

<script>
import * as echarts from "echarts";

export default {
    name: "KNN",
    props: {},
    components: {},
    data() {
        return {
            data1: {
                precision: [],
                recall: [],
                f1score: []
            },
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
            fetch('/knn/ValKnn.txt')
                    .then(response => response.text())
                    .then(textData => {
                        let lines = textData.split('\n');
                        lines.shift(); // 跳过标题行

                        lines.forEach(line => {
                            let values = line.split(/\s+/);
                            if (values.length > 3) {
                                this.data1.precision.push(parseFloat(values[2]));
                                this.data1.recall.push(parseFloat(values[3]));
                                this.data1.f1score.push(parseFloat(values[4]));
                            }
                        });
                        this.drawValReport();
                    });
        },
        drawValReport() {
            var chartDom = document.getElementById('valreport');
            var myChart = echarts.init(chartDom);
            var option;

            option = {
                title: {
                    text: '验证集分类报告',
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

                        name: 'valReport',
                        type: 'radar',
                        data: [
                            {
                                value: this.data1.precision,
                                name: 'Precision'
                            },
                            {
                                value: this.data1.recall,
                                name: 'Recall'
                            },
                            {
                                value: this.data1.f1score,
                                name: 'F1-Score'
                            }
                        ]
                    }
                ]
            };
            option && myChart.setOption(option);
        },
        readDataFromFile2() {
            // 使用fetch API来读取本地文本数据
            fetch('/knn/TestKnn.txt')
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
                        this.drawTestReport();
                    });
        },
        drawTestReport() {
            var chartDom = document.getElementById('testreport');
            var myChart = echarts.init(chartDom);
            var option;

            option = {
                title: {
                    text: '训练集分类报告',
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

                        name: 'testReport',
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
