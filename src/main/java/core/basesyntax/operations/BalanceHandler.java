package core.basesyntax.operations;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitOperationDto;
import core.basesyntax.service.parser.validator.FruitOperationDtoValidator;
import core.basesyntax.service.parser.validator.Validator;

public class BalanceHandler implements OperationHandler {
    @Override
    public int apply(FruitOperationDto fruitOperationDto) {
        Validator validator = new FruitOperationDtoValidator();
        validator.validFruitOperationDto(fruitOperationDto);
        Storage.storage.put(fruitOperationDto.getFruit(), fruitOperationDto.getQuantity());
        return fruitOperationDto.getQuantity();
    }
}
