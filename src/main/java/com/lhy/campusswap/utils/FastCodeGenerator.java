package com.lhy.campusswap.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * ClassName: FastGenerator
 * Package: com.lhy.campusswap.utils
 * Description:
 *
 * @Author LHY
 * @Create 2025/11/25 23:07
 */
public class FastCodeGenerator {
    public static void main(String[] args) {
        // 数据源配置
        String url = "jdbc:mysql://localhost:3306/campus_swap?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "190118";

        FastAutoGenerator.create(url, username, password)
                // 1. 全局配置
                .globalConfig(builder -> {
                    builder.author("LHY")        // 设置作者
                            .outputDir(System.getProperty("user.dir") + "/src/main/java") // 输出目录
                            .disableOpenDir()          // 禁止打开输出目录
                            .commentDate("2025-11-25"); // 注释日期
                })
                // 2. 包配置
                .packageConfig(builder -> {
                    builder.parent("com.lhy.campusswap") // 父包名
//                            .moduleName("demo")         // 模块名（可选）
                            .entity("entity")
                            .service("service")
                            .serviceImpl("service.impl")
                            .mapper("mapper")
                            .controller("controller");
                })
                // 3. 策略配置
                .strategyConfig(builder -> {
                    builder.addInclude("admin","category","conversation","favorite","goods","message","order","report","system_config","trade_comment") // 设置需要生成的表名
//                            .addTablePrefix("t_", "sys_") // 设置过滤表前缀
                            // Entity 策略配置
                            .entityBuilder()
                            .enableLombok()             // 开启 Lombok
                            .logicDeleteColumnName("is_deleted") // 逻辑删除字段名（按需）
                            .enableTableFieldAnnotation() // 开启字段注解
                            // Controller 策略配置
                            .controllerBuilder()
                            .enableRestStyle()          // 开启生成 @RestController
                            // Service 策略配置
                            .serviceBuilder()
                            .formatServiceFileName("%sService") // 格式化 service 接口文件名称
                            // Mapper 策略配置
                            .mapperBuilder()
                            .enableMapperAnnotation();  // 开启 @Mapper
                })
                // 4. 模板引擎配置（默认使用 Velocity，如无特殊需求可省略）
                .templateEngine(new FreemarkerTemplateEngine())
                .execute(); // 执行生成
    }
}
