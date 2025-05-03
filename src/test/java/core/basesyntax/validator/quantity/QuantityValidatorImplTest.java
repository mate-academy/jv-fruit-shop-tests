package core.basesyntax.validator.quantity;

import org.junit.BeforeClass;
import org.junit.Test;

public class QuantityValidatorImplTest {

    private static QuantityValidator quantityValidator;
    private static final long BALANCE = 10;
    private static final long POSITIVE_QUANTITY = 200;
    private static final long NEGATIVE_QUANTITY = -130;
    private static final int LINE_NUMBER = 1;

    @BeforeClass
    public static void setUp() {
        quantityValidator = new QuantityValidatorImpl();
    }

    @Test(expected = UnavailableQuantityException.class)
    public void isQuantityCorrectForPurchaseTest_Ok() {
        quantityValidator.isQuantityCorrectForPurchase(POSITIVE_QUANTITY, BALANCE);
    }

    @Test(expected = UnavailableQuantityException.class)
    public void isQuantityLessThanZeroTesst_Ok() {
        quantityValidator.isQuantityLessThanZero(NEGATIVE_QUANTITY, LINE_NUMBER);
    }
}
