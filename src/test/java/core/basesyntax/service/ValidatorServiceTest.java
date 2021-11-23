package core.basesyntax.service;

import static org.junit.Assert.assertTrue;

import core.basesyntax.service.impl.ValidatorServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorServiceTest {
    private static List<String> lines;
    private static ValidatorService validator;

    @BeforeClass
    public static void beforeClass() {
        lines = new ArrayList<>();
        validator = new ValidatorServiceImpl();
    }

    @Before
    public void beforeEachTest() {
        lines.add("type,fruit,quantity");
        lines.add("b,banana,60");
        lines.add("p,banana,10");
        lines.add("s,banana,5");
        lines.add("r,banana,5");
    }

    @Test (expected = RuntimeException.class)
    public void isValid_emptyList_notOk() {
        lines.clear();
        validator.isValid(lines);
    }

    @Test (expected = RuntimeException.class)
    public void isValid_emptyLine_notOk() {
        lines.add("");
        validator.isValid(lines);
    }

    @Test
    public void isValid_validLines_ok() {
        boolean actual = validator.isValid(lines);
        assertTrue(actual);
    }

    @Test (expected = RuntimeException.class)
    public void isValid_incorrectTypeActivity_notOk() {
        lines.add("t,apple,6");
        validator.isValid(lines);
    }

    @Test (expected = RuntimeException.class)
    public void isValid_incorrectNameFruit_notOk() {
        lines.add("b,app8e,6");
        validator.isValid(lines);
    }

    @Test (expected = RuntimeException.class)
    public void isValid_incorrectQuantity_notOk() {
        lines.add("b,apple,h");
        validator.isValid(lines);
    }

    @Test (expected = RuntimeException.class)
    public void isValid_negativeQuantity_notOk() {
        lines.add("b,apple,-6");
        validator.isValid(lines);
    }

    @Test (expected = RuntimeException.class)
    public void isValid_incorrectLine_notOk() {
        lines.add("b, ");
        validator.isValid(lines);
    }

    @After
    public void afterEachTest() {
        lines.clear();
    }
}
