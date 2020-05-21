# yblaas_open

易班请假与考勤系统开源版（yiban leave and attendance system open）

# 简介

易班请假与考勤系统是高校的一个请假与考勤的系统，提高纸质化请假与考勤的效率；鉴于易班目前已经入驻许多高校，而且具有实名制，所以接入易班单点登陆；参考自己的毕设重新改造的开源版本，去掉了QQ提醒和手机验证码验证啥的，增加自定义邮件。

# 软件手册
部署需要安装mysql、jdk、Redis  
文档地址：[中文文档](https://doc.benzhu.xyz/web/#/2?page_id=11 "中文文档")

# 特点

1. 系统请假三级审批，辅导员->学院领导->学工处，管理员可以在后台自定义达到多少天进行二级和三级审批。
2. 考勤通过易班定位的经纬度进行考勤，管理员可以在后台自定义考勤的精度，即范围多少米。
3. 如果配置的易班轻应用具备校级权限，学生登陆后可以直接获取班级和实名信息。
4. 管理员可以在后台配置邮件服务器账号。
5. 考勤与手机唯一标识绑定，单次考勤单手机只能考勤一次；老师可以修改考勤记录。
5. 接入易班，实现易班单点登陆。

# 演示图
![管理员登录界面](https://images.weserv.nl/?url=https://img03.sogoucdn.com/app/a/100520146/7dfc63bb5ea596aba505512046557e48)
![管理员首页](https://images.weserv.nl/?url=https://img03.sogoucdn.com/app/a/100520146/65706fac22a15e31642b3194fdf3b4c0)
![学生首页](https://images.weserv.nl/?url=https://img02.sogoucdn.com/app/a/100520146/9fdbc6a092476bc979463f67c30d6cc7)
![学生考勤](https://images.weserv.nl/?url=https://img03.sogoucdn.com/app/a/100520146/4aa8b606346c452785d308f54d773bb5)
![老师首页](https://images.weserv.nl/?url=https://img03.sogoucdn.com/app/a/100520146/cb8d5ad42e68eae73cf8e0c8dfd5a5f7)
![老师假条](https://images.weserv.nl/?url=https://img04.sogoucdn.com/app/a/100520146/ce00bf37693f1810d54371d0f586ab92)
![老师考勤](https://images.weserv.nl/?url=https://img02.sogoucdn.com/app/a/100520146/933f8b48f1cfce9aab817a9f4acb7feb)
![邮件测试](https://images.weserv.nl/?url=https://img02.sogoucdn.com/app/a/100520146/9e7cf1f34ffc3b30b53685dd92cdabfb)

# 前端框架

1. 学生端：light7、JQuery WeUI
2. 老师端：H-ui的H-ui.adminV3.1
3. 管理员端：H-ui的H-ui.adminV3.1

# 系统架构
1. Spring Boot 2.2.4
2. SSM
3. Maven
4. MySQL 5.7
5. JDK1.8
6. Apache Shiro
7. Druid
8. redis

# 致谢
学生作品，有许多不足之处希望多多包涵；最后还是感谢各大开源软件，学到许多，也成长许多。