package core.basesyntax.shop.service.operations;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.shop.item.Fruit;

public class Return implements OperationHandler {
    private FruitDao fruitDao = new FruitDaoImpl();

    @Override
    public void apply(Fruit fruit) {
        fruitDao.update(new Fruit(fruit.getName(),
                (fruit.getQuality() + fruitDao.get(fruit).getQuality())));
    }
}
