package lucas.db.service.proxy;

import lucas.common.util.FastJsonUtils;
import lucas.db.enums.OperationEnum;

import java.util.Map;

/**
 * @author lushengkao vip8
 * 异步实体的封装
 * 2018/12/3 10:50
 */
public class AsyncEntityWrapper {

    //创建时间
    private long createTime;
    //对应操作
    private OperationEnum operationEnum;
    //操作对象
    private Map<String,String> params;

    public String serialize() {
        return FastJsonUtils.toJson(this);
    }

    public void deserialize(String json) {
        AsyncEntityWrapper wrapper = FastJsonUtils.fromJson(json, getClass());
        this.createTime = wrapper.getCreateTime();
        this.operationEnum = wrapper.getOperationEnum();
        this.params = wrapper.getParams();
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public OperationEnum getOperationEnum() {
        return operationEnum;
    }

    public void setOperationEnum(OperationEnum operationEnum) {
        this.operationEnum = operationEnum;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
