package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CsvDataConverterImpl implements DataConverter {
    private static final String COMMA_DELIMITER = ",";
    private static final int FRUIT_INDEX = 1;
    private static final Pattern FRUIT_NAME_PATTERN = Pattern.compile("^?[a-zA-Z]?[a-z]+\\s?[a-zA-Z]?[a-z]+$");
    private static final int OPERATION_INDEX = 0;
    private static final int QUANTITY_INDEX = 2;
    private static final int VALUES_IN_LINE = 3;

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> lines) {
        List<FruitTransaction> fruitTransactionsList = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            fruitTransactionsList.add(formFruitTransaction(lines.get(i)));
        }
        return fruitTransactionsList;
    }

    private FruitTransaction formFruitTransaction(String line) {
        List<String> values = List.of(line.split(COMMA_DELIMITER));
        if (values.size() != VALUES_IN_LINE) {
            throw new RuntimeException("Wrong data format: " + line);
        }
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation
                .fromString(values.get(OPERATION_INDEX)));
        if (values.get(FRUIT_INDEX) == null || values.get(FRUIT_INDEX).isEmpty() || isValidFruitName(values.get(FRUIT_INDEX))) {
            throw new RuntimeException("Fruit cannot be null or empty");
        }
        fruitTransaction.setFruit(values.get(FRUIT_INDEX));
        fruitTransaction.setQuantity(Integer.parseInt(values.get(QUANTITY_INDEX)));
        return fruitTransaction;
    }

    private boolean isValidFruitName(String fruit) {
        Matcher matcher = FRUIT_NAME_PATTERN.matcher(fruit);
        return matcher.matches();
    }
}
