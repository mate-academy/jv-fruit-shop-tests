package core.basesyntax.service.files;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ValidatorImplTest {
    private Validator validator = new ValidatorImpl();
    private List<String> testList;

    @Test
    public void isValid_validInput_ok() {
        testList = new ArrayList<>();
        testList.add("type,fruit,quantity");
        testList.add("b,banana,20");
        testList.add("p,apple,100");
        testList.add("r,apple,100");
        testList.add("s,apple,100");
        boolean actual = validator.isValid(testList);
        assertTrue("Method should return true for valid input", actual);
    }

    @Test
    public void isValid_invalidInputWithoutFirstLine_notOk() {
        testList = new ArrayList<>();
        testList.add("b,banana,20");
        testList.add("b,apple,100");
        assertThrows("Method should throw RuntimeExeption for input without title",
                RuntimeException.class, () -> validator.isValid(testList));
    }

    @Test
    public void isValid_invalidInputWithInvalidOper_notOk() {
        testList = new ArrayList<>();
        testList.add("type,fruit,quantity");
        testList.add("b,banana,20");
        testList.add("a,apple,100");
        assertThrows("Method should throw RuntimeExeption for invalid values",
                RuntimeException.class, () -> validator.isValid(testList));
    }

    @Test
    public void isValid_invalidInputWithInvalidAmount_notOk() {
        testList = new ArrayList<>();
        testList.add("type,fruit,quantity");
        testList.add("b,banana,20");
        testList.add("b,apple,-100");
        assertThrows("Method should throw RuntimeExeption for invalid values",
                RuntimeException.class, () -> validator.isValid(testList));
    }

    @Test
    public void isValid_invalidInputWithEmptyValue_notOk() {
        testList = new ArrayList<>();
        testList.add("type,fruit,quantity");
        testList.add("b,banana,20");
        testList.add("a,apple,");
        assertThrows("Method should throw RuntimeExeption for invalid values",
                RuntimeException.class, () -> validator.isValid(testList));
    }

    @Test
    public void isValid_invalidInputWithInvalidLength_notOk() {
        testList = new ArrayList<>();
        testList.add("type,fruit,quantity");
        testList.add("b,banana,20");
        testList.add("b,apple,100,a");
        assertThrows("Method should throw RuntimeExeption for invalid values",
                RuntimeException.class, () -> validator.isValid(testList));
    }
}
