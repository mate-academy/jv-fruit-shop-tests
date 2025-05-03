package core.basesyntax.service.activities;

import core.basesyntax.dao.InputDao;
import core.basesyntax.dao.InputDaoImpl;
import core.basesyntax.model.Fruit;

public class BalanceHandler implements Handler {
    private static final int SECOND_CELL_NUMBER = 1;
    private static final int LAST_CELL_NUMBER = 2;

    @Override
    public void createFruitObject(String[] splitLine) {
        InputDao inputDao = new InputDaoImpl();
        Fruit fruit = new Fruit();
        fruit.setFruitName(splitLine[SECOND_CELL_NUMBER]);
        fruit.setFruitQuantity(Integer.parseInt(splitLine[LAST_CELL_NUMBER]));
        inputDao.add(fruit);
    }
}
