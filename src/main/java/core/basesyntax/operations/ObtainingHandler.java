package core.basesyntax.operations;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitOperationDto;
import core.basesyntax.service.parser.validator.FruitOperationDtoValidator;
import core.basesyntax.service.parser.validator.Validator;

public class ObtainingHandler implements OperationHandler {

    @Override
    public int apply(FruitOperationDto fruitOperationDto) {
        Validator validator = new FruitOperationDtoValidator();
        if ((Storage.storage.containsKey(fruitOperationDto.getFruit()))
                && validator.validFruitOperationDto(fruitOperationDto)) {
            return Storage.storage.get(fruitOperationDto.getFruit())
                    + fruitOperationDto.getQuantity();
        }
        OperationHandler operationHandler = new BalanceHandler();
        return operationHandler.apply(fruitOperationDto);
    }
}
