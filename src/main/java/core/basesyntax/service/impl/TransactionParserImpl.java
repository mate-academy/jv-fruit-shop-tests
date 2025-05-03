package core.basesyntax.service.impl;

import static java.util.stream.Collectors.toList;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.TransactionParser;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class TransactionParserImpl implements TransactionParser {
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final String COMMA = ",";
    private static final Integer UPPER_BOUND = 1000;

    @Override
    public List<FruitTransaction> parseData(List<String> fruitTransactions) {
        Predicate<String> nonNullPredicate = Objects::nonNull;
        Predicate<String> operationPredicate = s -> s.split(COMMA)[OPERATION_INDEX].length() == 1
                && Character.isLetter(s.charAt(OPERATION_INDEX));
        Predicate<String> quantityPredicate
                = s -> Integer.parseInt(s.split(COMMA)[QUANTITY_INDEX]) <= UPPER_BOUND;
        return fruitTransactions.stream()
                .filter(nonNullPredicate.and(operationPredicate).and(quantityPredicate))
                .map(s -> createFruitTransaction(s.split(COMMA)))
                .collect(toList());
    }

    private FruitTransaction createFruitTransaction(String[] splitString) {
        return new FruitTransaction(Operation.getByCode(splitString[OPERATION_INDEX]),
                splitString[FRUIT_INDEX], Integer.parseInt(splitString[QUANTITY_INDEX]));
    }
}
