package core.basesyntax.service.validator;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitValidatorTest {
    private static FruitValidator fruitValidator;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitValidator = new FruitValidator();
    }

    @Test(expected = RuntimeException.class)
    public void isFileEmpty_rawDataIsEmptyAndNull_NotOk() {
        List<String> testData = null;
        fruitValidator.isFileEmpty(testData);
        testData = new ArrayList<>();
        fruitValidator.isFileEmpty(testData);
    }

    @Test
    public void isFileEmpty_inputDataIsNotEmpty_Ok() {
        List<String> testData = new ArrayList<>();
        testData.add("Test text");
        assertTrue(fruitValidator.isFileEmpty(testData));
    }

    @Test(expected = RuntimeException.class)
    public void isDataCorrect_inputDataIsNotCorrect_NotOk() {
        List<String[]> testData = new ArrayList<>();
        testData.add(new String[] {"b", "test"});
        fruitValidator.isDataCorrect(testData);
    }

    @Test
    public void isDataCorrect_inputDataHasThreeElements_Ok() {
        List<String[]> testData = new ArrayList<>();
        testData.add(new String[] {"b", "apple", "10"});
        assertTrue(fruitValidator.isDataCorrect(testData));
    }
}
