<!--
*@UserView
*@author Feiqi
*@date 2024/10/08 上午 7:57
-->
<template>
    <div>
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
</template>

<script>
import request from "@/utils/request";

export default {
    name: "UserView",
    data() {
        return {
            params: {
                username: '',
            },
        }
    },
    /*created里面是页面加载的时候所做的事情*/
    created() {
        this.findUser();
    },
    methods: {

        /*中文表示角色*/
        formatRole(row) {
            if (row.role === 0) return "学生"
            else if (row.role === 1) return "老师"
            else return "管理员"
        },
    }
}
</script>

<style scoped>

</style>