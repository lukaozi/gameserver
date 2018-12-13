package lucas.rpcapi.dubbo;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.cluster.loadbalance.AbstractLoadBalance;

import java.util.List;

/**
 * @author lushengkao vip8
 * 逻辑服务器负债均衡策略
 * 2018/12/13 19:18
 */
public class LogicLoadBalance extends AbstractLoadBalance {

    @Override
    protected <T> Invoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation) {
        return null;
    }
}
