package core.basesyntax.service.impl;

import core.basesyntax.dto.FruitTransaction;
import core.basesyntax.model.Fruit;
import java.util.Arrays;

public class ParserServiceImplImpl implements core.basesyntax.service.ParserServiceImpl {
    private static final String CSV_SEPARATOR = ",";
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final int REQUIRED_ELEMENTS = 3;

    @Override
    public FruitTransaction parseLine(String line) {
        String[] splitLine = line.split(CSV_SEPARATOR);
        validate(splitLine);
        return new FruitTransaction(splitLine[OPERATION_INDEX],
                new Fruit(splitLine[FRUIT_INDEX]),
                Integer.parseInt(splitLine[QUANTITY_INDEX]));
    }

    private void validate(String[] value) {
        if (value.length != REQUIRED_ELEMENTS || Integer.parseInt(value[QUANTITY_INDEX]) <= 0) {
            throw new RuntimeException("Data is not valid" + Arrays.toString(value));
        }
    }
}
