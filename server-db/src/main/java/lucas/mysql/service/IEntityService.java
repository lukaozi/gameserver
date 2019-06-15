package lucas.mysql.service;

import lucas.mysql.entity.IEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author lushengkao vip8
 * 数据服务接口
 * 2018/10/22 16:04
 */
public interface IEntityService<T extends IEntity> {

    /**
     * 插入实体
     */
    long insertEntity(T entity);

    /**
     * 查询实体
     */
    IEntity getEntity(Serializable id);

    /**
     * 查询实体列表
     */
    List<T> getEntityList(Serializable key);

    /**
     * 更新实体
     */
    void updateEntity(T entity);

    /**
     * 删除实体
     */
    void deleteEntity(T entity);

    /**
     * 批量插入实体列表
     */
    List<Long> insertEntityBatch(List<T> entityList);

    /**
     * 批量更新实体列表
     */
    void updateEntityBatch(List<T> entityList);

    /**
     * 批量删除实体列表
     */
    void deleteEntityBatch(List<T> entityList);

    /**
     * 获取sharding后的结果
     */
    long getShardingId(T entity);
}
