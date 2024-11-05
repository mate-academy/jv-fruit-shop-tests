package core.basesyntax.dao.impl;

import core.basesyntax.dao.DataConverter;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    private static final String COMMA_SEPARATOR = ",";
    private static final int EXPECTED_PARTS_LENGTH = 3;
    private static final int MINIMAL_QUANTITY = 0;

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> report) {
        List<FruitTransaction> transactionsReport = new ArrayList<>();
        for (String line : report) {
            String[] values = line.trim().split(COMMA_SEPARATOR);
            if (values.length != EXPECTED_PARTS_LENGTH) {
                throw new IllegalArgumentException("Invalid format in report line: " + line);
            }
            Operation operation;
            try {
                operation = Operation.valueOf(values[0].trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Unsupported operation in line: " + line, e);
            }

            String fruit = values[1].trim();
            int quantity;
            try {
                quantity = Integer.parseInt(values[2].trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid quantity format in line: " + line, e);
            }

            if (quantity < MINIMAL_QUANTITY) {
                throw new IllegalArgumentException("Illegal quantity in line: " + line);
            }
            transactionsReport.add(new FruitTransaction(operation, fruit, quantity));
        }
        return transactionsReport;
    }
}
