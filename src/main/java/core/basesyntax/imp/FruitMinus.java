package core.basesyntax.imp;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.model.Storage;
import core.basesyntax.service.FruitOperation;
import core.basesyntax.service.Validator;

public class FruitMinus implements FruitOperation {
    private Validator validator = new ValidatorImp();

    @Override
    public boolean operation(FruitRecordDto fruitRecordDto) {
        if (fruitRecordDto == null) {
            throw new RuntimeException();
        }
        validator.checkAmount(fruitRecordDto);
        Fruit fruit = new Fruit(fruitRecordDto.getFruit().getName());
        Integer storageFruit = Storage.fruits.get(fruit);
        int newAmountFruit = storageFruit - fruitRecordDto.getAmount();
        Storage.fruits.put(fruit, newAmountFruit);
        return true;
    }
}
