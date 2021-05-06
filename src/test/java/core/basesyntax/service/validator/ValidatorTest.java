package core.basesyntax.service.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.FruitRecordDto;
import core.basesyntax.exceptions.IncorrectPurchaseRequestException;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import org.junit.Before;
import org.junit.Test;

public class ValidatorTest {
    private static FruitRecordDto correctFruitForDecrease;

    @Before
    public void setUp() {
        correctFruitForDecrease =
                new FruitRecordDto(OperationType.PURCHASE,
                        new Fruit("fruit"), 21);
    }

    @Test(expected = IncorrectPurchaseRequestException.class)
    public void validatorDoPurchase_NotOk() {
        Validator.canDoPurchase(-15, 12,
                correctFruitForDecrease);
    }

    @Test
    public void validatorIsValidLine_Ok() {
        boolean isValidLine = Validator.isValidLine(new String[]{"s"});
        assertTrue(isValidLine);
    }

    @Test
    public void validatorIsValidLine_NotOk() {
        boolean isValidLine = Validator.isValidLine(new String[]{"ss"});
        assertFalse(isValidLine);
    }
}
