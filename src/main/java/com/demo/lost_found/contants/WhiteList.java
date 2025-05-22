package com.demo.lost_found.contants;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 白名单，用于存放无需鉴权的接口
 */
@Data
public class WhiteList {

    public static final List<String> WHITE_LIST = new ArrayList<>();

    static {
        /**
         * 登录注册接口
         */
        WHITE_LIST.add("/user/loginByUsername");
        WHITE_LIST.add("/user/register");
        WHITE_LIST.add("/user/loginByPhone");
        WHITE_LIST.add("/user/sendPhoneCode");
        /**
         * 图片上传接口
         */
        WHITE_LIST.add("/file/upload");
        WHITE_LIST.add("/file/uploadCarouselImage");
        WHITE_LIST.add("/file/uploadPromotionalVideos");
        WHITE_LIST.add("/file/delete");
        WHITE_LIST.add("/file/uploadLostAndFound");
        WHITE_LIST.add("/file/uploadLostAndReport");
        WHITE_LIST.add("/file/uploadAnnouncementImage");
        WHITE_LIST.add("/file/uploadRecognizeImage");
        /**
         * 轮播图接口
         */
        WHITE_LIST.add("/admin/carouselImages/getList");
        WHITE_LIST.add("/admin/carouselImages/getListFront");
        WHITE_LIST.add("/admin/carouselImages/increaseClicks");
        /**
         * 宣传视频接口
         */
        WHITE_LIST.add("/admin/promotionalVideos/getList");
        /**
         * 物品类别接口
         */
        WHITE_LIST.add("/admin/category/getList");
        /**
         * 公告管理接口
         */
        WHITE_LIST.add("/admin/announcement/getList");
        WHITE_LIST.add("/admin/announcement/getById");
        /**
         * 修改密码接口
         */
        WHITE_LIST.add("/admin/user/changePasswordForm");
        /**
         * 退出登录接口
         */
        WHITE_LIST.add("/admin/user/logout");
    }
}
