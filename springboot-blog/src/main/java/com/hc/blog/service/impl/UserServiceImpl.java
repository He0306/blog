package com.hc.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hc.blog.common.constants.Constants;
import com.hc.blog.common.exception.ServiceException;
import com.hc.blog.common.lang.HttpCodeEnum;
import com.hc.blog.common.lang.R;
import com.hc.blog.config.RedisCache;
import com.hc.blog.entity.Role;
import com.hc.blog.entity.User;
import com.hc.blog.entity.UserRole;
import com.hc.blog.entity.dto.RegisterUserDto;
import com.hc.blog.entity.dto.UpdatePwdDto;
import com.hc.blog.entity.dto.UserRetrieveDto;
import com.hc.blog.entity.vo.*;
import com.hc.blog.handler.security.LoginUser;
import com.hc.blog.mapper.RoleMapper;
import com.hc.blog.mapper.RoleMenuMapper;
import com.hc.blog.mapper.UserMapper;
import com.hc.blog.mapper.UserRoleMapper;
import com.hc.blog.service.IMenuService;
import com.hc.blog.service.IUserService;
import com.hc.blog.utils.BeanCopyUtils;
import com.hc.blog.utils.JwtUtils;
import com.hc.blog.utils.SecurityUtils;
import com.hc.blog.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * ???????????????
 * </p>
 *
 * @author ??????
 * @since 2022-10-02
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    IMenuService menuService;

    @Autowired
    RoleMenuMapper roleMenuMapper;

    @Autowired
    RedisCache redisCache;

    @Autowired
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    //??????
    @Override
    public R login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        //????????????????????????
        if (Objects.isNull(authenticate)) {
            throw new ServiceException(HttpCodeEnum.USERNAME_PASSWORD_NO_EXIST);
        }
        //??????username  ??????token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String username = loginUser.getUser().getUsername();
        String token = JwtUtils.createJWT(username);
        //???token??????redis???????????????????????????2??????
        redisCache.setCacheObject(Constants.TOKEN_KEY + token, token, 2, TimeUnit.HOURS);
        //?????????????????????redis
        redisCache.setCacheObject(Constants.LOGIN_KEY + username, loginUser);

        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);

        UserLoginVo userLoginVo = new UserLoginVo(token, userInfoVo, menuService.getRoleMenu(loginUser.getUser().getId()));

        return R.okResult(userLoginVo);
    }

    @Override
    public R logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String username = loginUser.getUser().getUsername();
        redisCache.deleteObject(Constants.LOGIN_KEY + username);
        return R.okResult();
    }

    @Override
    public R register(RegisterUserDto registerUserDto) {
        if (!registerUserDto.getPassword().equals(registerUserDto.getConfirm())) {
            throw new ServiceException(HttpCodeEnum.PASSWORD_ATYPISM);
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, registerUserDto.getUsername());
        Long result = userMapper.selectCount(queryWrapper);
        if (result > 0) {
            throw new ServiceException(HttpCodeEnum.USERNAME_EXISTENCE);
        }
        User user = new User();
        user.setUsername(registerUserDto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(registerUserDto.getConfirm()));
        userMapper.insert(user);
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(Constants.DEFAULT_ROLE);
        userRoleMapper.insert(userRole);
        return R.okResult();
    }

    @Override
    public R pageList(Integer pageNum, Integer pageSize, String username, String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(username)) {
            queryWrapper.like(User::getUsername, username);
        }
        if (StringUtils.hasText(email)) {
            queryWrapper.like(User::getEmail, email);
        }
        Page<User> page = new Page<>(pageNum, pageSize);
        Page<User> userPage = userMapper.selectPage(page, queryWrapper);
        List<User> records = userPage.getRecords();
        long total = userPage.getTotal();
        List<UserListVo> list = new ArrayList<>();
        for (User user : records) {
            LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserRole::getUserId, user.getId());
            List<UserRole> userRoles = userRoleMapper.selectList(wrapper);
            for (UserRole userRole : userRoles) {
                Role role = roleMapper.selectById(userRole.getRoleId());
                UserListVo userListVo = new UserListVo();
                userListVo.setId(user.getId());
                userListVo.setUsername(user.getUsername());
                userListVo.setAvatar(user.getAvatar());
                userListVo.setNickName(user.getNickName());
                userListVo.setRoleName(role.getRoleName());
                userListVo.setEmail(user.getEmail());
                list.add(userListVo);
            }
        }
        UserPageVo userPageVo = new UserPageVo();
        userPageVo.setTotal(total);
        userPageVo.setUserListVos(list);

        return R.okResult(userPageVo);
    }

    @Override
    public R resetPassword(String id) {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getId, id);
        User user = new User();
        user.setPassword(bCryptPasswordEncoder.encode(Constants.DEFAULT_PASSWORD));
        userMapper.update(user, updateWrapper);
        return R.okResult();
    }

    @Transactional
    @Override
    public R deleteUserById(String id) {
        userMapper.deleteById(id);
        userRoleMapper.deleteByUserId(id);
        return R.okResult();
    }

    @Transactional
    @Override
    public R saveUser(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername());
        Long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new ServiceException(HttpCodeEnum.USERNAME_EXISTENCE);
        }
        queryWrapper.eq(User::getEmail,user.getEmail());
        Long aLong = userMapper.selectCount(queryWrapper);
        if (aLong > 0){
            throw new ServiceException(HttpCodeEnum.EMAIL_EXISTENCE);
        }
        user.setPassword(bCryptPasswordEncoder.encode(Constants.DEFAULT_PASSWORD));
        userMapper.insert(user);
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(user.getRoleId());
        userRoleMapper.insert(userRole);
        return R.okResult();
    }

    @Override
    public R updateUser(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail,user.getEmail());
        Long count = userMapper.selectCount(queryWrapper);
        if (count > 0){
            throw new ServiceException(HttpCodeEnum.EMAIL_EXISTENCE);
        }
        userMapper.updateById(user);
        userRoleMapper.userRoleByRoleId(user.getRoleId(), user.getId());
        return R.okResult();
    }

    @Transactional
    @Override
    public R deleteUserBatch(List<String> ids) {
        userMapper.deleteBatchIds(ids);
        for (String s : ids) {
            userRoleMapper.deleteByUserId(s);
        }
        return R.okResult();
    }

    @Override
    public R getUserById(String id) {
        List<Role> roles = roleMapper.selectList(null);
        User user = userMapper.selectById(id);
        String roleId = userRoleMapper.selectByUserId(id);
        UserInfoAndRoleIdsVo idsVo = new UserInfoAndRoleIdsVo(roleId, roles, user);
        return R.okResult(idsVo);
    }

    @Override
    public R updatePassword(UpdatePwdDto updatePwdDto) {
        // ???????????????????????????
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, SecurityUtils.getUserId());
        User currentUser = userMapper.selectOne(queryWrapper);
        //??????????????????????????????????????????
        if (!updatePwdDto.getNewPassword().equals(updatePwdDto.getConfirmPassword())) {
            throw new ServiceException(HttpCodeEnum.CONFIRMPASSWORD);
        }
        // ????????????????????????????????????????????????
        if (Objects.nonNull(currentUser) && bCryptPasswordEncoder.matches(updatePwdDto.getOldPassword(), currentUser.getPassword())) {
            User user = new User();
            user.setId(SecurityUtils.getUserId());
            user.setPassword(bCryptPasswordEncoder.encode(updatePwdDto.getNewPassword()));
            userMapper.updateById(user);
        } else {
            throw new ServiceException(HttpCodeEnum.OLDPASSWORD);
        }
        return R.okResult();
    }

    @Override
    public R getUserInfo() {
        String userId = SecurityUtils.getUserId();
        User user = userMapper.selectById(userId);
        return R.okResult(user);
    }

    /**
     * ???????????????
     *
     * @param email
     * @return
     */
    @Override
    public R sendCode(String email) {
        if (!StringUtils.hasText(email)){
            return R.errorResult(HttpCodeEnum.EMAIL_NON_NULL);
        }
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(email);
        String subject = "????????????";
        mailMessage.setSubject(subject);
        String code = ValidateCodeUtils.generateValidateCode(6).toString();
        String context = "????????????????????????????????????????????????" + code + ",???????????????????????????????????????";
        mailMessage.setText(context);
        // ????????????
        javaMailSender.send(mailMessage);
        //??????????????????????????????redis???
        redisCache.setCacheObject(email,code,5,TimeUnit.MINUTES);
        return R.okResult();
    }

    /**
     * ????????????
     *
     * @param dto
     * @return
     */
    @Override
    public R retrievePassword(UserRetrieveDto dto) {
        if (dto != null && dto.getEmail() != null && dto.getCode() != null && dto.getPassword() != null){
            String code = redisCache.getCacheObject(dto.getEmail());
            if (!dto.getCode().equals(code)){
                return R.errorResult(HttpCodeEnum.VERIFICATION_CODE_ERROR);
            }
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getEmail,dto.getEmail());
            User user = userMapper.selectOne(queryWrapper);
            if (user == null){
                return R.errorResult(HttpCodeEnum.EMAIL_NON_EXISTENCE);
            }
            String password = bCryptPasswordEncoder.encode(dto.getPassword());
            user.setPassword(password);
            userMapper.updateById(user);
            redisCache.deleteObject(dto.getEmail());
            return R.okResult();
        }else {
            return R.errorResult(HttpCodeEnum.MISSING_PARAMETER);
        }
    }
}
