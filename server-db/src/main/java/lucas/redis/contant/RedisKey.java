package lucas.redis.contant;

/**
 * redis的键名
 */
public enum RedisKey {
    PLAYER(1, "PR#", true),
    ;

    private int no;

    private String key;

    //是否异步存贮
    private boolean async;

    RedisKey(int no, String key, boolean async) {
        this.no = no;
        this.key = key;
        this.async = async;
    }

    public int getNo() {
        return no;
    }

    public String getKey() {
        return key;
    }

    public boolean isAsync() {
        return async;
    }
}
