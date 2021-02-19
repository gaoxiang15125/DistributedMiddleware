package github.gx.middlewarestudy;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: MiddlewareStudy
 * @description: Spring boot 项目启动类
 * @author: gaoxiang
 * @email: 630268696@qq.com
 * @create: 2021-02-19 10:22
 **/
@SpringBootApplication
@MapperScan("github.gx.middlewarestudy.server.mapper")
public class MainApplication {
    static Logger logger = LoggerFactory.getLogger(MainApplication.class);
    public static void main(String[] args) {
        logger.info(" Spring boot 启动了，看到了嘛");
        SpringApplication.run(MainApplication.class, args);
    }
}
