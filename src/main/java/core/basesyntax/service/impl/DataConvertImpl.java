package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.List;

public class DataConvertImpl implements DataConverter {
    private static final String SEPARATOR = ",";
    private static final int ZERO_NUMBER = 0;
    private static final int ONE_NUMBER = 1;
    private static final int TWO_NUMBER = 2;
    private static final int THREE_NUMBER = 3;

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> lines) {
        List<FruitTransaction> transactions = new ArrayList<>();
        for (int i = ZERO_NUMBER; i < lines.size(); i++) {
            String[] components = lines.get(i).split(SEPARATOR);
            if (components.length != THREE_NUMBER) {
                throw new IllegalArgumentException("Invalid line format: " + lines.get(i));
            }

            FruitTransaction.Operation operation = FruitTransaction.Operation
                    .fromCode(components[ZERO_NUMBER]);
            String fruit = components[ONE_NUMBER];
            int quantity;
            try {
                quantity = Integer.parseInt(components[TWO_NUMBER]);
                if (quantity < 0) {
                    throw new IllegalArgumentException("Quantity cannot be negative: "
                            + components[TWO_NUMBER]);
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid quantity format: "
                        + components[TWO_NUMBER] + " in line: " + lines.get(i), e);
            }
            transactions.add(new FruitTransaction(operation, fruit, quantity));
        }
        return transactions;
    }
}
