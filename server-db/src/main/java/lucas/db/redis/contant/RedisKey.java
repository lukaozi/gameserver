package lucas.db.redis.contant;

/**
 * redis的键名
 */
public enum RedisKey {
    PLAYER("PR#",true),
    ;

    private String key;

    private boolean async;

    public boolean isAsync() {
        return async;
    }

    RedisKey(String key, boolean async) {
        this.key = key;
        this.async = async;
    }

    public String getKey() {
        return key;
    }
}
