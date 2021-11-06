package com.atguigu.eduUcenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.eduUcenter.entity.UcenterMember;
import com.atguigu.eduUcenter.entity.vo.RegisterVo;
import com.atguigu.eduUcenter.mapper.UcenterMemberMapper;
import com.atguigu.eduUcenter.service.UcenterMemberService;
import com.atguigu.servicebase.config.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-11-03
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public String login(UcenterMember ucenterMember) {
        //获取手机号和密码
        String mobile = ucenterMember.getMobile();
        String password = ucenterMember.getPassword();
        //判断输入的手机号和密码是否为空
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(mobile)){
            throw new GuliException(20001,"手机号或密码为空");
        }

        //判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember member = baseMapper.selectOne(wrapper);
        if (member == null){
            throw new GuliException(20001,"手机号不存在");
        }

        //判断密码是否正确
        // MD5加密是不可逆性的，不能解密，只能加密
        //将获取到的密码经过MD5加密与数据库比较
        if (!MD5.encrypt(password).equals(member.getPassword())){
            throw new GuliException(20001,"密码不正确");
        }

        //判断用户是否禁用
        if (member.getIsDisabled()){
            throw new GuliException(20001,"用户被禁用");
        }

        //生成jwtToken
        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());

        return token;
    }

    @Override
    public void register(RegisterVo registerVo) {
        //获取前端传来的数据
        String nickname = registerVo.getNickname(); //昵称
        String code = registerVo.getCode(); //验证码
        String mobile = registerVo.getMobile(); //手机号
        String password = registerVo.getPassword(); //密码
        //非空判断
        if (StringUtils.isEmpty(nickname)
                ||StringUtils.isEmpty(code)
                ||StringUtils.isEmpty(mobile)
                ||StringUtils.isEmpty(password)){
            throw new GuliException(20001,"传来的数据有空值，注册失败");
        }
        //判断验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!redisCode.equals(code)){
            throw new GuliException(20001, "验证码不一致");
        }
        //手机号不能相同
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count>0){
            throw new GuliException(20001, "手机号已存在");
        }
        UcenterMember member=new UcenterMember();
        member.setPassword(MD5.encrypt(password));//密码加密
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setIsDisabled(false);//用户不禁用
        member.setAvatar("https://online-teach-file.oss-cn-beijing.aliyuncs.com/teacher/2019/10/30/65423f14-49a9-4092-baf5-6d0ef9686a85.png");
        baseMapper.insert(member);
    }

    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }
}
