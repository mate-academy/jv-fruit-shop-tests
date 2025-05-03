package core.basesyntax.service.actiontype;

import core.basesyntax.dao.ActionsDao;
import core.basesyntax.fruit.FruitTransaction;

public class ActionStrategyReturner implements ActionType {
    private ActionsDao actionsDao;

    public ActionStrategyReturner(ActionsDao actionsDao) {
        this.actionsDao = actionsDao;
    }

    @Override
    public int getNewValue(FruitTransaction fruitTransaction) {
        return actionsDao.getAmount(fruitTransaction.getName()) + fruitTransaction.getAmount();
    }
}
