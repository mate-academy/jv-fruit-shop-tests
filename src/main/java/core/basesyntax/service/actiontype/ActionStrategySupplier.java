package core.basesyntax.service.actiontype;

import core.basesyntax.dao.ActionsDao;
import core.basesyntax.fruit.FruitTransaction;

public class ActionStrategySupplier implements ActionType {
    private ActionsDao actionsDao;

    public ActionStrategySupplier(ActionsDao actionsDao) {
        this.actionsDao = actionsDao;
    }

    @Override
    public int getNewValue(FruitTransaction fruitTransaction) {
        return actionsDao.getAmount(fruitTransaction.getName()) + fruitTransaction.getAmount();
    }
}
