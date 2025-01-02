/**
 * UserController
 *
 * @author Feiqi
 * @date 2024/10/06  下午 4:59
 */
package com.xfy.controller;

import com.github.pagehelper.PageInfo;
import com.xfy.common.JwtInterceptor;
import com.xfy.common.Result;
import com.xfy.entity.Params;
import com.xfy.entity.User;
import com.xfy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    @Resource
    private UserService userService;

    /**
     * Controller里的一个方法，就是平常web项目的一个接口的入口
     * 在这个方法上用RequestMapping加上一个url
     * 也可以指定请求方式：GET POST PUT DELETE
     *
     * @return
     */

    /*用户登录*/
    @PostMapping("/login")
    public Result userLogin(@RequestBody User user) {
        User loginUser = userService.userLogin(user);
        return Result.success(loginUser);
    }

    /*用户注册*/
    @PostMapping("/register")
    public Result userRegister(@RequestBody User user) {
        userService.addUser(user);
        return Result.success();
    }

    /*查询用户*/
    @GetMapping
    public Result findUser(Params params) {
        PageInfo<User> list = userService.findUser(params);
        return Result.success(list);
    }

    /*添加or编辑用户*/
    @PostMapping("/addUser")
    public Result addUser(@RequestBody User user) {
        if (user.getId() == null) {
            userService.addUser(user);
        } else {
            userService.editUser(user);
        }
        return Result.success();
    }

    /*删除用户*/
    @DeleteMapping("/deleteUser/{id}")
    public Result deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return Result.success();
    }
}