/**
 * UserService
 *
 * @author Feiqi
 * @date 2024/10/06  下午 8:34
 */
package com.xfy.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xfy.common.JwtTokenUtils;
import com.xfy.dao.UserDao;
import com.xfy.entity.Params;
import com.xfy.entity.User;
import com.xfy.exception.CustomException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserDao userDao;

    /*查询用户*/
    public PageInfo<User> findUser(Params params) {
        // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 接下来的查询会自动按照当前开启的分页设置来查询
        List<User> list = userDao.findUser(params);
        return PageInfo.of(list);
    }

    /*添加（注册）用户*/
    public void addUser(User user) {
        /*用户名一定要有*/
        if (user.getUsername() == null || "".equals(user.getUsername())) {
            throw new CustomException("用户名不能为空！");
        }
        /**
         * 进行重复性判断，不允许有用户的用户名相同
         * 只要根据用户名去数据库查询一下就行了
         */
        User curuser = userDao.findByUserName(user.getUsername());
        if (curuser != null) {
            throw new CustomException("用户名不能重复！");
        }

        user.setRole(0);
        if (user.getSex() == null) user.setSex("男");
        if (user.getPassword() == null) user.setPassword("123");
        userDao.insertSelective(user);
    }

    /*编辑用户*/
    public void editUser(User user) {
        userDao.updateByPrimaryKeySelective(user);
    }

    /*删除用户*/
    public void deleteUser(Integer id) {
        userDao.deleteByPrimaryKey(id);
    }

    /*用户登录*/
    public User userLogin(User user) {
        /*1.非空判断*/
        if (user.getUsername() == null || "".equals(user.getUsername())) {
            throw new CustomException("用户名不能为空！");
        }
        if (user.getPassword() == null || "".equals(user.getPassword())) {
            throw new CustomException("密码不能为空！");
        }
        /*2.从数据库中根据所输入用户名和密码进行查询*/

        User curuser = userDao.findByUserNameAndPassword(user.getUsername(), user.getPassword());
        if (curuser == null) {
            /*3.如果查出来没有，则说明输入的用户名或密码有误，提示用户登录失败*/
            throw new CustomException("用户名或密码错误！");
        }
        /*4.如果查出来有，则说明有该用户，且用户名和密码都正确*/
        /*生成登录用户对应的token，然后跟着当前用户一起返回到前台*/
        String token = JwtTokenUtils.generateToken(curuser.getId().toString(), curuser.getPassword());
        curuser.setToken(token);
        return curuser;
    }
    /*根据id查询*/
    public User findUserById(Integer id) {
        return userDao.selectByPrimaryKey(id);
    }
}