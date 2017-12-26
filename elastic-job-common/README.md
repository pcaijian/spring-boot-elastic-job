#ConfigServer

##项目说明
config-client是java端的演示项目。C#要接入config-server要在项目内部导入steeltoe的依赖项

Mapper专用的MyBatis生成器插件,执行命令: mybatis-generator:generate

部署构建命令:clean deploy

打包docker命令: clean package -Dmaven.test.skip=true  docker:build

common为公共依赖

finance_service为金额相关微服务

netbar_service为网吧商城相关微服务

notify_service为短信发送及极光推送微服务

scheduling_service为定时任务微服务

user_service为oss微服务

###Config server备注，启动时注入仓库地址和帐号密码等环境参数
1,读取远程git仓库 \
docker run -itd -p 8888:8888 --name config-server -e SPRING_CLOUD_CONFIG_SERVER_GIT_URI=http://10.0.0.5:8010/gitblit/r/config-repo.git \
        -e SPRING_CLOUD_CONFIG_SERVER_GIT_USERNAME=zhouzhihua \
        -e SPRING_CLOUD_CONFIG_SERVER_GIT_PASSWORD=123456 \
       10.0.0.18/library/config-server:1.0

2,读取本地git仓库 \
docker run -it -p 8888:8888 \
      -v /path/to/config/files/dir:/config \
      -e SPRING_CLOUD_CONFIG_SERVER_GIT_URI=file:/config/my-local-git-repo \
      10.0.0.18/library/config-server:latest
      
3,读取本地文件 \
docker run -it -p 8888:8888 \
      -v /path/to/config/files/dir:/config \
      -e SPRING_PROFILES_ACTIVE=native \
      10.0.0.18/library/config-server:latest
      
4,Valt加密（未试验）
docker run -it -p 8888:8888 \
      -e SPRING_PROFILES_ACTIVE=vault \
      10.0.0.18/library/config-server:latest