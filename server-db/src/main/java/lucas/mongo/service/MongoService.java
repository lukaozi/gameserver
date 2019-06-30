package lucas.mongo.service;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.io.File;

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

    public static void main(String[] args) {
        File file = new File("C:\\Users\\lucas\\Desktop\\data\\极客时间\\深入拆解java虚拟机2");
        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (File file1 : files) {
            if (file1.isFile()) {
                String name = file1.getName();
                String newName = name.substring(7);
                file1.renameTo(new File("C:\\Users\\lucas\\Desktop\\data\\极客时间\\深入拆解java虚拟机\\" + newName));
            }
        }
    }


}
