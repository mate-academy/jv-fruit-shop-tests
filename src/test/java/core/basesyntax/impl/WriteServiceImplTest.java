package core.basesyntax.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.WriteService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class WriteServiceImplTest {
    private static final String NULL_REPORT = null;
    private static final String FILE_NAME_TO_NULL = null;
    private static final String VALID_REPORT =
            "type,fruit,quantity" + System.lineSeparator()
                    + "b,banana,20" + System.lineSeparator()
                    + "b,apple,100" + System.lineSeparator();
    private static final String FILE_NAME_CORRECT =
            "src/test/java/core/basesyntax/impl/resources/FileToWrite.csv";
    private static final WriteService writeService = new WriteServiceImpl();

    @Test
    void reportNull_notOk() {
        assertThrows(RuntimeException.class, () ->
                writeService.writeToFile(NULL_REPORT, FILE_NAME_CORRECT));
    }

    @Test
    void fileNameNull_notOk() {
        assertThrows(RuntimeException.class, () ->
                writeService.writeToFile(VALID_REPORT, FILE_NAME_TO_NULL));
    }

    @Test
    void allFieldCorrect_ok() {
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
