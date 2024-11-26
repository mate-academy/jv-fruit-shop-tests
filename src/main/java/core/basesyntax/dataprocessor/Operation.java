package core.basesyntax.dataprocessor;

public enum Operation {
    BALANCE("b"),
    SUPPLY("s"),
    PURCHASE("p"),
    RETURN("r"),
    UNKNOWN("");

    private final String code;

    Operation(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Operation fromCode(String code) {
        if (code == null) {
            return UNKNOWN;
        }
        for (Operation operation : values()) {
            if (operation.code.equals(code)) {
                return operation;
            }
        }
        return UNKNOWN;
    }
}
