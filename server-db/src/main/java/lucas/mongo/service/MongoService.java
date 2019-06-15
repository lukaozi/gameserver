package lucas.mongo.service;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * @author lushengkao vip8
 * <p>
 * mongo 数据库操作
 * 2019/6/15 15:10
 */
public class MongoService {

    private MongoTemplate template;

    public void insert(Object entity) {
        template.insert(entity);
    }

    public void update(Object entity) {
        template.save(entity);
    }

    public void findOne(Long id, Class<?> clazz) {
        Query query = new Query(Criteria.where("id").is(id));
        template.findOne(query, clazz);
    }

    public void removeOne(Long id, Class<?> clazz) {
        Query query = new Query(Criteria.where("id").is(id));
        template.remove(query, clazz);
    }


}
