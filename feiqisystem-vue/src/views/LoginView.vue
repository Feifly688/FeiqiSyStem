<!--
*@LoginView
*@author Feiqi
*@date 2024/10/07 下午 4:41
-->
<template>
    <div class="center">
        <h1>登录||注册</h1>
        <div class="logon">
            <div :class="overlaylong">
                <!--登录界面-->
                <div class="overlaylong-Signin" v-if="disfiex === 0">
                    <h2 class="overlaylongH2 colorful">用户登录</h2>
                    <!--登录表单-->
                    <el-form v-model="admin" align="center">
                        <el-input v-model="admin.username" placeholder="用户名" style="padding-left: 5px;width: 200px"
                                  clearable>
                        </el-input><!--@keyup.enter.native回车触发事件-->
                        <el-input @keyup.enter.native="login()" v-model="admin.password" show-password placeholder="密码" style="padding-left: 5px;width: 200px"
                                  clearable>
                        </el-input>
                    </el-form>
                    <h3 style="color: #d9320d">忘记密码？</h3>
                    <button class="inupbutton1" @click="login()">登录</button>
                </div>
                <!---->
                <!--注册界面-->
                <div class="overlaylong-Signup" v-if="disfiex === 1">
                    <h2 class="overlaylongH2 colorful">用户注册</h2>
                    <!--注册表单-->
                    <el-form v-model="admin" align="center">
                        <el-input v-model="admin.username" placeholder="用户名" style="padding-left: 5px;width: 200px"
                                  clearable>
                        </el-input>
                        <el-input @keyup.enter.native="register()" v-model="admin.password" show-password placeholder="密码" style="padding-left: 5px;width: 200px"
                                  clearable>
                        </el-input>
                    </el-form>
                    <button class="inupbutton2" @click="register()">注册</button>
                </div>
                <!---->
            </div>
            <!--点击切换登录注册-->
            <div :class="overlaytitle">
                <div class="overlaytitle-Signin" v-if="disfiex === 0">
                    <h2 class="overlaytitleH2" style="font-family: 宋体">你好朋友，请注册火星账户</h2>
                    <p class="overlaytitleP" style="font-family: 新宋体">
                        Register your Mars account and start journey.
                    </p>
                    <div class="buttongohs" @click="Signin">注册</div>
                </div>
                <div class="overlaytitle-Signup" v-if="disfiex === 1">
                    <h2 class="overlaytitleH2">欢迎回来！前去登录</h2>
                    <p class="overlaytitleP">Welcome back to Mars!</p>
                    <div class="buttongohs" @click="Signup">登录</div>
                </div>
            </div>
            <!---->
        </div>

    </div>
</template>

<script>
import request from "@/utils/request";

export default {
    name: 'LoginView',
    data() {
        return {
            overlaylong: 'overlaylong',
            overlaytitle: 'overlaytitle',
            disfiex: 0,
            admin: {},
        }
    },
    methods: {
        /*切换到登录*/
        Signin() {
            this.overlaylong = "overlaylongleft"
            this.overlaytitle = "overlaytitleright"
            this.admin = {}
            setTimeout(() => {
                this.disfiex = 1
            }, 200)
        },
        /*切换到注册*/
        Signup() {
            this.overlaylong = "overlaylongright"
            this.overlaytitle = "overlaytitleleft"
            this.admin = {}
            setTimeout(() => {
                this.disfiex = 0
            }, 200)
        },
        /*刷新页面*/
        refresh() {
            location.reload();
        },
        /*登录请求*/
        login() {
            request.post("/admin/login", this.admin).then(res => {
                if (res.code === '0') {
                    this.$message({
                        message: "登录成功！",
                        type: 'success'
                    });
                    localStorage.setItem("user", JSON.stringify(res.data))
                    this.$router.push("/home")
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error',
                    });
                }
            });
        },
        register() {
            request.post("/admin/register", this.admin).then(res => {
                if (res.code === '0') {
                    this.$message({
                        message: "注册成功！",
                        type: 'success'
                    }, this.refresh());
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error',
                    });
                }
            });
        }
    }
}
</script>


