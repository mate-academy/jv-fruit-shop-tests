package core.basesyntax.service;

import core.basesyntax.service.impl.FileReaderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

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
        String expected = """
                type,fruit,quantity
                b,banana,20
                b,apple,100
                s,banana,100
                p,banana,13
                r,apple,10
                p,apple,20
                p,banana,5
                s,banana,50
                """;
        System.out.println(expected);
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
