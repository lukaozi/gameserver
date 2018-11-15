package lucas.db.service;

import lucas.db.annnotation.DbMapper;
import lucas.db.annnotation.Operation;
import lucas.db.entity.AbstractEntity;
import lucas.db.entity.IEntity;
import lucas.db.enums.OperationEnum;
import lucas.db.mapper.IDBMapper;
import lucas.db.service.proxy.EntityProxy;
import org.apache.commons.collections.MapUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lushengkao vip8
 * 2018/11/15 15:18
 */
@Service
public class EntityService<T extends AbstractEntity> implements IEntityService<T> {

    private SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Override
    @Operation(operation = OperationEnum.insert)
    public long insertEntity(T entity) {
        IDBMapper<T> mapper = getEntityMapper(entity);
        return mapper.insertEntity(entity);
    }

    @Override
    @Operation(operation = OperationEnum.query)
    public IEntity getEntity(Serializable id, Class<T> entityClazz) {
        IDBMapper<T> mapper = getEntityMapper(entityClazz);
        return mapper.getEntity(id);
    }

    @Override
    @Operation(operation = OperationEnum.queryList)
    public List<T> getEntityList(Serializable key, Class<T> entityClazz) {
        return getEntityMapper(entityClazz).getEntityList(key);
    }

    @Override
    @Operation(operation = OperationEnum.update)
    @SuppressWarnings("unchecked")
    public void updateEntity(T entity) {
        EntityProxy proxy = entity.getProxy();
        if (proxy == null) {
            return;
        }
        Map changeParamMap = proxy.getChangeParamMap();
        if (MapUtils.isEmpty(changeParamMap)) {
            return;
        }
        Map data = new HashMap<>(changeParamMap);
        IDBMapper<T> mapper = getEntityMapper(entity);
        mapper.updateEntityByMap(data);
    }

    @Override
    @Operation(operation = OperationEnum.delete)
    public void deleteEntity(T entity) {
        IDBMapper<T> mapper = getEntityMapper(entity);
        mapper.deleteEntity(entity);
    }

    @Override
    @Operation(operation = OperationEnum.insertBatch)
    public List<Long> insertEntityBatch(List<T> entityList) {
        //TODO
        return null;
    }

    @Override
    @Operation(operation = OperationEnum.updateBatch)
    public void updateEntityBatch(List<T> entityList) {
        //TODO
    }

    @Override
    @Operation(operation = OperationEnum.deleteBatch)
    public void deleteEntityBatch(List<T> entityList) {
        //TODO
    }

    @Override
    public long getShardingId(T entity) {
        return 0;
    }

    @SuppressWarnings("unchecked")
    private IDBMapper<T> getEntityMapper(T entity) {
        Class<? extends IEntity> entityClass = entity.getClass();
        DbMapper dbMapper = entityClass.getAnnotation(DbMapper.class);
        Class<? extends IDBMapper> mapperClazz = dbMapper.mapper();
        return sqlSessionTemplate.getMapper(mapperClazz);
    }

    @SuppressWarnings("unchecked")
    private IDBMapper<T> getEntityMapper(Class<? extends T> entityClazz) {
        DbMapper dbMapper = entityClazz.getAnnotation(DbMapper.class);
        Class<? extends IDBMapper> mapperClazz = dbMapper.mapper();
        return sqlSessionTemplate.getMapper(mapperClazz);
    }
}
