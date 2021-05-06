package core.basesyntax.imp;

import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.service.Validator;

public class ValidatorImp implements Validator {
    @Override
    public boolean checkAmount(FruitRecordDto fruit) {
        if (fruit.getAmount() < 0) {
            throw new RuntimeException("Wrong fruit !");
        }
        return true;
    }
}
