package core.basesyntax.service;

import core.basesyntax.service.impl.ValidationServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.function.Predicate;

public class ValidationServiceTest {
    private static Predicate<String> validationService;
    private static String dataLine;

    @BeforeClass
    public static void setUp() {
        validationService = new ValidationServiceImpl();
    }

    @Test (expected = NullPointerException.class)
    public void test_NullDataLine_NotOk() {
        validationService.test(null);
    }

    @Test (expected = RuntimeException.class)
    public void test_EmptyDataLine_NotOk() {
        validationService.test("");
    }

    @Test (expected = RuntimeException.class)
    public void test_InvalidPatternDataLine_NotOk() {
        dataLine = "this_is_invalid_pattern";
        validationService.test(dataLine);
    }

    @Test (expected = RuntimeException.class)
    public void test_UnsupportedOperationDataLine_NotOk() {
        dataLine = "y,apple,100";
        validationService.test(dataLine);
    }

    @Test (expected = RuntimeException.class)
    public void test_UnsupportedOperationDataLine_NotOk() {
        dataLine = "y,apple,100";
        validationService.test(dataLine);
    }
}
