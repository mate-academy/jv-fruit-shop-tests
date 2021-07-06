package core.basesyntax.imp;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.model.Storage;
import core.basesyntax.service.FruitOperation;
import core.basesyntax.service.Validator;

public class FruitAdd implements FruitOperation {
    private Validator validator = new ValidatorImp();

    @Override
    public boolean operation(FruitRecordDto fruitRecordDto) {
        validator.checkAmount(fruitRecordDto);
        Fruit fruit = new Fruit(fruitRecordDto.getFruit().getName());
        int storageFruit = Storage.fruits.get(fruit) == null ? 0 : Storage.fruits.get(fruit);
        int newAmountFruit = storageFruit + fruitRecordDto.getAmount();
        Storage.fruits.put(fruit, newAmountFruit);
        return true;
    }
}
