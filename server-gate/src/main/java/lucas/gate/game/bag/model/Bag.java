package lucas.gate.game.bag.model;


import java.util.HashMap;

/**
 * @author lushengkao vip8
 * 2018/12/12 16:42
 */
public class Bag {

    private HashMap<Integer,Integer> items = new HashMap<>();

    public HashMap<Integer, Integer> getItems() {
        return items;
    }

    public void setItems(HashMap<Integer, Integer> items) {
        this.items = items;
    }
}
