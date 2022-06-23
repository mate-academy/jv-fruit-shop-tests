package core.basesyntax.service.actiontype;

import core.basesyntax.dao.ActionsDao;
import core.basesyntax.fruit.FruitTransaction;

public class ActionStrategyBalance implements ActionType {
    private ActionsDao actionsDao;

    public ActionStrategyBalance(ActionsDao actionsDao) {
        this.actionsDao = actionsDao;
    }

    @Override
    public int getNewValue(FruitTransaction fruitTransaction) {
        return fruitTransaction.getAmount();
    }
}
