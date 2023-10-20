package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.ParserService;

import java.util.List;
import java.util.stream.Collectors;

public class ParserServiceImpl implements ParserService {
    private static final String COMA = ",";

    @Override
    public List<FruitTransaction> parseStringsIntoObjects(List<String> strings) {
        if (strings == null) {
            throw new RuntimeException("Cannot parse null");
        }
        return strings.stream().map(this::getFruitFromString).collect(Collectors.toList());
    }

    private FruitTransaction getFruitFromString(String string) {
        String[] parts = string.split(COMA);
        FruitTransaction fruitTransaction
                = FruitTransaction.of(getOperationFromString(parts[0]), parts[1],
                        Integer.parseInt(parts[2]));
        return fruitTransaction;
    }

    private Operation getOperationFromString(String string) {
        switch (string) {
            case "b": {
                return Operation.BALANCE;
            }
            case "s": {
                return Operation.SUPPLY;
            }
            case "r": {
                return Operation.RETURN;
            }
            default: {
                return Operation.PURCHASE;
            }
        }
    }
}
