package core.basesyntax.service.impl;

import core.basesyntax.OperationType;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataParser;

public class CsvDataParserImpl implements DataParser {
    private static final int OPERATION_TYPE_INDEX = 0;
    private static final int PRODUCT_NAME_INDEX = 1;
    private static final int PRODUCT_AMOUNT_INDEX = 2;
    private static final int EXPECTED_LENGTH = 3;
    private final String columnSeparator;
    private String[] stringToParse;

    public CsvDataParserImpl(String columnSeparator) {
        this.columnSeparator = columnSeparator;
    }

    public FruitTransaction parse(String stringToParse) {
        this.stringToParse = stringToParse.split(columnSeparator);
        if (this.stringToParse.length != EXPECTED_LENGTH) {
            throw new RuntimeException("Incorrect string format");
        }
        return new FruitTransaction(getOperationType(),
                getProductName(), getProductAmount());
    }

    private OperationType getOperationType() {
        String type = stringToParse[OPERATION_TYPE_INDEX].strip();
        return OperationType.fromString(type);
    }

    private String getProductName() {
        return stringToParse[PRODUCT_NAME_INDEX].strip();
    }

    private int getProductAmount() {
        return Integer.parseInt(stringToParse[PRODUCT_AMOUNT_INDEX].strip());
    }
}
