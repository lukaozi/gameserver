package lucas.serverteam;

import lucas.common.SpringStarter;

/**
 * @author lushengkao vip8
 * 2018/12/13 20:38
 */
public class Start {

    public static void main(String[] args) throws InterruptedException {
        SpringStarter starter = new SpringStarter("applicationContext.xml");
        starter.start();
    }
}
