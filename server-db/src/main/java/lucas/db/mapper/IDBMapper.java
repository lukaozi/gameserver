package lucas.db.mapper;

import lucas.db.entity.IEntity;
import org.apache.ibatis.session.RowBounds;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author lushengkao vip8
 * 2018/10/22 15:29
 */
public interface IDBMapper<T extends IEntity> {

    long insertEntity(T entity);

    IEntity getEntity(Serializable id);

    List<T> getEntityList(Serializable key);

    List<T> getEntityList(T entity, RowBounds rowBounds);

    /**
     * 直接查找db，无缓存
     */
    List<T> filterList(Map map);

    /**
     * 直接查找db，无缓存
     */
    List<T> filterList(Map map, RowBounds rowBounds);

    void updateEntityByMap(Map map);

    void deleteEntity(T entity);
}
