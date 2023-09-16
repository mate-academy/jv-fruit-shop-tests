package core.basesyntax.model;

import core.basesyntax.exceptions.InvalidDataException;
import java.util.Arrays;

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

    public static Operation getOperationType(String code) {
        return Arrays.stream(Operation.values())
                .filter(o -> o.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new InvalidDataException(
                        "Invalid operation code value: " + code));
    }

    public static Boolean validateOperationType(String code) {
        return Arrays.stream(Operation.values())
                .anyMatch(o -> o.getCode().equals(code));
    }
}
