package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.service.ProcessorService;
import core.basesyntax.strategy.TransactionStrategy;
import java.util.List;

public class ProcessorServiceImpl implements ProcessorService {
    private static final String SEPARATOR = ",";
    private static final int TYPE_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final String TYPE_COLUMN = "type";
    private static final String FRUIT_COLUMN = "fruit";
    private static final String QUANTITY_COLUMN = "quantity";
    private TransactionStrategy transactionStrategy;
    private FruitDao dao;

    public ProcessorServiceImpl(FruitDao dao, TransactionStrategy transactionStrategy) {
        this.transactionStrategy = transactionStrategy;
        this.dao = dao;
    }

    @Override
    public void processData(List<String> rawData) {
        if (!rawData.get(0).equals(TYPE_COLUMN + SEPARATOR
                + FRUIT_COLUMN + SEPARATOR + QUANTITY_COLUMN)) {
            throw new RuntimeException("Invalid header");
        }
        rawData.stream()
                .skip(1)
                .map(n -> n.split(SEPARATOR))
                .forEach(n -> dao.update(n[FRUIT_INDEX], transactionStrategy.get(n[TYPE_INDEX])
                            .getAmount(dao.get(n[FRUIT_INDEX]),
                                    Integer.parseInt(n[QUANTITY_INDEX]))));
    }
}
