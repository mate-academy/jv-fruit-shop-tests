package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.DataConverter;
import java.util.List;

public class DataConverterImpl implements DataConverter {

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> transactions) {
        if (transactions == null || transactions.isEmpty()) {
            throw new IllegalArgumentException("Transactions cannot be null or empty");
        }
        return transactions.stream().map(s -> s.split(","))
                .map(this::parseTransaction)
                .toList();
    }

    private int parseInteger(String s) {

        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid integer: " + s);
        }
    }

    private FruitTransaction parseTransaction(String[] s) {
        for (int i = 0; i < s.length; i++) {
            s[i] = s[i].trim();
        }
        if (s.length != 3) {
            throw new IllegalArgumentException("wrong transaction length");
        }
        if (s[1] == null || s[1].isEmpty()) {
            throw new IllegalArgumentException("Wrong fruit name");
        }
        return new FruitTransaction(Operation.fromCode(s[0]), s[1],
                parseInteger(s[2]));

    }
}
