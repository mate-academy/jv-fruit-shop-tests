package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.exception.InvalidInputException;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecordDto;

public class RemoveOperation implements Operation {
    private FruitsDao fruitsDao;

    public RemoveOperation(FruitsDao fruitsDao) {
        this.fruitsDao = fruitsDao;
    }

    @Override
    public void apply(FruitRecordDto fruitRecordDto) {
        Fruit fruit = fruitsDao.getAmountByType(fruitRecordDto.getFruitType());
        int newAmount = fruitsDao.getAmount(fruit) - fruitRecordDto.getAmount();
        if (newAmount < 0) {
            throw new InvalidInputException("there are not enough "
                    + fruitRecordDto.getFruitType()
                    + " in the store");
        }
        fruitsDao.update(fruit, newAmount);
    }
}
