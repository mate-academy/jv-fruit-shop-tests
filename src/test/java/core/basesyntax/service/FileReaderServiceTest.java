package core.basesyntax.service;

import core.basesyntax.service.impl.FileReaderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileReaderServiceTest {
    private static final String CORRECT_INPUT_FILE_NAME = "src/main/resources/input.csv";
    private static final String WRONG_INPUT_FILE_NAME = "53hsd59.csv";
    private static FileReaderService fileReaderService;

    @BeforeAll
    public static void setUp() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void read_correctInputData_Ok() {
        StringBuilder inputString = new StringBuilder("type,fruit,quantity");
        inputString.append(System.lineSeparator()).append("b,banana,20")
                .append(System.lineSeparator()).append("b,apple,100")
                .append(System.lineSeparator()).append("s,banana,100")
                .append(System.lineSeparator()).append("p,banana,13")
                .append(System.lineSeparator()).append("r,apple,10")
                .append(System.lineSeparator()).append("p,apple,20")
                .append(System.lineSeparator()).append("p,banana,5")
                .append(System.lineSeparator()).append("s,banana,50")
                .append(System.lineSeparator());
        String expected = inputString.toString();
        String actual = fileReaderService.readInputData(CORRECT_INPUT_FILE_NAME);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void read_nullInputData_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fileReaderService.readInputData(null));
    }

    @Test
    public void read_incorrectInputData_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fileReaderService.readInputData(WRONG_INPUT_FILE_NAME));
    }
}
