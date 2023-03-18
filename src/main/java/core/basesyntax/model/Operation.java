package core.basesyntax.model;

import java.util.Arrays;

public enum Operation {
    BALANCE("b"),
    SUPPLY("s"),
    PURCHASE("p"),
    RETURN("r");

    public static final String OPERATION_NOT_SUPPORTED = "Transaction operation %s not supported";
    private final String code;

    Operation(String code) {
        this.code = code;
    }

    public static Operation of(String code) {
        return Arrays.stream(Operation.values())
                .filter(o -> o.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        String.format(OPERATION_NOT_SUPPORTED, code)));
    }

    public String getCode() {
        return code;
    }
}
