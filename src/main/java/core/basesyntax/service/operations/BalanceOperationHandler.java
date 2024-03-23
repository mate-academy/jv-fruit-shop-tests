package core.basesyntax.service.operations;

import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.exeptions.UnsupportedOperationExeption;
import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;

public class BalanceOperationHandler implements OperationHandler {
    public static final String BALANCE_EXEPTION_MESSAGE = "There is no applicable operation!";
    public static final String NULL_DTO_EXEPTION_MESSAGE = "Dto cant be null!";

    @Override
    public void apply(FruitTransactionDto dto) {
        if (dto == null) {
            throw new UnsupportedOperationExeption(NULL_DTO_EXEPTION_MESSAGE);
        }
        if (!isApplicable(dto)) {
            throw new UnsupportedOperationExeption(BALANCE_EXEPTION_MESSAGE);
        }
        Storage.fruits.put(new Fruit(dto.fruitName()), dto.quantity());
    }

    @Override
    public boolean isApplicable(FruitTransactionDto dto) {
        return "b".equals(dto.operationType());
    }
}
