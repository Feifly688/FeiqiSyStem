<!--
*@layoutView.vue
*@author Feiqi
*@date 2024/10/07 下午 5:14
-->
<template>
    <div id="app">
        <el-container>
            <el-aside style="overflow: hidden; min-height: 100vh;background-color: #012041; width: 250px">
                <div align="center">
                    <img src="../assets/飞起logo.png" style="width:35px; position: relative; top: 7px" alt="">
                    <span style="font-size: 25px; font-family: 楷体,serif;color: white">Feiqi人脸情绪识别系统</span>
                </div>
                <el-menu
                        router
                        default-active="$route.path"
                        class="el-menu-vertical-demo el-menu"
                        @open="handleOpen"
                        @close="handleClose"
                        background-color="#012041"
                        text-color="#fff"
                        active-text-color="deepskyblue">
                    <el-menu-item index="/home">
                        <i class="el-icon-s-home"></i>
                        <span slot="title">首页</span>
                    </el-menu-item>
                    <el-menu-item index="/prehandle">
                        <i class="el-icon-menu"></i>
                        <span slot="title">数据集可视化展示</span>
                    </el-menu-item>
                    <el-submenu style="background-color: #000b17" index="">
                        <template slot="title">
                            <i class="el-icon-cpu"></i>
                            <span>算法选择</span>
                        </template>
                        <el-menu-item-group>
                            <el-menu-item index="/alg1">算法1-CNN卷积神经网络</el-menu-item>
                        </el-menu-item-group>
                        <el-menu-item-group>
                            <el-menu-item index="/alg2">算法2-K近邻</el-menu-item>
                        </el-menu-item-group>
                        <el-menu-item-group>
                            <el-menu-item index="/alg3">算法3-Bayes贝叶斯</el-menu-item>
                        </el-menu-item-group>
                        <el-menu-item-group>
                            <el-menu-item index="/alg4">算法4-决策树</el-menu-item>
                        </el-menu-item-group>
                    </el-submenu>
                    <el-menu-item index="/analysis">
                        <i class="el-icon-document"></i>
                        <span slot="title">结果分析</span>
                    </el-menu-item>
                    <!--<el-menu-item index="/user">
                        <i class="el-icon-user"></i>
                        <span slot="title">个人设置</span>
                    </el-menu-item>-->
                    <el-menu-item index="/admin">
                        <i class="el-icon-s-custom"></i>
                        <span slot="title">用户管理</span>
                    </el-menu-item>
                </el-menu>
            </el-aside>
            <el-main class="el-main" style="background-color: #fff">
                <div>
                    <!--顶部状态栏-->
                    <el-row class="el-row grid-content" style="background-color: #012041">
                        <el-col>
                            <!--下拉菜单-->
                            <el-dropdown style="float: right;line-height: 30px;margin-right: 20px">
                                <span class="el-dropdown-link" style="color: white;font-size: 16px">
                                     {{ user.username }}
                                    <i class="el-icon-arrow-down el-icon--right" style="margin-left: -3px"></i>
                                </span>
                                <el-dropdown-menu slot="dropdown">
                                    <el-dropdown-item icon="el-icon-circle-close">
                                        <span @click="logout()">退出登录</span>
                                    </el-dropdown-item>
                                </el-dropdown-menu>
                            </el-dropdown>
                        </el-col>
                    </el-row>
                </div>
                <!---->
                <router-view/>
            </el-main>
        </el-container>
    </div>
</template>

<script>
import Cookies from 'js-cookie';

export default {
    data() {
        return {
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
        }
    },
    methods: {
        handleOpen(key, keyPath) {
            console.log(key, keyPath);
        },
        handleClose(key, keyPath) {
            console.log(key, keyPath);
        },
        logout() {
            localStorage.removeItem("user");
            this.$router.push('/login')
        },
    },
    mounted() {
        /*区分关闭和刷新，关闭退出登录 start*/
        window.onload = function () {
            if (!window.sessionStorage["tempFlag"]) {
                localStorage.clear()
                location.reload();   //强制跳到登陆页
            } else {
                window.sessionStorage.removeItem("tempFlag");
            }
        }
        window.onunload = function () {
            window.sessionStorage["tempFlag"] = true;
        }
        window.onbeforeunload = function () {
            window.sessionStorage["tempFlag"] = true;
        }
    },

}
</script>
<style>
.el-menu { /***去除毛边***/
    border-right: none !important;
}

.el-row {
    margin-top: -20px;
    margin-left: -20px;
    height: 30px;
}

.grid-content {
    width: 1765px;
    min-height: 36px;
}

.el-dropdown-link {
    cursor: pointer;
    color: #409EFF;
}

.el-icon-arrow-down {
    font-size: 12px;
}

</style>
