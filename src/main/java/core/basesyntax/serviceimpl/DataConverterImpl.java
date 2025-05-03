package core.basesyntax.serviceimpl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    private static final String SEPARATOR_SIGN = ",";
    private static final int OPERATOR_PART = 0;
    private static final int FRUIT_PART = 1;
    private static final int QUANTITY_PART = 2;

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> data) {
        if (!data.isEmpty()) {
            return data.stream()
                    .skip(1)
                    .map(line -> {
                        String[] parts = line.split(SEPARATOR_SIGN);
                        String opCode = parts[OPERATOR_PART];
                        FruitTransaction.Operation op = FruitTransaction.Operation.fromCode(opCode);
                        String fruit = parts[FRUIT_PART];
                        int quantity = Integer.parseInt(parts[QUANTITY_PART]);
                        if (quantity >= 0) {
                            return new FruitTransaction(op, fruit, quantity);
                        }
                        throw new IllegalArgumentException("Quantity can't be negative: "
                                + quantity);
                    })
                    .toList();
        }
        throw new RuntimeException("Data can't be empty");
    }
}
