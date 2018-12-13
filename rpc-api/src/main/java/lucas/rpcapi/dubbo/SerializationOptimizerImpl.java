package lucas.rpcapi.dubbo;

import com.alibaba.dubbo.common.serialize.support.SerializationOptimizer;
import lucas.rpcapi.serverteam.model.TeamPlayer;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author lushengkao vip8
 * //Kryo 序列化预加载
 * 2018/12/13 19:44
 */
public class SerializationOptimizerImpl implements SerializationOptimizer {

    @Override
    public Collection<Class> getSerializableClasses() {
        List<Class> classes = new LinkedList<Class>();
        classes.add(TeamPlayer.class);
        return classes;
    }
}
