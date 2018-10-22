package lucas.db.service;

import lucas.db.entity.IEntity;

import java.util.List;

/**
 * @author lushengkao vip8
 * 数据服务接口
 * 2018/10/22 16:04
 */
public interface IEntityService<T extends IEntity> {

    /**
     * 插入实体
     * @param entity
     * @return
     */
    long insertEntity(T entity);

    /**
     * 查询实体
     * @param entity
     * @return
     */
    IEntity getEntity(T entity);

    /**
     * 查询实体列表
     * @param entity 需要实现代理，才能拼写sql map
     * @return
     */
    List<T> getEntityList(T entity);

    /**
     * 更新实体
     * @param entity 需要实现代理
     */
    void updateEntity(T entity);

    /**
     * 删除实体
     * @param entity
     */
    void deleteEntity(T entity);

    /**
     * 批量插入实体列表
     * @param entityList
     * @return
     */
    List<Long> insertEntityBatch(List<T> entityList);

    /**
     * 批量更新实体列表
     * @param entityList
     */
    void updateEntityBatch(List<T> entityList);

    /**
     * 批量删除实体列表
     * @param entityList
     */
    void deleteEntityBatch(List<T> entityList);

    /**
     * 获取sharding后的结果
     * @param entity
     * @return
     */
    long getShardingId(T entity);
}
