package com.elastic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

/**
 * 
 * 数据源的配置.扫描mapper包
 * @author 009
 *
 */
@Configuration
public class MapperConfig {

  @Bean
  public MapperScannerConfigurer mapperScannerConfigurer() {
    MapperScannerConfigurer configure = new MapperScannerConfigurer();
    // 扫描mapper的包
    configure.setBasePackage("com.netbar.mapper");
    configure.setMarkerInterface(BaseMapper.class);
    return configure;
  }

}
