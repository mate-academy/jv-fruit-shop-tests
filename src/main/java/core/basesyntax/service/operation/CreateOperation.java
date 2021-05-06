package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecordDto;

public class CreateOperation implements Operation {
    private FruitsDao fruitsDao;

    public CreateOperation(FruitsDao fruitsDao) {
        this.fruitsDao = fruitsDao;
    }

    @Override
    public void apply(FruitRecordDto fruitRecordDto) {
        fruitsDao.add(new Fruit(fruitRecordDto.getFruitType()), fruitRecordDto.getAmount());
    }
}
