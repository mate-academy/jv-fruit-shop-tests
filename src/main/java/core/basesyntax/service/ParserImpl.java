package core.basesyntax.service;

import core.basesyntax.dto.ShopOperation;

public class ParserImpl implements Parser {
    static final String SEPARATOR = ",";
    static final int OPERATION_IN_ARRAY = 0;
    static final int FRUIT_IN_ARRAY = 1;
    static final int AMOUNT_IN_ARRAY = 2;
    static final int TOTAL_ELEMENTS = 3;

    @Override
    public ShopOperation parseLine(String line) {
        String[] splitLine = line.split(SEPARATOR);
        if (splitLine.length != TOTAL_ELEMENTS) {
            throw new RuntimeException("wrong input line");
        }
        String operation = splitLine[OPERATION_IN_ARRAY];
        String fruit = splitLine[FRUIT_IN_ARRAY];
        int amount;
        try {
            amount = Integer.parseInt(splitLine[AMOUNT_IN_ARRAY]);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Wrong fruit amount");
        }
        if (amount <= 0) {
            throw new RuntimeException("Wrong fruit amount");
        }
        return new ShopOperation(operation, fruit, amount);
    }
}
