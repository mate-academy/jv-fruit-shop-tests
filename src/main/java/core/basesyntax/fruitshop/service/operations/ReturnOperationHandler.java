package core.basesyntax.fruitshop.service.operations;

import core.basesyntax.fruitshop.fruitstoragedb.FruitStorage;
import core.basesyntax.fruitshop.model.Fruit;
import core.basesyntax.fruitshop.model.RecordDto;
import core.basesyntax.fruitshop.service.operations.OperationHandler;

public class ReturnOperationHandler implements OperationHandler {
    @Override
    public void applyOperation(RecordDto data) {
        Fruit fruit = data.getFruitType();
        int amountOfReturnedFruits = data.getAmount();
        if (FruitStorage.getStorage().containsKey(fruit)) {
            Integer amountAfterReturn = FruitStorage.getStorage()
                    .get(fruit) + amountOfReturnedFruits;
            FruitStorage.getStorage().replace(fruit, FruitStorage.getStorage()
                    .get(fruit), amountAfterReturn);
        } else {
            FruitStorage.getStorage().put(fruit, amountOfReturnedFruits);
        }
    }
}
