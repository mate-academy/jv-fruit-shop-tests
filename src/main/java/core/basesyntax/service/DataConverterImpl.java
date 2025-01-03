package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    private static final int ARRAY_EXPECTED_LENGTH = 3;

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> data) {
        List<FruitTransaction> transactions = new ArrayList<>();
        for (String line: data) {
            String[] elements = line.split(",");
            if (elements.length != ARRAY_EXPECTED_LENGTH) {
                throw new IllegalArgumentException("Invalid input format. "
                        + "Expected 3 elements separated by commas.");
            }
            FruitTransaction fruitTransaction;
            try {
                fruitTransaction = new FruitTransaction(
                                FruitTransaction.Operation.fromCode(elements[0]),
                                elements[1],
                                Integer.parseInt(elements[2])
                        );
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid number format for quantity: "
                        + elements[2], e);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid operation code: " + elements[0], e);
            }
            if (Integer.parseInt(elements[2]) < 0) {
                throw new IllegalArgumentException("Quantity cannot be negative: " + elements[2]);
            }
            transactions.add(fruitTransaction);
        }
        return transactions;
    }
}
