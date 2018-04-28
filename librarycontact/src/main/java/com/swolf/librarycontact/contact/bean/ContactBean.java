package com.swolf.librarycontact.contact.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 联系人
 * Created by LiuYi-15973602714 on 2017-01-01
 */
@SuppressWarnings("serial")
public class ContactBean extends ContactIdBean implements Serializable {
    /*********************** 查询 *****************************************/
    public RingBean ring;// 铃声
    public PhotoBean photo;// 头像
    public NameBean name;// 名字
    public BirthdayBean birthday;// 生日
    public NicknameBean nickname;// 昵称
    public ArrayList<GroupMembershipBean> groupMembershipList = new ArrayList<GroupMembershipBean>(0);// 分组
    public ArrayList<PhoneBean> phoneList = new ArrayList<PhoneBean>(0);// 电话
    public ArrayList<EmailBean> emailList = new ArrayList<EmailBean>(0);// 邮件
    public ArrayList<PostalBean> postalList = new ArrayList<PostalBean>(0);// 邮政
    public ArrayList<OrganizationBean> organizationList = new ArrayList<OrganizationBean>(0);// 组织
    public ArrayList<ImBean> imList = new ArrayList<ImBean>(0);// im
    public ArrayList<WebsiteBean> websiteList = new ArrayList<WebsiteBean>(0);// 网站
    /*********************** 删除 *****************************************/
    public RingBean delRing;// 铃声
    public PhotoBean delPhoto;// 头像
    public NameBean delName;// 名字
    public BirthdayBean delBirthday;// 生日
    public NicknameBean delNickname;// 昵称
    public ArrayList<GroupMembershipBean> delGroupMembershipList = new ArrayList<GroupMembershipBean>(0);// 分组
    public ArrayList<PhoneBean> delPhoneList = new ArrayList<PhoneBean>(0);// 电话
    public ArrayList<EmailBean> delEmailList = new ArrayList<EmailBean>(0);// 邮件
    public ArrayList<PostalBean> delPostalList = new ArrayList<PostalBean>(0);// 邮政
    public ArrayList<OrganizationBean> delOrganizationList = new ArrayList<OrganizationBean>(0);// 组织
    public ArrayList<ImBean> delImList = new ArrayList<ImBean>(0);// im
    public ArrayList<WebsiteBean> delWebsiteList = new ArrayList<WebsiteBean>(0);// 网站
}

// /:~