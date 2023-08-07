package core.basesyntax.services.actions;

import core.basesyntax.db.Storage;

public class SupplyActionHandler implements ActionHandler {
    private Storage fruitDB;

    public SupplyActionHandler(Storage fruitDB) {
        this.fruitDB = fruitDB;
    }

    @Override
    public boolean executeAction(String nameOfGoods, Integer valueOfTask) {
        validateInfo(nameOfGoods, valueOfTask);
        validateData(fruitDB, nameOfGoods);
        Integer value = fruitDB.getStorageFruits().get(nameOfGoods);
        return fruitDB.add(nameOfGoods, value + valueOfTask);
    }
}
