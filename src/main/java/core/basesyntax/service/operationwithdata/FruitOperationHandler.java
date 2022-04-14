package core.basesyntax.service.operationwithdata;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.dto.FruitDto;
import core.basesyntax.fruitmodel.Fruit;
import core.basesyntax.fruitoperationstrategy.FruitStrategy;
import java.util.List;

public class FruitOperationHandler implements OperationHandler {
    private FruitStrategy fruitStrategy;

    public FruitOperationHandler(FruitStrategy fruitStrategy) {
        this.fruitStrategy = fruitStrategy;
    }

    @Override
    public int operationProcessing(List<FruitDto> fruitDtos) {
        FruitDao fruitDao = new FruitDaoImpl();
        int quantity = 0;
        for (FruitDto fruit : fruitDtos) {
            FruitOperationService fruitOperationService = fruitStrategy.get(fruit.getOperation());
            quantity = fruitOperationService.apply(fruit);
            fruitDao.save(new Fruit(fruit.getFruitName()), quantity);
        }
        return quantity;
    }
}
