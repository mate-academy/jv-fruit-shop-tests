package core.basesyntax.services.actions;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.ValidationDataException;

public class BalanceActionHandler implements ActionHandler {
    private Storage fruitDB;

    public BalanceActionHandler(Storage fruitDB) {
        this.fruitDB = fruitDB;
    }

    @Override
    public boolean executeAction(String nameOfGoods, Integer valueOfTask) {
        validateInfo(nameOfGoods, valueOfTask);
        if (fruitDB == null) {
            throw new ValidationDataException("ActionHandle error db is null");
        }
        return fruitDB.add(nameOfGoods, valueOfTask);
    }
}
