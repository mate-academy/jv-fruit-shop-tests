package core.basesyntax.service;

import static org.junit.Assert.assertTrue;

import core.basesyntax.service.impl.ValidationServiceImpl;
import java.util.function.Predicate;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidationServiceTest {
    private static Predicate<String> validationService;
    private static String line;

    @BeforeClass
    public static void setUp() {
        validationService = new ValidationServiceImpl();
    }

    @Test (expected = NullPointerException.class)
    public void test_NullLine_NotOk() {
        validationService.test(null);
    }

    @Test (expected = RuntimeException.class)
    public void test_EmptyLine_NotOk() {
        validationService.test("");
    }

    @Test (expected = RuntimeException.class)
    public void test_UnsupportedOperation_NotOk() {
        line = "yy,apple,100";
        validationService.test(line);
    }

    @Test (expected = RuntimeException.class)
    public void test_InvalidFruitName_NotOk() {
        line = "b,strange fruit,100";
        validationService.test(line);
    }

    @Test (expected = RuntimeException.class)
    public void test_FloatingPointQuantity_NotOk() {
        line = "b,orange,123.45";
        validationService.test(line);
    }

    @Test (expected = RuntimeException.class)
    public void test_NegativeQuantity_NotOk() {
        line = "b,orange,-123";
        validationService.test(line);
    }

    @Test
    public void test_OperationBalance_Ok() {
        line = "b,orange,123";
        assertTrue(validationService.test(line));
    }

    @Test
    public void test_OperationPurchase_Ok() {
        line = "p,orange,123";
        assertTrue(validationService.test(line));
    }

    @Test
    public void test_OperationSupply_Ok() {
        line = "s,orange,123";
        assertTrue(validationService.test(line));
    }

    @Test
    public void test_OperationReturn_Ok() {
        line = "r,orange,123";
        assertTrue(validationService.test(line));
    }
}
