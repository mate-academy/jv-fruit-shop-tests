package core.basesyntax.service.oparation;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.model.Storage;

public class AddOperationHandler implements OperationHandler {

    @Override
    public void apply(FruitRecordDto fruitRecordDto) {
        String nameFruit = fruitRecordDto.getFruitName();
        Fruit fruit = new Fruit(nameFruit);
        int addQuantity = fruitRecordDto.getQuantity();
        int oldQuantity = Storage.getFruitStorageMap().get(fruit);
        Storage.getFruitStorageMap().put(new Fruit(nameFruit), (oldQuantity + addQuantity));
    }
}
