package core.basesyntax.dataconverter;

import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DataConverterImpl implements DataConverter {
    private static final String SPLITTER = ",";
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_NAME_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> data) {
        List<FruitTransaction> transactions = new ArrayList<>();

        data.forEach(line -> {
            try {
                String[] fields = line.split(SPLITTER);
                if (fields.length < 3) {
                    throw new DataProcessingException(
                            "Error: the string does not contain all 3 fields: " + line);
                }
                if (fields[0].equals("") || fields[1].equals("") || fields[2].equals("")) {
                    throw new DataProcessingException(
                            "Invalid input format");
                }
                Set<String> validOperations = Set.of("s", "b", "p", "r");
                if (!validOperations.contains(fields[0])) {
                    throw new DataProcessingException("Unknown operation: " + fields[0]);
                }

                FruitTransaction.Operation operation =
                        FruitTransaction.Operation.fromCode(fields[OPERATION_INDEX].trim());
                String fruit = fields[FRUIT_NAME_INDEX].trim();

                int quantity;
                try {
                    quantity = Integer.parseInt(fields[QUANTITY_INDEX].trim());
                    if (quantity < 0) {
                        throw new DataProcessingException(
                                "Error: negative amount of fruit in the line: " + line);
                    }
                } catch (NumberFormatException e) {
                    throw new DataProcessingException(
                            "Error: quantity is not a number in the string: " + line);
                }

                transactions.add(new FruitTransaction(operation, fruit, quantity));

            } catch (DataProcessingException e) {
                throw new DataProcessingException(e.getMessage());
            }
        });

        return transactions;
    }
}
