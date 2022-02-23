package core.basesyntax.strategy;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.dto.FruitDto;

public class SupplyHandler implements Operation {
    @Override
    public void process(FruitDto fruitDto) {
        FruitDao storageDao = new FruitDaoImpl();
        if (storageDao.getValue(fruitDto.getName()) == null) {
            storageDao.save(new Fruit(fruitDto.getName(), fruitDto.getQuantity()));
            return;
        }
        Integer value = storageDao.getValue(fruitDto.getName());
        storageDao.save(new Fruit(fruitDto.getName(), fruitDto.getQuantity() + value));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return true;
    }
}
