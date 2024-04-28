package core.basesyntax.service.parser;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ParserCsvImpl implements ParserCsv {
    private static final String DELIMETER = ",";
    private static final Integer FRUIT_OPERATION_INDEX = 0;
    private static final Integer FRUIT_NAME_INDEX = 1;
    private static final Integer FRUIT_QUANTITY_INDEX = 2;

    @Override
    public List<FruitTransaction> parseTransactions(List<String> transactions) {
        if (transactions.isEmpty()) {
            throw new RuntimeException("Empty transaction list received");
        } else if (transactions.equals(null)) {
            throw new RuntimeException("Null value received");
        } else if (transactions.size() == 1) {
            throw new RuntimeException("The list of transactions must contain at least 2 lines!");
        }
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        List<String[]> parsedTransactions = convertTransactions(transactions);
        for (String[] element : parsedTransactions) {
            FruitTransaction fruitTransaction = new FruitTransaction(
                    FruitTransaction.Operation.getOperation(element[FRUIT_OPERATION_INDEX]),
                    element[FRUIT_NAME_INDEX],
                    Integer.parseInt(element[FRUIT_QUANTITY_INDEX])
            );
            fruitTransactions.add(fruitTransaction);
        }
        return fruitTransactions;
    }

    public List<String[]> convertTransactions(List<String> transactions) {
        return transactions.stream()
                .skip(1)
                .map(el -> el.split(DELIMETER))
                .collect(Collectors.toList());
    }
}
