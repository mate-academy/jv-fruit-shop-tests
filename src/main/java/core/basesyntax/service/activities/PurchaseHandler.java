package core.basesyntax.service.activities;

import core.basesyntax.dao.InputDao;
import core.basesyntax.dao.InputDaoImpl;
import core.basesyntax.model.Fruit;

public class PurchaseHandler implements Handler {
    private static final int SECOND_CELL_NUMBER = 1;
    private static final int LAST_CELL_NUMBER = 2;

    @Override
    public void createFruitObject(String[] splitLine) {
        InputDao inputDao = new InputDaoImpl();
        int fruitNumber = Integer.parseInt(splitLine[LAST_CELL_NUMBER]);
        Fruit existingFruit = inputDao.getFruit(splitLine[SECOND_CELL_NUMBER]);
        if (existingFruit.getFruitQuantity() < fruitNumber) {
            throw new RuntimeException("Not enough " + splitLine[SECOND_CELL_NUMBER] + " to buy");
        }
        existingFruit.setFruitQuantity(existingFruit.getFruitQuantity()
                - fruitNumber);
    }
}
