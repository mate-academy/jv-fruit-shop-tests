package core.basesyntax.service.actiontype;

import core.basesyntax.dao.ActionsDao;
import core.basesyntax.fruit.FruitTransaction;

public class ActionStrategyPurchase implements ActionType {
    private ActionsDao actionsDao;

    public ActionStrategyPurchase(ActionsDao actionsDao) {
        this.actionsDao = actionsDao;
    }

    @Override
    public int getNewValue(FruitTransaction fruitTransaction) {
        if (actionsDao.getAmount(fruitTransaction.getName()) < fruitTransaction.getAmount()) {
            throw new RuntimeException("Amount of " + fruitTransaction.getName()
                    + " cant be less then zero");
        }
        return actionsDao.getAmount(fruitTransaction.getName()) - fruitTransaction.getAmount();
    }
}
