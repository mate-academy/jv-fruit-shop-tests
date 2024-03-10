package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReadServiceFromCsvImplTest {
    private static final String CORRECT_ADDRESS = "src\\main\\java\\core\\basesyntax\\file.csv";
    private static final String INCORRECT_ADDRESS = "User\\IliaSytnik"
            + "\\src\\main\\java\\core\\basesyntax\\file.csv";
    private static ReadService readService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        readService = new ReadServiceFromCsvImpl();
    }

    @Test
    public void readFileWithCorrectInput_Ok() {
        List<String> expected;
        try {
            expected = Files.readAllLines(Path.of(CORRECT_ADDRESS));
        } catch (IOException e) {
            throw new RuntimeException("Cant read file" + CORRECT_ADDRESS);
        }
        List<String> actual = readService.readFile(CORRECT_ADDRESS);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFileWithIncorrectInput_NotOk() {
        readService.readFile(INCORRECT_ADDRESS);
    }
}
