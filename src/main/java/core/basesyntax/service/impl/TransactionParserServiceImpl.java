package core.basesyntax.service.impl;

import core.basesyntax.exception.FruitShopException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParserService;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionParserServiceImpl implements TransactionParserService {
    private static final int OPERATION = 0;
    private static final int FRUIT = 1;
    private static final int QUANTITY = 2;
    private static final String SEPARATOR = ",";

    @Override
    public List<FruitTransaction> parseFromListStrings(List<String> dataFromFile) {
        return dataFromFile.stream()
                .map(this::parseFromString)
                .collect(Collectors.toList());
    }

    @Override
    public FruitTransaction parseFromString(String inputStrings) {
        String[] data = inputStrings.split(SEPARATOR);
        if (data.length != 3) {
            throw new FruitShopException("Can't parse data from file");
        }
        return new FruitTransaction(data[OPERATION], data[FRUIT], data[QUANTITY]);
    }
}
