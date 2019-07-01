package lucas.rpcapi.dubbo;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.cluster.loadbalance.AbstractLoadBalance;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.cluster.LoadBalance;

import java.util.List;

/**
 * @author lushengkao vip8
 * 逻辑服务器负债均衡策略
 * 2018/12/13 19:18
 */
public class LogicLoadBalance implements LoadBalance {

    @Override
    public <T> org.apache.dubbo.rpc.Invoker<T> select(List<org.apache.dubbo.rpc.Invoker<T>> invokers, org.apache.dubbo.common.URL url, org.apache.dubbo.rpc.Invocation invocation) throws RpcException {
        return null;
    }
}
