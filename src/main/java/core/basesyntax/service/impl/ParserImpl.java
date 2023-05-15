package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.Parser;
import java.util.List;
import java.util.stream.Collectors;

public class ParserImpl implements Parser {
    @Override
    public List<FruitTransaction> parse(List<String> lines) {
        if (lines == null || lines.isEmpty()) {
            throw new IllegalArgumentException("Input file is empty");
        }
        return lines.stream()
                .skip(1)
                .map(this::parseLine)
                .collect(Collectors.toList());
    }

    private FruitTransaction parseLine(String line) {
        final int operationIndex = 0;
        final int fruitIndex = 1;
        final int quantityIndex = 2;
        String[] fields = line.split(",");
        Operation operation = Operation.getByCode(fields[operationIndex].trim());
        String fruit = fields[fruitIndex].trim();
        int quantity = Integer.parseInt(fields[quantityIndex].trim());
        return new FruitTransaction(operation, fruit, quantity);
    }
}
