package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.TransactionStrategy;
import java.util.List;
import java.util.stream.Collectors;

public class FruitServiceImpl implements FruitService {
    private static final String REPORT_HEADER = "fruit,quantity";
    private final FruitDao fruitDao;
    private final TransactionStrategy transactionStrategy;

    public FruitServiceImpl(FruitDao fruitDao,
                            TransactionStrategy transactionStrategy) {
        this.fruitDao = fruitDao;
        this.transactionStrategy = transactionStrategy;
    }

    @Override
    public void processTransactions(List<FruitTransaction> fruitList) {
        fruitList.forEach(fruit ->
                transactionStrategy.getOperation(fruit.getOperationType())
                        .handle(fruit));
    }

    @Override
    public List<String> createReport() {
        List<String> stringList = fruitDao.getAll().entrySet().stream()
                .map(fruitInfo -> fruitInfo.getKey() + "," + fruitInfo.getValue())
                .collect(Collectors.toList());
        stringList.add(0, REPORT_HEADER);
        return stringList;
    }
}

