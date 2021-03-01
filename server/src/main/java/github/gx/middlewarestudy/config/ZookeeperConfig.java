package github.gx.middlewarestudy.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @program: MiddlewareStudy
 * @description: zookeeper分布式锁配置类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-03-01 20:00
 **/
@Configuration
public class ZookeeperConfig {

    private static final Logger log = LoggerFactory.getLogger(ZookeeperConfig.class);

    // 配置文件处理对象
    Environment environment;

    @Autowired
    public ZookeeperConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public CuratorFramework curatorFramework() {
        // 创建 zookeeper 高度封装的客户端
        // 通过它实现 心脏检测、维护可用服务列表等操作
        CuratorFramework curatorFramework= CuratorFrameworkFactory.builder()
                .connectString(environment.getProperty("zk.host"))
                .namespace(environment.getProperty("zk.namespace"))
                .retryPolicy(new RetryNTimes(5,1000)).build();
        curatorFramework.start();
        // 返回客户端实例
        return curatorFramework;
    }
}
