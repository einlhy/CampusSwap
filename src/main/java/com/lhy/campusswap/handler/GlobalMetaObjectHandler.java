package com.lhy.campusswap.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * ClassName: GlobalMetaObjectHandler
 * Package: com.lhy.campusswap.handler
 * Description:
 *
 * @Author LHY
 * @Create 2025/12/6 11:53
 */
@Component
public class GlobalMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        // 1. 设置默认角色（字段为空时）
        if (metaObject.getValue("roles") == null) {
            this.strictInsertFill(metaObject,"roles", List.class, Collections.singletonList("user_normal"));
        }
        if (metaObject.getValue("permissions") == null) {
            List<String> defaultPermissions = List.of("goods:create","goods:update_own","goods:delete_own",
                    "order:refund","message:send","favorite:add");
            this.strictInsertFill(metaObject,"permissions", List.class, defaultPermissions);
        }

        // 2. 顺带统一设置其他常用字段（主流项目的常见做法）
        LocalDateTime now = LocalDateTime.now();
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, now);
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, now);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 统一设置更新时间
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }
}
