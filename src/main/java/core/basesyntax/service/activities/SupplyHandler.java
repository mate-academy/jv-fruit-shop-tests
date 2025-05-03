package core.basesyntax.service.activities;

import core.basesyntax.dao.InputDao;
import core.basesyntax.dao.InputDaoImpl;
import core.basesyntax.model.Fruit;

public class SupplyHandler implements Handler {
    private static final int SECOND_CELL_NUMBER = 1;
    private static final int LAST_CELL_NUMBER = 2;

    @Override
    public void createFruitObject(String[] splitLine) {
        InputDao inputDao = new InputDaoImpl();
        Fruit existingFruit = inputDao.getFruit(splitLine[SECOND_CELL_NUMBER]);
        existingFruit.setFruitQuantity(existingFruit.getFruitQuantity()
                + Integer.parseInt(splitLine[LAST_CELL_NUMBER]));
    }
}
