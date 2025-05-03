package core.basesyntax.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriteService;
import core.basesyntax.service.WriteServiceImpl;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriteServiceImplTest {
    private static final String VALID_REPORT =
            "type,fruit,quantity" + System.lineSeparator()
                    + "b,banana,20" + System.lineSeparator()
                    + "b,apple,100" + System.lineSeparator();
    private static final String FILE_NAME_CORRECT =
            "src/test/resources/FileToWrite.csv";
    private static final String FILE_NAME_INVALID = "this/path/don`t/exist";
    private static WriteService writeService;

    @BeforeClass
    public static void beforeClass() {
        writeService = new WriteServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void writeData_reportNull_notOk() {
        writeService.writeToFile(null, FILE_NAME_CORRECT);
    }

    @Test(expected = RuntimeException.class)
    public void writeData_fileNameNull_notOk() {
        writeService.writeToFile(VALID_REPORT, null);
    }

    @Test(expected = RuntimeException.class)
    public void writeData_invalidFilePath_notOk() {
        writeService.writeToFile(VALID_REPORT, FILE_NAME_INVALID);
    }

    @Test
    public void writeData_allFieldCorrect_ok() {
        writeService.writeToFile(VALID_REPORT, FILE_NAME_CORRECT);
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader(FILE_NAME_CORRECT))) {
            String text = bufferedReader.readLine();
            while (text != null) {
                stringBuilder.append(text).append(System.lineSeparator());
                text = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Can`t read data from file " + FILE_NAME_CORRECT, e);
        }
        String actual = stringBuilder.toString();
        assertEquals(VALID_REPORT, actual);
    }
}
