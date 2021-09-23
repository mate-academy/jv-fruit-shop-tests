package core.basesyntax.fruitshop.service.operations;

import core.basesyntax.fruitshop.fruitstoragedb.FruitStorage;
import core.basesyntax.fruitshop.model.Fruit;
import core.basesyntax.fruitshop.model.RecordDto;
import core.basesyntax.fruitshop.service.operations.OperationHandler;

public class SupplyOperationHandler implements OperationHandler {
    @Override
    public void applyOperation(RecordDto data) {
        Fruit fruit = data.getFruitType();
        int supplyAmount = data.getAmount();
        if (FruitStorage.getStorage().containsKey(fruit)) {
            Integer amountBeforeSupply = FruitStorage.getStorage().get(fruit);
            Integer amountAfterSupply = amountBeforeSupply + supplyAmount;
            FruitStorage.getStorage().replace(fruit, amountBeforeSupply, amountAfterSupply);
        } else {
            FruitStorage.getStorage().put(fruit, supplyAmount);
        }
    }
}
