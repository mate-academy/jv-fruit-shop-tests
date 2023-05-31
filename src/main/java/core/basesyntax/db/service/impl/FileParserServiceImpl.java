package core.basesyntax.db.service.impl;

import core.basesyntax.db.model.FruitTransaction;
import java.util.List;
import java.util.stream.Collectors;

public class FileParserServiceImpl implements core.basesyntax.db.service.FileParserService {
    private static final String REGEX = ",";
    private static final int INDEX_OPERATION = 0;
    private static final int INDEX_FRUIT = 1;
    private static final int INDEX_QUANTITY = 2;

    @Override
    public List<FruitTransaction> parse(List<String> inputFile) {
        return inputFile.stream()
                .map(String::toLowerCase)
                .map(this::toTransaction)
                .collect(Collectors.toList());
    }

    @Override
    public FruitTransaction toTransaction(String line) {
        String[] splitLine = line.split(REGEX);
        return new FruitTransaction(FruitTransaction.Operation
                .getOperationStrChar(splitLine[INDEX_OPERATION]),
                splitLine[INDEX_FRUIT],
                Math.abs(Integer.parseInt(splitLine[INDEX_QUANTITY])));
    }
}
