package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParser;
import java.util.List;
import java.util.stream.Collectors;

public class FruitTransactionParserImpl implements FruitTransactionParser {
    private static final String SPLITTER_FIELD = ";";
    private static final int HEADER = 0;
    private static final int OPERATION_FIELD_NUMBER = 0;
    private static final int FRUIT_FIELD_NUMBER = 1;
    private static final int QUANTITY_FIELD_NUMBER = 2;
    private static final int FIELDS_QUANTITY = 3; //added after approval
    private static final int MIN_DATA_LINES = 2; //added after approval

    @Override
    public List<FruitTransaction> parseFruitTransaction(List<String> inputStoreActivities) {
        if (inputStoreActivities.isEmpty()) { //added after approval
            throw new RuntimeException("The input file has no data");
        }
        if (inputStoreActivities.size() < MIN_DATA_LINES) { //added after approval
            throw new RuntimeException("There's no any information about fruits in input file");
        }
        inputStoreActivities.remove(HEADER);
        return inputStoreActivities.stream()
                .map(this::getTransactionRecordFromCsvRow)
                .collect(Collectors.toList());
    }

    private FruitTransaction getTransactionRecordFromCsvRow(String line) {
        String[] dataFields = line.split(SPLITTER_FIELD);
        if (dataFields.length < FIELDS_QUANTITY) { //added after approval
            throw new RuntimeException("Input File has incorrect records");
        }
        FruitTransaction.Operation operation = FruitTransaction.Operation
                .getByCode(dataFields[OPERATION_FIELD_NUMBER]);
        String fruit = dataFields[FRUIT_FIELD_NUMBER];
        int quantity = Integer.parseInt(dataFields[QUANTITY_FIELD_NUMBER]);
        return new FruitTransaction(operation, fruit, quantity);
    }
}
