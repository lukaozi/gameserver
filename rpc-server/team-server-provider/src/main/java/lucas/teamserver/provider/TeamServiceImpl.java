package lucas.teamserver.provider;

import lucas.teamserver.RpcTeamService;
import org.springframework.stereotype.Service;

/**
 * @author lushengkao vip8
 * 2018/12/7 17:10
 */
@Service
public class TeamServiceImpl implements RpcTeamService {

    public void hello() {
        System.out.println("Hello World");
    }
}
