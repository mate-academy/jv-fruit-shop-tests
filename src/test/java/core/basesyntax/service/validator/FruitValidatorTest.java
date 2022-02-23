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
    public void rawDataIsEmpty_Expected() {
        List<String> testData = null;
        fruitValidator.isFileEmpty(testData);
        testData = new ArrayList<>();
        fruitValidator.isFileEmpty(testData);
    }

    @Test
    public void inputDataIsNotEmpty_True() {
        List<String> testData = new ArrayList<>();
        testData.add("Test text");
        assertTrue(fruitValidator.isFileEmpty(testData));
    }

    @Test(expected = RuntimeException.class)
    public void inputDataIsNotCorrect_Exception() {
        List<String[]> testData = new ArrayList<>();
        testData.add(new String[] {"b", "test"});
        fruitValidator.isDataCorrect(testData);
    }

    @Test
    public void inputDataHasThreeElements_True() {
        List<String[]> testData = new ArrayList<>();
        testData.add(new String[] {"b", "apple", "10"});
        assertTrue(fruitValidator.isDataCorrect(testData));
    }
}
