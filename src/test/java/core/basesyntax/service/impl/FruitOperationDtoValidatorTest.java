package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.Validator;
import java.util.LinkedList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitOperationDtoValidatorTest {
    private static final String CORRECT_DATA = "b,apple,10";
    private static final String INCOMPLETE_DATA = "b,10";
    private static final String DATA_WITH_INCORRECT_QUANTITY = "b,apple,-10";
    private static final String NULL_DATA = null;
    private static Validator<String> validator;
    private static List<String> dataFromFile;

    @BeforeClass
    public static void initialize() {
        validator = new FruitOperationDtoValidator();
        dataFromFile = new LinkedList<>();
    }

    @Test
    public void fruitOperationDtoValidator_getCorrectData_Ok() {
        dataFromFile.add(CORRECT_DATA);
        boolean actual = validator.validate(dataFromFile);
        Assert.assertTrue(actual);
        dataFromFile.clear();
    }

    @Test
    public void fruitOperationDtoValidator_getIncompleteData_notOk() {
        dataFromFile.add(INCOMPLETE_DATA);
        assertThrows(RuntimeException.class, () -> validator.validate(dataFromFile));
        dataFromFile.clear();
    }

    @Test
    public void fruitOperationDtoValidator_getDataWithIncorrectQuantity_notOk() {
        dataFromFile.add(DATA_WITH_INCORRECT_QUANTITY);
        assertThrows(RuntimeException.class, () -> validator.validate(dataFromFile));
        dataFromFile.clear();
    }

    @Test
    public void fruitOperationDtoValidator_getNullData_notOk() {
        dataFromFile.add(NULL_DATA);
        assertThrows(RuntimeException.class, () -> validator.validate(dataFromFile));
        dataFromFile.clear();
    }
}
