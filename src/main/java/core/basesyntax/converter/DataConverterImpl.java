package core.basesyntax.converter;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    private TransactionParser parser;

    public DataConverterImpl(TransactionParser parser) {
        this.parser = parser;
    }

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> inputTransactions) {
        if (inputTransactions == null) {
            throw new RuntimeException("Input list can't be null");
        }
        return inputTransactions.stream()
                .map(s -> parser.parseToTransaction(s))
                .toList();
    }
}
