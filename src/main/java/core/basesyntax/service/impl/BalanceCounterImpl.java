package core.basesyntax.service.impl;

import core.basesyntax.dao.ActionsDao;
import core.basesyntax.fruit.FruitTransaction;
import core.basesyntax.service.ActionStrategy;
import core.basesyntax.service.BalanceCounter;
import java.util.List;

public class BalanceCounterImpl implements BalanceCounter {
    private ActionsDao actionsDao;

    public BalanceCounterImpl(ActionsDao actionsDao) {
        this.actionsDao = actionsDao;
    }

    @Override
    public void calculateBalance(List<FruitTransaction> fruitsMoving, ActionStrategy mapStrategy) {
        for (FruitTransaction fruitTransaction : fruitsMoving) {
            if (!actionsDao.isPresentFruit(fruitTransaction.getName())) {
                actionsDao.add(fruitTransaction.getName(), fruitTransaction.getAmount());
            }
            actionsDao.update(fruitTransaction.getName(),
                    mapStrategy.get(fruitTransaction.getTypeAction())
                            .getNewValue(fruitTransaction));
        }
    }
}
