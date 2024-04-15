package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.service.ReportCreator;
import java.util.stream.Collectors;

public class ReportCreatorImpl implements ReportCreator {

    private final FruitTransactionDao fruitTransactionDao;

    public ReportCreatorImpl(FruitTransactionDao fruitTransactionDao) {
        this.fruitTransactionDao = fruitTransactionDao;
    }

    @Override
    public String create() {
        return fruitTransactionDao.getFull()
                .stream()
                .filter(entry -> entry.getKey() != null)
                .map(entry -> entry.getKey() + "," + entry.getValue())
                .collect(Collectors.joining("\n", "fruit,quantity\n", ""));
    }
}
