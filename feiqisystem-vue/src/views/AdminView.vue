<template>
    <div>
        <div>
            <!--搜索栏-->
            <div>
                <el-input @keyup.enter.native="findUser()" v-model="params.username" placeholder="请输入用户名" style="padding-left: 5px;width: 200px"
                          clearable>
                </el-input>
                <el-button type="warning" style="margin-left: 5px;" @click="findUser()">搜索</el-button>
                <br/><br/>
                <el-button type="primary" style="margin-left: 5px" @click="addEvent()">添加用户</el-button>
            </div>
            <!---->
            <!--用户信息表格-->
            <el-table
                    :data="tableData"
                    style="width: 100%">
                <el-table-column prop="id" label="编号"></el-table-column>
                <el-table-column prop="username" label="姓名"></el-table-column>
                <el-table-column
                        prop="role"
                        label="角色"
                        :formatter="formatRole"
                        :filters="[{ text: '学生', value: 0 }, { text: '老师', value: 1 },{text: '管理员', value: 2}]"
                        :filter-method="filterRole"
                        filter-placement="bottom-end">
                </el-table-column>
                <!---->
                <el-table-column prop="sex" label="性别"></el-table-column>
                <!--编辑删除操作-->
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="mini" type="primary" @click="editEvent(scope.row)">编辑</el-button>
                        <el-popconfirm icon-color="red" title="确定要删除吗？" @confirm="deleteSubmit(scope.row.id)">
                            <el-button slot="reference" size="mini" type="danger" style="margin-left: 5px">删除
                            </el-button>
                        </el-popconfirm>
                    </template>
                </el-table-column>
                <!---->
            </el-table>
        </div>
        <!--分页模块-->
        <div class="block">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="params.pageNum"
                    :page-sizes="[5, 10, 20]"
                    :page-size="params.pageSize"
                    layout="total, sizes, prev, pager, next"
                    :total="total">
            </el-pagination>
        </div>
        <!---->
        <div>
            <!--添加用户-->
            <el-dialog title="添加用户" :visible.sync="dialogFormVisible1" width="30%">
                <el-form :model="form">
                    <el-form-item label="用户名" label-width="15%">
                        <el-input v-model="form.username" autocomplete="off" style="width: 90%"></el-input>
                    </el-form-item>

                    <el-form-item label="性别" label-width="15%">
                        <el-radio v-model="form.sex" label="男">男</el-radio>
                        <el-radio v-model="form.sex" label="女" style="margin-left: -15px">女</el-radio>
                    </el-form-item>

                    <el-form-item label="角色" label-width="15%">
                        <el-input v-model="form.role" autocomplete="off" style="width: 90%"></el-input>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible1 = false">取 消</el-button>
                    <el-button type="primary" @click="submit()">确 定</el-button>
                </div>
            </el-dialog>
            <!---->
            <!--编辑用户-->
            <el-dialog title="修改用户信息" :visible.sync="dialogFormVisible2" width="25%">
                <el-form :model="form">
                    <el-form-item label="用户名" label-width="12%">
                        <el-input v-model="form.username" autocomplete="off" style="width: 90%"></el-input>
                    </el-form-item>

                    <el-form-item label="性别" label-width="12%">
                        <el-radio v-model="form.sex" label="男">男</el-radio>
                        <el-radio v-model="form.sex" label="女" style="margin-left: -15px">女</el-radio>
                    </el-form-item>

                    <el-form-item label="密码" label-width="15%">
                        <el-input v-model="form.password" autocomplete="off" style="width: 90%" show-password></el-input>
                    </el-form-item>

                    <el-form-item label="角色" class="important-label-color" label-width="12%">
                        <el-input v-model="form.role" autocomplete="off" style="width: 90%"></el-input>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible2 = false;findUser()">取 消</el-button>
                    <el-button type="primary" @click="submit()">确 定</el-button>
                </div>
            </el-dialog>
            <!---->
        </div>
    </div>
</template>
<script>

import request from "@/utils/request";

export default {
    name: 'AdminView',
    data() {
        return {
            params: {
                username: '',
                pageNum: 1,
                pageSize: 10,
            },
            tableData: [],
            form: {},
            total: 0,
            dialogFormVisible1: false,
            dialogFormVisible2: false,
        }
    },
    /*created里面是页面加载的时候所做的事情*/
    created() {
        this.findUser();
    },
    reset() {

    },
    methods: {
        /*查询所有用户和模糊查询*/
        findUser() {
            request.get("/admin", {
                params: this.params
            }).then(res => {
                if (res.code === '0') {
                    this.tableData = res.data.list;
                    this.total = res.data.total
                } else {
                }
            })
        },
        /*中文表示角色*/
        formatRole(row) {
            if (row.role === 0) return "学生"
            else if (row.role === 1) return "老师"
            else return "管理员"
        },
        /*分角色筛选用户*/
        filterRole(value, row) {
            return row.role === value;
        },
        /*页面大小*/
        handleSizeChange(pageSize) {
            this.params.pageSize = pageSize;
            this.findUser();
        },
        /*跳转页面*/
        handleCurrentChange(pageNum) {
            this.params.pageNum = pageNum;
            this.findUser();
        },
        /*点击添加按钮事件*/
        addEvent() {
            this.form = {}
            this.dialogFormVisible1 = true
        },
        /*点击编辑按钮事件*/
        editEvent(obj) {
            this.form = obj
            this.dialogFormVisible2 = true
        },
        /*添加编辑用户*/
        submit() {
            request.post("/admin/addUser", this.form).then(res => {
                if (res.code === '0') {
                    this.$message({
                        message: "操作成功！",
                        type: 'success',
                    }, this.dialogFormVisible1 = false, this.dialogFormVisible2 = false, this.findUser())
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            },)
        },
        /*删除用户方法*/
        deleteSubmit(id, curid) {
            curid = JSON.parse(localStorage.getItem("user")).id;

            if (id !== curid) {
                request.delete("/admin/deleteUser/" + id).then(res => {
                    if (res.code === '0') {
                        this.$message({
                            message: '删除成功！',
                            type: 'success'
                        }, this.findUser());
                    } else {
                        this.$message({
                            message: '删除失败！',
                            type: 'warning'
                        });
                    }
                })
            } else {
                this.$message({
                    message: '此为当前登录用户，不能删除！',
                    type: 'warning'
                });
            }
        },
    },
}
</script>
