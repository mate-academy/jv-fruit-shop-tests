package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.CsvParserService;
import java.util.List;

public class CsvParserImpl implements CsvParserService {
    private static final int HEADER_INDEX = 0;
    private static final int HEADER_ROW_COUNT = 1;
    private static final int OPERATION_CODE_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int AMOUNT_INDEX = 2;
    private static final String SEPARATOR = ",";
    private static final String HEADER_PATTERN = "type,fruit,quantity";

    @Override
    public List<FruitTransaction> parse(List<String> fruitsList) {
        validateList(fruitsList);
        return fruitsList.stream()
                .skip(HEADER_ROW_COUNT)
                .map(s -> s.split(SEPARATOR))
                .map(this::getByRow)
                .toList();
    }

    private FruitTransaction getByRow(String... row) {
        if (row.length != 3) {
            throw new RuntimeException("Invalid row format");
        }
        Operation operation = Operation.getByCode(row[OPERATION_CODE_INDEX]);
        Fruit fruit = new Fruit(row[FRUIT_INDEX]);
        int amount = validateAmount(row[AMOUNT_INDEX]);
        return new FruitTransaction(operation, fruit, amount);
    }

    private int validateAmount(String amount) {
        try {
            return Integer.parseInt(amount.strip());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Amount must be an integer " + amount, e);
        }
    }

    private void validateList(List<String> fruitsList) {
        if (fruitsList.isEmpty() || !fruitsList.get(HEADER_INDEX).matches(HEADER_PATTERN)) {
            throw new RuntimeException("List must start with header " + HEADER_PATTERN);
        }
    }
}
