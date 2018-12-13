package lucas.serverteam;

import lucas.common.DubboServerStarter;

/**
 * @author lushengkao vip8
 * 2018/12/13 20:38
 */
public class Start {

    public static void main(String[] args) throws InterruptedException {
        DubboServerStarter starter = new DubboServerStarter("applicationContext.xml");
        starter.start();
    }
}
