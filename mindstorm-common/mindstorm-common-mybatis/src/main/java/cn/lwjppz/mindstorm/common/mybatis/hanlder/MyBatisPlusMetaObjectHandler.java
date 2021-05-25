package cn.lwjppz.mindstorm.common.mybatis.hanlder;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <p>
 * Mybatis Plus 字段自动注入处理器
 * </p>
 *
 * @author : lwj
 * @since : 2021-05-10
 */
@Component
public class MyBatisPlusMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "gmtCreate", Date.class, new Date());
        this.strictUpdateFill(metaObject, "gmtModified", Date.class, new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "gmtModified", Date.class, new Date());
    }

}
