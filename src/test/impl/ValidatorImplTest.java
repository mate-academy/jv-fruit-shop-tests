package core.basesyntax.service.file.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.model.CsvLineDto;
import core.basesyntax.service.file.Validator;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorImplTest {
    private static Validator validator;
    private static List<CsvLineDto> validData;
    private static List<CsvLineDto> invalidOperation;
    private static List<CsvLineDto> invalidFruitName;
    private static List<CsvLineDto> invalidNumber;

    @BeforeClass
    public static void beforeClass() {
        validator = new ValidatorImpl();
        validData = List.of(
                new CsvLineDto("b","banana", "20"),
                new CsvLineDto("s","banana", "100"),
                new CsvLineDto("p","banana", "13"),
                new CsvLineDto("r","banana", "10")
        );
        invalidOperation = List.of(
                new CsvLineDto("80","banana", "100")
        );
        invalidFruitName = List.of(
                new CsvLineDto("p","@!&^*#", "13")
        );
        invalidNumber = List.of(
                new CsvLineDto("p","banana", "as1213")
        );
    }

    @Test
    public void checkFileData_ValidData_Ok() {
        assertTrue(validator.checkFileData(validData));
    }

    @Test(expected = RuntimeException.class)
    public void checkFileData_InvalidOperation_NotOk() {
        validator.checkFileData(invalidOperation);
    }

    @Test(expected = RuntimeException.class)
    public void checkFileData_InvalidFruitName_NotOk() {
        validator.checkFileData(invalidFruitName);
    }

    @Test(expected = RuntimeException.class)
    public void checkFileData_InvalidNumber_NotOk() {
        validator.checkFileData(invalidNumber);
    }
}
