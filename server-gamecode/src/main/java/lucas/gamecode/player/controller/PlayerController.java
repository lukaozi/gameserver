package lucas.gamecode.player.controller;

import lucas.gamecode.anno.GameController;
import lucas.gamecode.anno.GameRequest;
import lucas.net.packet.Req_Login;
import org.springframework.stereotype.Controller;

/**
 * @author lushengkao vip8
 * 2018/11/8 20:25
 */

@Controller
@GameController
public class PlayerController {

    @GameRequest
    public void Hello(Req_Login req) {
        System.out.println("Hello");
    }
}
