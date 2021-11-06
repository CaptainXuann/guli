package com.atguigu.eduUcenter.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.OrderVo.UcenterMemberOrder;
import com.atguigu.commonutils.R;
import com.atguigu.eduUcenter.entity.UcenterMember;
import com.atguigu.eduUcenter.entity.vo.RegisterVo;
import com.atguigu.eduUcenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-11-03
 */
@RestController
@RequestMapping("/eduUcenter/ucenter-member")
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService memberService;

    //登录
    @PostMapping("/login")
    public R login(@RequestBody UcenterMember ucenterMember){
        //返回token，使用jwt生成
        String token = memberService.login(ucenterMember);
        return R.ok().data("token",token);
    }

    //注册
    @PostMapping("/register")
    public R register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }

    //根据token获取用户信息
    @GetMapping("/getUserInfoForJwt")
    public R getUserInfoForJwt(HttpServletRequest request){
        //调用jwt工具类里面的根据request对象，获取头信息，返回用户id
        String id = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库，根据用户id，获取用户信息
        UcenterMember member = memberService.getById(id);

        return R.ok().data("userInfo",member);
    }

    //根据id获取用户信息  评论
    @GetMapping("/getMemberInfoById/{id}")
    public R getMemberInfoById(@PathVariable("id") Long id){
        UcenterMember member = memberService.getById(id);
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", member.getId());
        map.put("nickname", member.getNickname());
        map.put("avatar", member.getAvatar());
        return R.ok().data(map);
    }

    //根据id获取用户信息
    @GetMapping("/getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable("id") Long id){
        UcenterMember member = memberService.getById(id);
        UcenterMemberOrder memberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member, memberOrder);
        return memberOrder;
    }

    //根据日期，获取那天注册人数
    @GetMapping("/countRegister/{day}")
    public R countRegister(@PathVariable String day){
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.likeRight("gmt_create", day);
        int count = memberService.count(wrapper);
        return R.ok().data("count", count);
    }
}

