package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import core.basesyntax.service.Parser;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    public static final int NUMBER_OF_SKIP = 1;
    private Parser<FruitTransaction> parser = new ParserImpl();

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> inputData) {
        return inputData.stream()
                .skip(NUMBER_OF_SKIP)
                .map(parser::parse)
                .toList();
    }
}
