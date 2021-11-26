package core.basesyntax.shop.service;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum Operation {
    BALANCE("b"),
    SUPPLY("s"),
    PURCHASE("p"),
    RETURN_BACK("r");

    private String operationCode;

    Operation(String operationCode) {
        this.operationCode = operationCode;
    }

    public String getOperationCode() {
        return operationCode;
    }

    public static String getOperationsString() {
        return Arrays.stream(Operation.values())
                .map(Operation::getOperationCode)
                .collect(Collectors.joining());
    }
}
