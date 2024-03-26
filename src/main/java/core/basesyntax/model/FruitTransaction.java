package core.basesyntax.model;

import java.util.Arrays;
import java.util.stream.Collectors;

public record FruitTransaction(Operation operation, String fruit, int quantity) {

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

        public static Operation getOperationByCode(String code) {
            return Arrays.stream(Operation.values())
                    .filter(operation -> operation.code.equals(code))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(
                            String.format("Invalid operation code: %s", code)
                    ));
        }

        public static String getAllOperationsCodes() {
            return Arrays.stream(Operation.values())
                    .map(Operation::getCode)
                    .collect(Collectors.joining());
        }
    }
}
