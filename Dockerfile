#使用基础镜像java8
FROM hub.c.163.com/library/java:8-jre
#作者信息
MAINTAINER xiaozhu zyw@zhuyw.top
#时区设置
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone && dpkg-reconfigure -f noninteractive tzdata
# 声明需要暴露的端口
EXPOSE 8080
#指定临时目录/tmp Spring Boot 使用的内嵌 Tomcat 容器默认使用/tmp作为工作目录
VOLUME /tmp
#定义了一个log的持久化存储
VOLUME /log
#将jar包添加到容器并更名为app.jar
COPY yblaas_open_v1.1.jar app.jar
# 更新jar包时间属性
RUN bash -c 'touch /app.jar'
# 运行jar包
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]