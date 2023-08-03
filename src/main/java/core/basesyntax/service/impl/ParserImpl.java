package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.Parser;
import java.util.List;
import java.util.stream.Collectors;

public class ParserImpl implements Parser {
    private static final String COMMA_REGEX = ",";
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int AMOUNT_INDEX = 2;

    @Override
    public List<FruitTransaction> parseDataFromFile(List<String> data) {
        checkDataForNull(data);
        return data.stream()
                .map(this::createFruitTransaction)
                .collect(Collectors.toList());
    }

    private FruitTransaction createFruitTransaction(String dataLine) {
        checkDataColumnNumbers(dataLine);
        checkQuantityForZeroOrNegativeValue(dataLine);
        String[] splitLine = dataLine.split(COMMA_REGEX);
        return new FruitTransaction(
                splitLine[OPERATION_INDEX],
                splitLine[FRUIT_INDEX],
                Integer.parseInt(splitLine[AMOUNT_INDEX]));
    }

    private void checkDataForNull(List<String> data) {
        if (data == null) {
            throw new RuntimeException("Input data cannot be null.");
        }
    }

    private void checkDataColumnNumbers(String dataLine) {
        if (dataLine.split(COMMA_REGEX).length != 3) {
            throw new RuntimeException("Invalid CSV line: " + dataLine);
        }
    }

    private void checkQuantityForZeroOrNegativeValue(String dataLine) {
        if (Integer.parseInt(dataLine.split(COMMA_REGEX)[AMOUNT_INDEX]) <= 0) {
            throw new RuntimeException("Quantity cannot be equal or below zero. "
                    + "Invalid quantity in line " + dataLine);
        }
    }

}
