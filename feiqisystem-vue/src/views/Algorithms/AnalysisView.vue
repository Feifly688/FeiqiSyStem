<!--
*@AnalysisView
*@author Feiqi
*@date 2024/10/05 下午 8:40
-->
<template>
    <div>
        <div align="center"><span style="font-size: 20px">模型结果评估对比</span></div>
        <el-card shadow="hover" class="my-card">
            <div class="chart-container" style="width: 2000px;">
                <div id="report1" style="width: 30%;height: 500px;margin-left: 130px;"></div>
                <div id="report2" style="width: 30%;height: 500px;margin-left: 130px;"></div>
                <div id="report3" style="width: 30%;height: 500px;margin-left: 130px;"></div>
                <div id="report4" style="width: 30%;height: 500px;margin-left: 130px;"></div>
            </div>
        </el-card>
    </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
    name: "AnalysisView",
    props: {},
    components: {},
    data() {
        return {
            accuracy: [],
            precision: [],
            recall: [],
            f1score: [],
        }
    },
    mounted() {
        this.readDataFromFile();
    },
    methods: {
        readDataFromFile() {
            const filePaths = [
                '/vgg16/Vgg16.txt',
                '/knn/TestKnn.txt',
                '/bayes/ValNaiveBayes.txt',
                '/decisionTree/TestDecisionTree.txt'
            ];
            // 使用fetch API来读取本地文本数据
            filePaths.forEach((filePath, index) => {
                fetch(filePath)
                        .then(response => response.text())
                        .then(textData => {
                            let lines = textData.split('\n');
                            lines.shift(); // 跳过标题行

                            let accuracyValue, precisionValue, recallValue, f1scoreValue;

                            lines.forEach(line => {
                                let values = line.split(/\s+/);
                                if (values[1] === 'accuracy') {
                                    accuracyValue = parseFloat(values[2]);
                                } else if (values[0] === 'weighted' && values[1] === 'avg') {
                                    precisionValue = parseFloat(values[2]);
                                    recallValue = parseFloat(values[3]);
                                    f1scoreValue = parseFloat(values[4]);
                                }
                            });

                            // 将读取到的值分别存入对应的数组
                            this.accuracy.push(accuracyValue);
                            this.precision.push(precisionValue);
                            this.recall.push(recallValue);
                            this.f1score.push(f1scoreValue);

                            // 当四个文件都读取完成后，调用绘制报告的方法（假设存在此方法）
                            if (index === filePaths.length - 1) {
                                this.drawReport1();
                                this.drawReport2();
                                this.drawReport3();
                                this.drawReport4();
                            }
                        });
            });
        },
        drawReport1() {
            var chartDom = document.getElementById('report1');
            var myChart = echarts.init(chartDom);
            var option;

            option = {
                title: {
                    text: '准确度对比',
                    left: 'center'
                },
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
                    data: ['Vgg16', 'KNN', 'Bayes', 'decisionTree']
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        data: this.accuracy,
                        type: 'bar',
                        showBackground: true,
                        backgroundStyle: {
                            color: 'rgba(180, 180, 180, 0.2)'
                        },
                        itemStyle: {
                            color: function(params) {
                                return '#a90000';
                            }
                        }
                    }
                ]
            };
            option && myChart.setOption(option);
        },
        drawReport2() {
            var chartDom = document.getElementById('report2');
            var myChart = echarts.init(chartDom);
            var option;

            option = {
                title: {
                    text: '精确率对比',
                    left: 'center'
                },
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
                    data: ['Vgg16', 'KNN', 'Bayes', 'decisionTree']
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        data: this.precision,
                        type: 'bar',
                        showBackground: true,
                        backgroundStyle: {
                            color: 'rgba(180, 180, 180, 0.2)'
                        },
                        itemStyle: {
                            color: function(params) {
                                return 'green';
                            }
                        }
                    }
                ]
            };
            option && myChart.setOption(option);
        },
        drawReport3() {
            var chartDom = document.getElementById('report3');
            var myChart = echarts.init(chartDom);
            var option;

            option = {
                title: {
                    text: '召回率对比',
                    left: 'center'
                },
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
                    data: ['Vgg16', 'KNN', 'Bayes', 'decisionTree']
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        data: this.recall,
                        type: 'bar',
                        showBackground: true,
                        backgroundStyle: {
                            color: 'rgba(180, 180, 180, 0.2)'
                        },
                        itemStyle: {
                            color: function(params) {
                                return 'orange';
                            }
                        }
                    }
                ]
            };
            option && myChart.setOption(option);
        },
        drawReport4() {
            var chartDom = document.getElementById('report4');
            var myChart = echarts.init(chartDom);
            var option;

            option = {
                title: {
                    text: 'f1值对比',
                    left: 'center'
                },
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
                    data: ['Vgg16', 'KNN', 'Bayes', 'decisionTree']
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        data: this.f1score,
                        type: 'bar',
                        showBackground: true,
                        backgroundStyle: {
                            color: 'rgba(180, 180, 180, 0.2)'
                        }
                    }
                ]
            };
            option && myChart.setOption(option);
        },
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
