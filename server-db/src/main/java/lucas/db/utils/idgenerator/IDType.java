package lucas.db.utils.idgenerator;

public enum IDType {

    PLAYER_ID(true),
    SESSION_ID(false);


    private boolean save;

    IDType(boolean save) {
        this.save = save;
    }

    public boolean isSave() {
        return save;
    }
}
