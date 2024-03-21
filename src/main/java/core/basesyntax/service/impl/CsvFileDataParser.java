package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public class CsvFileDataParser implements core.basesyntax.service.FileDataParser {
    public static final int HEADERS_LINE_NUMBER = 1;
    private static final int FRUIT_INDEX = 1;
    private static final int OPERATION_INDEX = 0;
    private static final int QUANTITY_INDEX = 2;
    private static final String REGEX_COMMA_SEPARATOR = ",";
    public static final String REGEX_INPUT_LINE_VALIDATE = "^[bspr],\\w+,\\d+$";

    @Override
    public List<FruitTransaction> parseData(List<String> data) {
        return data.stream()
                .skip(HEADERS_LINE_NUMBER)
                .map(line -> {
                    validateLine(line);
                    return line.split(REGEX_COMMA_SEPARATOR);
                })
                .map(this::create)
                .toList();
    }

    private void validateLine(String line) {
        if (!line.matches(REGEX_INPUT_LINE_VALIDATE)) {
            throw new IllegalArgumentException(String.format("Invalid input line %s", line));
        }
    }

    private FruitTransaction create(String[] data) {
        return new FruitTransaction(
                FruitTransaction.Operation.getOperationByCode(data[OPERATION_INDEX]),
                data[FRUIT_INDEX],
                Integer.parseInt(data[QUANTITY_INDEX])
        );
    }
}
