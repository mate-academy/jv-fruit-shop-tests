package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.Mapper;
import core.basesyntax.service.Parser;
import java.util.List;
import java.util.stream.Collectors;

public class CsvParser implements Parser {
    private static final String SPLIT_VALUE = ",";
    private static final int SKIP_HEADER_LINE_NUMBER = 1;
    private final Mapper<FruitTransaction> fruitOperationMapper;

    public CsvParser(Mapper<FruitTransaction> fruitOperationMapper) {
        this.fruitOperationMapper = fruitOperationMapper;
    }

    @Override
    public List<FruitTransaction> parseData(List<String> rawData) {
        validate(rawData);
        return rawData.stream()
                .skip(SKIP_HEADER_LINE_NUMBER)
                .map(string -> fruitOperationMapper.getMappedObject(string.split(SPLIT_VALUE)))
                .collect(Collectors.toList());
    }

    private void validate(List<String> data) {
        if (data == null) {
            throw new RuntimeException("Data must not be null.");
        }
        if (data.isEmpty()) {
            throw new RuntimeException("Data is empty.");
        }
    }
}
