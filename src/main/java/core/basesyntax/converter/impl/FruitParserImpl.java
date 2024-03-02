package core.basesyntax.converter.impl;

import core.basesyntax.converter.FruitParser;
import core.basesyntax.model.FruitTransaction;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FruitParserImpl implements FruitParser {
    private static final String HEADER_LINE = "type,fruit,quantity";
    private static final String CSV_DELIMITER = ",";
    private static final int HEADER_OFFSET = 0;
    private static final int CSV_ROW_ELEMENTS_COUNT = 3;
    private static final int ACTIVITY_TYPE_POS = 0;
    private static final int FRUIT_NAME_POS = 1;
    private static final int QUANTITY_POS = 2;

    @Override
    public List<FruitTransaction> parseFruitTransactions(List<String> lines) {
        if (lines == null) {
            throw new IllegalArgumentException("Passed method argument is null");
        }
        if (lines.get(HEADER_OFFSET).contains(HEADER_LINE)) {
            lines.remove(HEADER_OFFSET);
        }
        return lines.stream()
                .filter(Objects::nonNull)
                .map(this::parseLine)
                .collect(Collectors.toList());
    }

    private FruitTransaction parseLine(String line) {
        String[] splitLine = splitAndCheckLine(line);
        FruitTransaction.Operation fruitOperation =
                FruitTransaction.Operation.getOperationByCode(splitLine[ACTIVITY_TYPE_POS]);
        return new FruitTransaction(fruitOperation,
                splitLine[FRUIT_NAME_POS],
                Integer.parseInt(splitLine[QUANTITY_POS]));
    }

    private String[] splitAndCheckLine(String line) {
        String[] splitLine = line.split(CSV_DELIMITER);
        if (splitLine.length != CSV_ROW_ELEMENTS_COUNT) {
            throw new IllegalArgumentException("Some column in csv file is absent");
        }
        if (splitLine[ACTIVITY_TYPE_POS].isEmpty()) {
            throw new IllegalArgumentException("Activity field can't be empty");
        }
        if (splitLine[FRUIT_NAME_POS].isEmpty()) {
            throw new IllegalArgumentException("Fruit name field can't be empty");
        }
        try {
            if (Integer.parseInt(splitLine[QUANTITY_POS]) < 0) {
                throw new IllegalArgumentException("Quantity value can't be negative");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Csv number parameter should be numeric");
        }
        return splitLine;
    }
}
