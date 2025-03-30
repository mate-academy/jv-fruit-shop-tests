package core.basesyntax.converter;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import java.util.function.Function;

public class DataConverterImpl implements DataConverter {
    private final Function<String, FruitTransaction> transactionMapper;

    public DataConverterImpl(Function<String, FruitTransaction> transactionMapper) {
        this.transactionMapper = transactionMapper;
    }

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> inputTransactions) {
        if (inputTransactions == null) {
            throw new RuntimeException("Input list can't be null");
        }
        return inputTransactions.stream()
                .map(transactionMapper)
                .toList();
    }
}
