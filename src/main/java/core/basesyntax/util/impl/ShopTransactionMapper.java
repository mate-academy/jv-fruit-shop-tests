package core.basesyntax.util.impl;

import core.basesyntax.model.ShopTransaction;
import core.basesyntax.util.Mapper;
import java.util.Objects;

public class ShopTransactionMapper implements Mapper<String, ShopTransaction> {
    @Override
    public ShopTransaction map(String from) {
        Objects.requireNonNull(from, "transaction string is null");

        String[] parts = from
                .replaceAll("\\s", "")
                .split(",");

        if (parts.length != 3) {
            throw new RuntimeException("Invalid transaction format: " + from);
        }

        String productName = parts[1];
        if (parts[1].isBlank()) {
            throw new RuntimeException("Product name can't be blank");
        }

        int quantity;
        try {
            quantity = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid number argument: " + parts[2], e);
        }

        String stringOperation = parts[0];
        ShopTransaction.Operation operation = switch (stringOperation) {
            case "b" -> ShopTransaction.Operation.BALANCE;
            case "s" -> ShopTransaction.Operation.SUPPLY;
            case "p" -> ShopTransaction.Operation.PURCHASE;
            case "r" -> ShopTransaction.Operation.RETURN;
            default -> throw new RuntimeException("Unexpected operation: " + stringOperation);
        };

        return new ShopTransaction(operation, productName, quantity);
    }
}
