package com.smart.cityos.datav;

import com.smart.cityos.datav.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * 节点实体共通服务入口
 *
 * @author: zhengkai
 * @date Created in 2018-06-06
 */
@EnableAutoConfiguration
@EnableConfigurationProperties({ApplicationProperties.class})
@ComponentScan({"com.smart.cityos.datav"})
@EnableMongoRepositories(basePackages = {"com.smart.cityos.datav"})
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
