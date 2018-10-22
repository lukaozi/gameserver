package lucas.db.entity;

import java.io.Serializable;

public interface IEntity<V extends Serializable> extends Serializable {

    V getId();

    void setId(V id);
}
