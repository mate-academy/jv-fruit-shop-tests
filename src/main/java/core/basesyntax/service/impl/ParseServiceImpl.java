package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseService;
import java.util.List;
import java.util.stream.Collectors;

public class ParseServiceImpl implements ParseService {
    private static final int NUMBER_OF_FIRST_LINE = 1;
    private static final int INDEX_FOR_OPERATION = 0;
    private static final int INDEX_FOR_NAME_FRUIT = 1;
    private static final int INDEX_FOR_QUANTITY = 2;

    @Override
    public List<FruitTransaction> parseTransactions(List<String> list) {
        return list.stream()
                .skip(NUMBER_OF_FIRST_LINE)
                .map(this::getFromCsvRow)
                .collect(Collectors.toList());
    }

    private FruitTransaction getFromCsvRow(String line) {
        String[] fields = line.split(",");
        FruitTransaction.Operation operation = FruitTransaction.Operation
                .getByCode(fields[INDEX_FOR_OPERATION]);
        return new FruitTransaction(
                operation,
                fields[INDEX_FOR_NAME_FRUIT],
                Integer.parseInt(fields[INDEX_FOR_QUANTITY])
        );
    }
}
