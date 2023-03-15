package core.basesyntax.service.impl;

import core.basesyntax.exception.FruitStoreException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParserService;
import java.util.ArrayList;
import java.util.List;

public class TransactionParserServiceImpl implements TransactionParserService {
    private static final String DELIMITER = ",";
    private static final String REGEX_PATTERN = "-?\\d+";
    private static final int ARRAY_SIZE = 3;
    private static final int FIELD_TYPE = 0;
    private static final int FIELD_NAME = 1;
    private static final int FIELD_QUANTITY = 2;

    @Override
    public List<FruitTransaction> parse(List<String> records) {
        if (records == null) {
            throw new FruitStoreException("Incorrect input data for adding to storage");
        }
        List<FruitTransaction> parsedRecords = new ArrayList<>();
        for (int i = 0; i < records.size(); i++) {
            var buffer = records.get(i).split(DELIMITER);
            if (buffer.length != ARRAY_SIZE || buffer[FIELD_TYPE].isEmpty()
                    || buffer[FIELD_NAME].isEmpty() || buffer[FIELD_QUANTITY].isEmpty()
                    || !buffer[FIELD_QUANTITY].matches(REGEX_PATTERN)) {
                throw new FruitStoreException("Incorrect input data for adding to storage");
            }
            if (buffer.length == ARRAY_SIZE && buffer[FIELD_TYPE].length() == FIELD_NAME) {
                parsedRecords.add(new FruitTransaction(
                        FruitTransaction.Operation.getCode(buffer[FIELD_TYPE].trim()),
                        buffer[FIELD_NAME].trim(),
                        Integer.parseInt(buffer[FIELD_QUANTITY].trim())));
            }
        }
        return parsedRecords;
    }
}
