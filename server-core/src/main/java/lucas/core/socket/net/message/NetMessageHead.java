package lucas.core.socket.net.message;

import lucas.common.GlobalContant;

/**
 * @author lushengkao vip8
 * 消息头
 * 2018/10/11 18:05
 */
public class NetMessageHead {

    private short head;
    //版本
    private byte version;

    private int length;
    //对应请求的序号
    private int serial;
    //对应命令
    private int command;

    public NetMessageHead() {
        head = GlobalContant.MAGIC_CODE;
    }

    public short getHead() {
        return head;
    }

    public void setHead(short head) {
        this.head = head;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }
}
