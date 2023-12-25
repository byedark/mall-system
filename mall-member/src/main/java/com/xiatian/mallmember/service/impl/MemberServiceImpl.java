package com.xiatian.mallmember.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiatian.mallmember.entity.Member;
import com.xiatian.mallmember.entity.MemberLevel;
import com.xiatian.mallmember.exception.ExistException;
import com.xiatian.mallmember.service.MemberLevelService;
import com.xiatian.mallmember.service.MemberService;
import com.xiatian.mallmember.mapper.MemberMapper;
import com.xiatian.mallmember.vo.SocialUser;
import com.xiatian.mallmember.vo.UserLoginVo;
import com.xiatian.mallmember.vo.UserRegistVo;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;

/**
* @author XT189
* @description 针对表【ums_member(会员)】的数据库操作Service实现
* @createDate 2023-11-08 12:55:42
*/
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member>
    implements MemberService{

    @Resource
    MemberLevelService memberLevelService;
    @Override
    public void register(UserRegistVo vo) throws ExistException{
        //手机和昵称必须是唯一的
        boolean phoneUnique= this.checkPhoneUnique(vo.getPhone());
        boolean userNameUnique = this.checkUserNameUnique(vo.getUserName());
        //采用异常机制
        if(!phoneUnique || !userNameUnique){
            //感知异常
            throw new ExistException();
        }
        MemberMapper memberMapper = this.baseMapper;
        Member member = new Member();
        //设置默认的会员等级
        MemberLevel memberLevel = memberLevelService.getDefaultLevel();
        member.setLevelId(memberLevel.getId());

        //设置手机号
        member.setMobile(vo.getPhone());
        //昵称和用户名都设置一下
        member.setNickname(vo.getUserName());
        member.setUsername(vo.getUserName());
        //密码采用盐值加密技术，自带的库
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode(vo.getPassword());
        member.setPassword(encode);
        memberMapper.insert(member);
    }

    @Override
    public boolean checkPhoneUnique(String phone) {
        MemberMapper memberMapper = this.baseMapper;
        Integer mobile = memberMapper.selectCount(new QueryWrapper<Member>().eq("mobile",phone));
        return mobile == 0;
    }

    @Override
    public boolean checkUserNameUnique(String userName) {
        MemberMapper memberMapper = this.baseMapper;
        Integer count = memberMapper.selectCount(new QueryWrapper<Member>().eq("username",userName));
        return count == 0;
    }

    @Override
    public Member login(UserLoginVo vo) {
        String loginacct = vo.getLoginacct();
        String password = vo.getPassword();
        MemberMapper memberMapper = this.baseMapper;
        Member member = memberMapper.selectOne(new QueryWrapper<Member>().eq("username",loginacct).or().
                eq("mobile",loginacct));
        if(member == null){
            return null;
        }else{
            String passwordDb = member.getPassword();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            boolean match = passwordEncoder.matches(password,passwordDb);
            if(match){
                return member;
            }else {
                return null;
            }
        }
    }

    @Override
    public Member authLogin(SocialUser socialUser) {
        // 微博的uid
        String uid = socialUser.getUid();
        // 1.判断社交用户登录过系统
        MemberMapper dao = this.baseMapper;
        Member entity = dao.selectOne(new QueryWrapper<Member>().eq("social_uid", uid));
        Member memberEntity = new Member();
        if(entity != null){
            // 说明这个用户注册过, 修改它的资料
            memberEntity.setId(entity.getId());
            memberEntity.setAccessToken(socialUser.getAccess_token());
            memberEntity.setExpiresIn(socialUser.getExpires_in());
            // 更新
            dao.updateById(memberEntity);
            entity.setAccessToken(socialUser.getAccess_token());
            entity.setExpiresIn(socialUser.getExpires_in());
            entity.setPassword(null);
            return entity;
        }else{
            // 2. 没有查到当前社交用户对应的记录 我们就需要注册一个
            try {
                CloseableHttpClient httpclient = HttpClients.createDefault();
                String url = "https://api.weibo.com/2/users/show.json?"+
                        "access_token="+socialUser.getAccess_token()+
                        "&&uid="+socialUser.getUid();
                HttpGet httpGet = new HttpGet(url);
                //TODO:加入需要的参数
                CloseableHttpResponse response = httpclient.execute(httpGet);
                // 3. 查询当前社交用户账号信息(昵称、性别等)
                if(response.getStatusLine().getStatusCode() == 200){
                    // 查询成功
                    String json = EntityUtils.toString(response.getEntity());
                    // 这个JSON对象什么样的数据都可以直接获取
                    JSONObject jsonObject = JSON.parseObject(json);
                    memberEntity.setNickname(jsonObject.getString("name"));
                    memberEntity.setUsername(jsonObject.getString("name"));
                    memberEntity.setGender("m".equals(jsonObject.getString("gender"))?1:0);
                    memberEntity.setCity(jsonObject.getString("location"));
                    memberEntity.setJob("自媒体");
                    memberEntity.setEmail(jsonObject.getString("email"));
                }
            } catch (Exception e) {
                log.warn("社交登录时远程调用出错 [尝试修复]");
            }
            memberEntity.setStatus(0);
            memberEntity.setCreateTime(new Date());
            memberEntity.setBirth(new Date());
            memberEntity.setLevelId(1L);
            memberEntity.setSocialUid(socialUser.getUid());
            memberEntity.setAccessToken(socialUser.getAccess_token());
            memberEntity.setExpiresIn(socialUser.getExpires_in());
            // 注册 -- 登录成功
            dao.insert(memberEntity);
            memberEntity.setPassword(null);
            return memberEntity;
        }
    }
//    {
//        "id": 5605937365,
//            "idstr": "5605937365",
//            "class": 1,
//            "screen_name": "汀西氟的我是你",
//            "name": "汀西氟的我是你",
//            "province": "43",
//            "city": "1",
//            "location": "湖南 长沙",
//            "description": "",
//            "url": "",
//            "profile_image_url": "https://tvax1.sinaimg.cn/crop.0.0.996.996.50/0067nWoBly8g1vhjx6rfrj30ro0rojs8.jpg?KID=imgbed,tva&Expires=1702835301&ssig=M4NvNR7ame",
//            "light_ring": false,
//            "cover_image_phone": "https://ww1.sinaimg.cn/crop.0.0.640.640.640/549d0121tw1egm1kjly3jj20hs0hsq4f.jpg",
//            "profile_url": "u/5605937365",
//            "domain": "",
//            "weihao": "",
//            "gender": "m",
//            "followers_count": 0,
//            "followers_count_str": "5",
//            "friends_count": 0,
//            "pagefriends_count": 0,
//            "statuses_count": 0,
//            "video_status_count": 0,
//            "video_play_count": 0,
//            "super_topic_not_syn_count": 0,
//            "favourites_count": 0,
//            "created_at": "Sun May 31 06:09:55 +0800 2015",
//            "following": false,
//            "allow_all_act_msg": false,
//            "geo_enabled": true,
//            "verified": false,
//            "verified_type": -1,
//            "remark": "",
//            "insecurity": {
//        "sexual_content": false
//    },
//        "status": {},
//        "ptype": 0,
//            "allow_all_comment": true,
//            "avatar_large": "https://tvax1.sinaimg.cn/crop.0.0.996.996.180/0067nWoBly8g1vhjx6rfrj30ro0rojs8.jpg?KID=imgbed,tva&Expires=1702835301&ssig=kkgoevguD3",
//            "avatar_hd": "https://tvax1.sinaimg.cn/crop.0.0.996.996.1024/0067nWoBly8g1vhjx6rfrj30ro0rojs8.jpg?KID=imgbed,tva&Expires=1702835301&ssig=kXSryupaX9",
//            "verified_reason": "",
//            "verified_trade": "",
//            "verified_reason_url": "",
//            "verified_source": "",
//            "verified_source_url": "",
//            "follow_me": false,
//            "like": false,
//            "like_me": false,
//            "online_status": 0,
//            "bi_followers_count": 0,
//            "lang": "zh-cn",
//            "star": 0,
//            "mbtype": 0,
//            "mbrank": 0,
//            "svip": 0,
//            "vvip": 0,
//            "mb_expire_time": 0,
//            "block_word": 0,
//            "block_app": 0,
//            "chaohua_ability": 0,
//            "brand_ability": 0,
//            "nft_ability": 0,
//            "vplus_ability": 0,
//            "wenda_ability": 0,
//            "live_ability": 0,
//            "gongyi_ability": 0,
//            "paycolumn_ability": 0,
//            "newbrand_ability": 0,
//            "ecommerce_ability": 0,
//            "hardfan_ability": 0,
//            "wbcolumn_ability": 0,
//            "interaction_user": 0,
//            "audio_ability": 0,
//            "credit_score": 80,
//            "user_ability": 0,
//            "urank": 4,
//            "story_read_state": -1,
//            "vclub_member": 0,
//            "is_teenager": 0,
//            "is_guardian": 0,
//            "is_teenager_list": 0,
//            "pc_new": 0,
//            "special_follow": false,
//            "planet_video": 0,
//            "video_mark": 0,
//            "live_status": 0,
//            "user_ability_extend": 0,
//            "status_total_counter": {
//        "total_cnt": 2,
//                "repost_cnt": 0,
//                "comment_cnt": 1,
//                "like_cnt": 1,
//                "comment_like_cnt": 0
//    },
//        "video_total_counter": {
//        "play_cnt": -1
//    },
//        "brand_account": 0,
//            "hongbaofei": 0,
//            "green_mode": 0,
//            "urisk": 0,
//            "unfollowing_recom_switch": 1,
//            "block": 0,
//            "block_me": 0,
//            "avatar_type": 0,
//            "is_big": 0,
//            "auth_status": 0,
//            "auth_realname": null,
//            "auth_career": null,
//            "auth_career_name": null,
//            "show_auth": 0
//    }
}




