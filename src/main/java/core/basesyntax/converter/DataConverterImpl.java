package core.basesyntax.converter;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    private static final String SEPARATOR = ",";
    private static final int HEADER_INDEX = 1;
    private static final int EXPECTED_FIELDS_COUNT = 3;

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> data) {
        List<FruitTransaction> convertedTransaction = new ArrayList<>();
        for (int i = HEADER_INDEX; i < data.size(); i++) {
            String[] parts = data.get(i).split(SEPARATOR);

            if (parts.length != EXPECTED_FIELDS_COUNT) {
                throw new IllegalArgumentException("Invalid number of fields in row: "
                        + data.get(i));
            }

            FruitTransaction.Operation operation;
            try {
                operation = getOperation(parts[0]);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid operation code in row: "
                        + data.get(i), e);
            }

            String fruit = parts[1];
            int quantity;
            try {
                quantity = Integer.parseInt(parts[2]);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Invalid quantity for fruit '"
                        + fruit + "': " + parts[2]);
            }

            convertedTransaction.add(new FruitTransaction(operation, fruit, quantity));
        }
        return convertedTransaction;
    }

    public static Operation getOperation(String code) {
        for (Operation value : Operation.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException(code + " operation doesn't exist.");
    }
}
