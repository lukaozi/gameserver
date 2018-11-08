package lucas.net;

import lucas.net.packet.AbstractPacket;
import lucas.net.packet.Req_Login;

import java.util.concurrent.ConcurrentHashMap;


/**
 * @author lushengkao vip8
 * 消息类型
 * 2018/11/7 16:22
 */
public enum  PacketType {

    //登陆
    LOGIN(1, Req_Login.class,true);

    PacketType(int command, Class<? extends AbstractPacket> clazz, boolean req) {
        this.command = command;
        this.clazz = clazz;
        this.req = req;
        PacketType.getId2Class().put(command,clazz);
        PacketType.getClass2id().put(clazz,command);
    }

    private int command;

    private Class clazz;

    private boolean req;

    public static int getCommand(Class<? extends AbstractPacket> aClass) {
        return class2id.get(aClass);
    }

    public static Class<? extends AbstractPacket> getClazz(int commond) {
        return id2Class.get(commond);
    }

    public boolean isReq() {
        return req;
    }

    public void setReq(boolean req) {
        this.req = req;
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public static final ConcurrentHashMap<Integer,Class<? extends AbstractPacket>> id2Class = new ConcurrentHashMap<>();

    public static final ConcurrentHashMap<Class<? extends AbstractPacket>,Integer> class2id = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<Integer, Class<? extends AbstractPacket>> getId2Class() {
        return id2Class;
    }

    public static ConcurrentHashMap<Class<? extends AbstractPacket>, Integer> getClass2id() {
        return class2id;
    }
}
