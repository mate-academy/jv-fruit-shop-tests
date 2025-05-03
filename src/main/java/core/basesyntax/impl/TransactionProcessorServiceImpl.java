package core.basesyntax.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.service.TransactionProcessorService;
import core.basesyntax.strategy.TransactionStrategy;
import java.util.List;

public class TransactionProcessorServiceImpl implements TransactionProcessorService {
    private static final String SEPARATOR = ",";
    private static final int TYPE_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private TransactionStrategy transactionStrategy;
    private FruitDao dao;

    public TransactionProcessorServiceImpl(FruitDao dao, TransactionStrategy transactionStrategy) {
        this.transactionStrategy = transactionStrategy;
        this.dao = dao;
    }

    @Override
    public void processData(List<String> lines) {
        lines.stream()
                .skip(1)
                .map(n -> n.split(SEPARATOR))
                .forEach(n -> dao.update(n[FRUIT_INDEX], transactionStrategy.get(n[TYPE_INDEX])
                        .getAmount(dao.get(n[FRUIT_INDEX]),
                                Integer.parseInt(n[QUANTITY_INDEX]))));
    }
}
