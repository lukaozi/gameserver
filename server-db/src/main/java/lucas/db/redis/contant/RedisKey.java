package lucas.db.redis.contant;

/**
 * redis的键名
 */
public enum RedisKey {
    PLAYER("PR#"),
    ;

    private String key;

    RedisKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
