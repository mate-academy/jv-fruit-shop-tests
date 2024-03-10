package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriteServiceToCscImplTest {
    private static final String CORRECT_PATH = "src\\main\\java\\core\\basesyntax\\file.csv";
    private static final String CONTENT = "type,fruit,quantity";
    private static WriteService writeService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        writeService = new WriteServiceToCscImpl();
    }

    @Test
    public void fileWriteWithCorrectInput_Ok() {
        writeService.writeToFile(CORRECT_PATH, CONTENT);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(CORRECT_PATH))) {
            String actual = bufferedReader.readLine();
            assertEquals(CONTENT, actual);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
