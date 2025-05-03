package core.basesyntax.dataprocessor;

import core.basesyntax.service.FruitTransaction;
import java.util.ArrayList;
import java.util.List;

public class DataConverter {
    private static final int TYPE_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final String SEPARATOR = ",";
    private static final int LIMITER = 3;

    public List<FruitTransaction> convert(List<String> rawData) {
        List<FruitTransaction> transactions = new ArrayList<>();
        for (String record : rawData) {
            try {
                String[] parts = record.split(SEPARATOR);
                if (parts.length != LIMITER) {
                    throw new DataConversionException("Invalid record format: " + record);
                }
                String operation = parts[TYPE_INDEX].trim();
                String fruit = parts[FRUIT_INDEX].trim();
                int quantity = Integer.parseInt(parts[QUANTITY_INDEX].trim());
                transactions.add(new FruitTransaction(operation, fruit, quantity));
            } catch (NumberFormatException e) {
                throw new DataConversionException("Invalid quantity in record: " + record);
            }
        }
        return transactions;
    }
}
