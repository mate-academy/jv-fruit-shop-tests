package core.basesyntax.operations;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitOperationDto;
import core.basesyntax.service.parser.validator.FruitOperationDtoValidator;
import core.basesyntax.service.parser.validator.Validator;

public class PurchaseHandler implements OperationHandler {
    @Override
    public int apply(FruitOperationDto fruitOperationDto) {
        Validator validator = new FruitOperationDtoValidator();
        if (validator.validFruitOperationDto(fruitOperationDto)
                && Storage.storage.containsKey(fruitOperationDto.getFruit())
                && Storage.storage.get(fruitOperationDto.getFruit())
                >= fruitOperationDto.getQuantity()) {
            return Storage.storage.get(fruitOperationDto.getFruit())
                    - fruitOperationDto.getQuantity();
        }
        throw new RuntimeException("Can't purchase");
    }
}
