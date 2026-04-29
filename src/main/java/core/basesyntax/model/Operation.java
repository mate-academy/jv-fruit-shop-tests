package core.basesyntax.model;

public enum Operation {
    BALANCE("b"),
    SUPPLY("s"),
    PURCHASE("p"),
    RETURN("r");

    private final String code;
    Operation(String value) {
        this.code = value;
    }

    public static Operation fromCode(String code) {
        if (code == null) {
            throw new IllegalArgumentException("Operation code is null");
        }
        for (Operation op : values()) {
            if (op.getCode().equals(code)) {
                return op;
            }
        }
        throw new IllegalArgumentException("Unknown operation code: " + code);
    }

    public String getCode() {
        return code;
    }
}

