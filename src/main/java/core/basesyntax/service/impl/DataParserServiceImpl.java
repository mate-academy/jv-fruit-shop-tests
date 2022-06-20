package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataParserService;
import core.basesyntax.strategy.TransactionsStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DataParserServiceImpl implements DataParserService {
    private static final int TYPE_OF_OPERATION_INDEX = 0;
    private static final int FRUIT_TITLE_INDEX = 1;
    private static final int AMOUNT_INDEX = 2;
    private TransactionsStrategy transactionsStrategy;

    public DataParserServiceImpl(TransactionsStrategy transactionsStrategy) {
        this.transactionsStrategy = transactionsStrategy;
    }

    @Override
    public List<FruitTransaction> parse(List<String> dataFromFile) {
        if (dataFromFile == null) {
            throw new RuntimeException("List with file data can't be null");
        }
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        dataFromFile.stream()
                .map(string -> string.split(","))
                .skip(1)
                .forEach(getParseConsumer(fruitTransactions));
        return fruitTransactions;
    }

    private Consumer<String[]> getParseConsumer(List<FruitTransaction> fruitTransactions) {
        return stringArray ->
                fruitTransactions.add(
                        FruitTransaction.of(transactionsStrategy
                                .get(stringArray[TYPE_OF_OPERATION_INDEX]),
                                stringArray[FRUIT_TITLE_INDEX],
                                Integer.parseInt(stringArray[AMOUNT_INDEX])));
    }
}
