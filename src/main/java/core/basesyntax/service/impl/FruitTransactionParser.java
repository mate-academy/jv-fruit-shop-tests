package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.Parser;
import java.util.ArrayList;
import java.util.List;

public class FruitTransactionParser implements Parser {
    private static final String SPLITER = ",";
    private static final int INDEX_OF_OPERATION = 0;
    private static final int INDEX_OF_FRUIT_NAME = 1;
    private static final int INDEX_OF_QUANTITY = 2;

    @Override
    public List<FruitTransaction> parse(List<String> fileData) {
        if (fileData == null) {
            throw new IllegalArgumentException("Can't parse this list because list is null");
        }
        List<FruitTransaction> fruits = new ArrayList<>();
        for (String line : fileData) {
            String[] splitedLine = line.split(SPLITER);
            if (splitedLine.length > 3 || splitedLine.length <= 1) {
                throw new IllegalArgumentException("Can't parse this list");
            }
            for (Operation operation : Operation.values()) {
                if (operation.getCode().equalsIgnoreCase(splitedLine[INDEX_OF_OPERATION])) {
                    fruits.add(new FruitTransaction(operation,
                            splitedLine[INDEX_OF_FRUIT_NAME],
                            Integer.parseInt(splitedLine[INDEX_OF_QUANTITY])));
                }
            }
        }
        return fruits;
    }
}
