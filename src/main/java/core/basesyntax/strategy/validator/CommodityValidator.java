package core.basesyntax.strategy.validator;

import core.basesyntax.model.FruitModel;

public interface CommodityValidator extends OperationValidator {
    default boolean isFruitAmountCorrect(FruitModel fruitModel, String operationName) {
        if (fruitModel.getAmount() < 0) {
            throw new RuntimeException("Negative amount for operation " + operationName
                    + System.lineSeparator() + fruitModel);
        }
        return true;
    }
}
