package lucas.net;

import lucas.net.packet.Req_Login;

/**
 * @author lushengkao vip8
 * 消息类型
 * 2018/11/7 16:22
 */
public enum  PacketType {
    LOGIN(1, Req_Login.class,true);

    PacketType(int serialId, Class clazz,boolean req) {
        this.serialId = serialId;
        this.clazz = clazz;
        this.req = req;
    }

    private int serialId;

    private Class clazz;

    private boolean req;

    public boolean isReq() {
        return req;
    }

    public void setReq(boolean req) {
        this.req = req;
    }

    public int getSerialId() {
        return serialId;
    }

    public void setSerialId(int serialId) {
        this.serialId = serialId;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
