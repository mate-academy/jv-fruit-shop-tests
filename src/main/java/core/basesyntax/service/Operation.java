package core.basesyntax.service;

import java.util.Arrays;

public enum Operation {
    BALANCE("b"),
    SUPPLY("s"),
    PURCHASE("p"),
    RETURN("r");

    private String abbreviation;

    Operation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public static Operation getOperationValue(String str) {
        return Arrays.stream(Operation.values())
                .filter(operation -> operation.getAbbreviation().equals(str))
                .findFirst()
                .orElseThrow();

    }
}
