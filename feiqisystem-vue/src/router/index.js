import Vue from 'vue'
import VueRouter from 'vue-router'
import AnalysisView from "@/views/Algorithms/AnalysisView";
import AdminView from "@/views/AdminView";
import LoginView from "@/views/LoginView";
import LayoutView from "@/views/Layout";
import HomeView from "@/views/HomeView";
import UserView from "@/views/UserView";
import PreHandleView from "@/views/Algorithms/PreHandleView";
import Alg2ViewKNN from "@/views/Algorithms/Alg2View-KNN";
import Alg3ViewBayes from "@/views/Algorithms/Alg3View-Bayes";
import Alg4ViewDecisionTree from "@/views/Algorithms/Alg4View-DecisionTree";
import Alg1ViewCNN from "@/views/Algorithms/Alg1View-CNN";

Vue.use(VueRouter)

const routes = [
    {
        path: '/login',
        name: 'login',
        component: LoginView,
    },
    {
        path: '/',
        name: '',
        component: LayoutView,
        children: [
            {
                path: '/home',
                name: 'home',
                component: HomeView
            },
            {
                path: '/prehandle',
                name: 'prehandle',
                component: PreHandleView
            },
            {
                path: '/alg1',
                name: 'alg2',
                component: Alg1ViewCNN
            },
            {
                path: '/alg2',
                name: 'alg2',
                component: Alg2ViewKNN
            },
            {
                path: '/alg3',
                name: 'alg3',
                component: Alg3ViewBayes
            },
            {
                path: '/alg4',
                name: 'alg4',
                component: Alg4ViewDecisionTree
            },
            {
                path: '/analysis',
                name: 'analysis',
                component: AnalysisView
            },
            {
                path: '/user',
                name: 'user',
                component: UserView
            },
            {
                path: '/admin',
                name: 'admin',
                component: AdminView
            }
        ]
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

/*路由守卫*/
router.beforeEach((to, from, next) => {
    if (to.path === '/login') {
        next();
    }
    const user = localStorage.getItem("user");
    if (!user && to.path !== '/login') {
        return next('/login');
    }
    if (JSON.parse(user).role !== 2 && to.path === '/admin') { //若不是管理员，则不能访问用户管理
        alert("你不是管理员，没有权限访问！")
        return next('/')
    }
    next();
})

export default router
