package core.basesyntax.strategy;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.dto.FruitDto;

public class PurchaseHandler implements Operation {
    private FruitDao fruitDao = new FruitDaoImpl();

    @Override
    public void process(FruitDto fruitDto) {
        Integer value = fruitDao.getValue(fruitDto.getName());
        if (value == null) {
            return;
        }
        fruitDao.save(new Fruit(fruitDto.getName(), value - fruitDto.getQuantity()));
    }
}
