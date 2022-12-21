package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParser;
import java.util.List;
import java.util.stream.Collectors;

public class FruitTransactionParserImpl implements FruitTransactionParser {
    private static final int INFO_LINE = 0;
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int AMOUNT_INDEX = 2;
    private static final int CORRECT_LENGTH = 3;
    private static final String CONTAIN_INFO_LINE = "type,fruit,quantity";

    @Override
    public List<FruitTransaction> parse(List<String> lines) {
        if (lines == null || lines.isEmpty() || !lines.get(INFO_LINE).equals(CONTAIN_INFO_LINE)) {
            throw new RuntimeException("Wrong input data");
        }
        lines.remove(INFO_LINE);
        return lines.stream()
                .map(this::createTransactionFromLine)
                .collect(Collectors.toList());

    }

    private FruitTransaction createTransactionFromLine(String line) {
        FruitTransaction fruitTransaction = new FruitTransaction();
        if (line == null) {
            throw new RuntimeException("Line can't be null");
        }
        String[] fields = line.split(",");
        if (fields.length != CORRECT_LENGTH) {
            throw new RuntimeException("Wrong format data in line, line: " + line);
        }
        fruitTransaction.setOperation(FruitTransaction.Operation
                .getOperation(fields[OPERATION_INDEX]));
        fruitTransaction.setFruit(new Fruit(fields[FRUIT_INDEX]));
        fruitTransaction.setAmount(Integer.valueOf(fields[AMOUNT_INDEX]));
        return fruitTransaction;
    }
}
