package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ParseServiceImpl implements ParseService {
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;

    @Override
    public List<FruitTransaction> getInfo(List<String> list) {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        list.stream()
                .filter(Objects::nonNull)
                .map(i -> i.split("-"))
                .forEach(i -> fruitTransactions.add(
                        new FruitTransaction(getOperation(i[OPERATION_INDEX]),
                        new Fruit(i[FRUIT_INDEX]), Integer.parseInt(i[QUANTITY_INDEX]))));
        return fruitTransactions;
    }

    private FruitTransaction.Operation getOperation(String option) {
        switch (option) {
            case "b":
                return FruitTransaction.Operation.BALANCE;
            case "p":
                return FruitTransaction.Operation.PURCHASE;
            case "s":
                return FruitTransaction.Operation.SUPPLY;
            case "r":
                return FruitTransaction.Operation.RETURN;
            default:
                throw new RuntimeException("Unknown operation " + option);
        }
    }
}
