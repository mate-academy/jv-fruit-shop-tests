package core.basesyntax.model;

import java.util.Objects;

public record ShopTransaction(Operation operation, String product, int quantity) {
    public ShopTransaction {
        Objects.requireNonNull(operation, "operation is null");
        Objects.requireNonNull(product, "product is null");
    }

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
    }
}
