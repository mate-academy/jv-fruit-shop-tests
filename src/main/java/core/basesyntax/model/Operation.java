package core.basesyntax.model;

public enum Operation {
    BALANCE("b"),
    SUPPLY("s"),
    PURCHASE("p"),
    RETURN("r");

    private String code;

    Operation(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Operation valueOfCode(String code) {
        if (code == null || code.equals("")) {
            throw new RuntimeException("Can`t get value of null!");
        }
        for (Operation o : values()) {
            if (o.code.equals(code)) {
                return o;
            }
        }
        return null;
    }
}
