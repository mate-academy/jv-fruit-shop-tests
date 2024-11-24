package core.basesyntax;

import java.util.Arrays;

public enum Operation {
    BALANCE("b"),
    SUPPLY("s"),
    PURCHASE("p"),
    RETURN("r");

    private final String code;

    Operation(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Operation fromCode(String code) {
        return Arrays.stream(Operation.values())
                .filter(op -> op.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid operation code: " + code));
    }
}
