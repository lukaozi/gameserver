package lucas.common.promise;

/**
 * @author lushengkao vip8
 */
public class CauseHolder {

    public Throwable cause;

    public CauseHolder(Throwable cause) {
        this.cause = cause;
    }

    public CauseHolder() {

    }
}
