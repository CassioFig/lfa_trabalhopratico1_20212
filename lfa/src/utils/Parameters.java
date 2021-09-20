package utils;

public enum Parameters {
    STATES("#states"),
    INITIAL("#initial"),
    ACCEPTING("#accepting"),
    ALPHABET("#alphabet"),
    TRANSITIONS("#transitions"),
    END_OF_FILE(" ");

    public String value;

    Parameters(String value) {
        this.value = value;
    }

    public Parameters next() {
        return switch (this) {
            case STATES -> INITIAL;
            case INITIAL -> ACCEPTING;
            case ACCEPTING -> ALPHABET;
            case ALPHABET -> TRANSITIONS;
            case TRANSITIONS -> END_OF_FILE;
            default -> null;
        };
    }
}
