package core.basesyntax.service.oparation;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.model.Storage;

public class PurchaseHandler implements OperationHandler {

    @Override
    public void apply(FruitRecordDto fruitRecordDto) {
        String nameFruit = fruitRecordDto.getFruitName();
        Fruit fruit = new Fruit(nameFruit);
        int subtractQuantity = fruitRecordDto.getQuantity();
        int oldQuantity = Storage.getFruitStorageMap().get(new Fruit(nameFruit));
        if (subtractQuantity <= Storage.getFruitStorageMap().get(fruit)) {
            Storage.getFruitStorageMap()
                    .put(new Fruit(nameFruit), (oldQuantity - subtractQuantity));
        } else {
            throw new RuntimeException("It isn't enough " + fruit.getName() + "!");
        }
    }
}
