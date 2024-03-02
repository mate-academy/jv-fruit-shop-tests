package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.FileDataParser;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FileDataParserCsvImpl implements FileDataParser {
    private static final String SEPARATOR = ",";
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;

    @Override
    public List<FruitTransaction> parseData(List<String> dataFromFile) {
        return dataFromFile == null ? Collections.emptyList() : dataFromFile.stream()
            .map(this::parseFruitTransaction)
            .collect(Collectors.toList());
    }

    private FruitTransaction parseFruitTransaction(String line) {
        String[] parts = line.split(SEPARATOR);
        String operationCode = parts[OPERATION_INDEX];
        FruitTransaction.Operation operation = FruitTransaction.fromCode(operationCode);
        String fruit = parts[FRUIT_INDEX];
        int quantity = Integer.parseInt(parts[QUANTITY_INDEX]);
        return new FruitTransaction(operation, fruit, quantity);
    }
}
