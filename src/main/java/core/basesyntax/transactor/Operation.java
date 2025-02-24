package core.basesyntax.transactor;

import java.util.stream.Stream;

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

    public static Operation getOperationCode(String code) {
        return Stream.of(Operation.values())
                .filter(o -> o.getCode().equals(code))
                .findFirst().orElseThrow(() -> new RuntimeException(
                        "No operation following code: " + code));
    }

}
