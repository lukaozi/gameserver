package lucas.db.service;

import lucas.db.annnotation.DbMapper;
import lucas.db.annnotation.Operation;
import lucas.db.entity.IEntity;
import lucas.db.enums.OperationEnum;
import lucas.db.mapper.IDBMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * @author lushengkao vip8
 * 2018/11/15 15:18
 */
@Service
public class EntityService<T extends IEntity> implements IEntityService<T> {

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
    public void updateEntity(T entity) {
    }

    @Override
    @Operation(operation = OperationEnum.delete)
    public void deleteEntity(T entity) {

    }

    @Override
    @Operation(operation = OperationEnum.insertBatch)
    public List<Long> insertEntityBatch(List<T> entityList) {
        return null;
    }

    @Override
    @Operation(operation = OperationEnum.updateBatch)
    public void updateEntityBatch(List<T> entityList) {

    }

    @Override
    @Operation(operation = OperationEnum.deleteBatch)
    public void deleteEntityBatch(List<T> entityList) {

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
