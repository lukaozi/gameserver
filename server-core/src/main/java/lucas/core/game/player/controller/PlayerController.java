package lucas.core.game.player.controller;

import lucas.core.game.anno.GameController;
import lucas.core.game.anno.GameRequest;
import lucas.core.packet.Req_Login;
import lucas.core.socket.net.session.GameSession;
import org.springframework.stereotype.Controller;

/**
 * @author lushengkao vip8
 * 2018/11/8 20:25
 */

@Controller
@GameController
public class PlayerController {

    @GameRequest
    public void Hello(GameSession session,Req_Login req) {
        System.out.println("Hello " + session.getPlayer().getName());
    }
}