<style>
/*.colorful {
    font-size: 30px;
    background-image: -webkit-gradient(linear, left top, right top, color-stop(0, #f22),
    color-stop(0.15, #f2f),
    color-stop(0.3, #22f),
    color-stop(0.45, #2ff),
    color-stop(0.5, #2f2),
    color-stop(0.7, #25f7e2),
    color-stop(0.85, #fcfc10),
    color-stop(1, #f22));
    color: transparent;
    -webkit-background-clip: text;
    -ms-background-clip: text
}*/

.center {
    width: 2030px;
    height: 1080px;
    background-image: url('@/assets/img1.jpg');
    background-size: 100% 100%;
    background-repeat: no-repeat;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
}

h1 {
    font-size: 35px;
    margin-top: -300px;
}

.logon {
    background-color: #fff;
    border-radius: 10px;
    box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
    /* position: relative;
    overflow: hidden; */
    width: 768px;
    max-width: 100%;
    min-height: 480px;
    margin-top: 20px;
    display: flex;
    background: -webkit-linear-gradient(right, #ed0909, #05f2fe);;
}

.overlaylong {
    border-radius: 10px 0 0 10px;
    width: 50%;
    height: 100%;
    background-color: #fff;
    display: flex;
    align-items: center;
    justify-content: center;
}

.overlaylongleft {
    border-radius: 0px 10px 10px 0px;
    width: 50%;
    height: 100%;
    background-color: #fff;
    transform: translateX(100%);
    transition: transform 0.6s ease-in-out;
    display: flex;
    align-items: center;
    justify-content: center;
}

.overlaylongright {
    border-radius: 10px 0 0 10px;
    width: 50%;
    height: 100%;
    background-color: #fff;
    transform: translateX(0%);
    transition: transform 0.6s ease-in-out;
    display: flex;
    align-items: center;
    justify-content: center;
}

.overlaytitle {
    border-radius: 0px 10px 10px 0px;
    width: 50%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0);
    display: flex;
    align-items: center;
    justify-content: center;
}

.overlaytitleH2 {
    font-size: 30px;
    color: #fff;
    margin-top: 5px;
}

.overlaytitleP {
    font-size: 15px;
    color: #fff;
    position: center;
}

.overlaytitleleft {
    border-radius: 0px 10px 10px 0px;
    width: 50%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0);
    display: flex;
    align-items: center;
    justify-content: center;
    transform: translateX(0%);
    transition: transform 0.6s ease-in-out;
}

.overlaytitleright {
    border-radius: 0px 10px 10px 0px;
    width: 50%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0);
    display: flex;
    align-items: center;
    justify-content: center;
    transform: translateX(-100%);
    transition: transform 0.6s ease-in-out;
}

.overlaytitle-Signin {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
}

.overlaytitle-Signup {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
}

.buttongohs {
    width: 130px;
    height: 40px;
    border-radius: 50px;
    border: 1px solid #fff;
    color: #fff;
    font-size: 15px;
    text-align: center;
    line-height: 40px;
    margin-top: 20px;
}

.overlaylongH2 {
    font-size: 25px;
    /* width: 250px; */
}

.overlaylong-Signin {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
}

.overlaylong-Signup {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
}

input {
    background-color: #eee;
    border: none;
    padding: 12px 15px;
    margin: 10px 0;
    width: 240px;
}

h3 {
    font-size: 10px;
    margin-top: 10px;
    cursor: pointer;
}

.inupbutton1 {
    background-color: #00eaff;
    border: none;
    width: 130px;
    height: 40px;
    border-radius: 50px;
    font-size: 15px;
    color: #fff;
    text-align: center;
    line-height: 40px;
    margin-top: 10px;
}

.inupbutton2 {
    background-color: #f72853;
    border: none;
    width: 130px;
    height: 40px;
    border-radius: 50px;
    font-size: 15px;
    color: #fff;
    text-align: center;
    line-height: 40px;
    margin-top: 10px;
}
</style>
