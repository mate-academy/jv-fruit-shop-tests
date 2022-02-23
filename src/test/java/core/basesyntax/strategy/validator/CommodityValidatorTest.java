package core.basesyntax.strategy.validator;

import static org.junit.Assert.assertTrue;

import core.basesyntax.model.FruitModel;
import core.basesyntax.strategy.handlers.ReturnOperationHandler;
import org.junit.Test;

public class CommodityValidatorTest {
    @Test(expected = RuntimeException.class)
    public void amountIsNegative_Exception() {
        CommodityValidator commodityValidator = new ReturnOperationHandler();
        FruitModel fruitModel = new FruitModel("pineapple", -100);
        commodityValidator.isFruitAmountCorrect(fruitModel, "r");
    }

    @Test
    public void amountIsCorrect_True() {
        CommodityValidator commodityValidator = new ReturnOperationHandler();
        FruitModel fruitModel = new FruitModel("garlic", 101);
        assertTrue(commodityValidator.isFruitAmountCorrect(fruitModel, "r"));
    }
}
