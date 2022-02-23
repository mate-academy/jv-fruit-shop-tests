package core.basesyntax.strategy;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.dto.FruitDto;

public class BalanceHandler implements Operation {
    @Override
    public void process(FruitDto fruitDto) {
        FruitDao storageDao = new FruitDaoImpl();
        if (fruitDto.getName() == null) {
            throw new IllegalArgumentException("name can't be null");
        }
        if (fruitDto.getQuantity() < 0) {
            throw new IllegalArgumentException();
        }
        storageDao.save(new Fruit(fruitDto.getName(), fruitDto.getQuantity()));
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
