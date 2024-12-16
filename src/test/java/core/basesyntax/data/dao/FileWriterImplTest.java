package core.basesyntax.data.dao;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.data.exeption.FileException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileWriterImplTest {
    private static final String TEST_FILE_NAME = "src/resources/testWrite.csv";
    private static final String DATA = "apple,40";
    private static FileWriter fileWriter;

    @BeforeAll
    static void beforeAll() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void write_toExistentFile_Ok() {
        boolean expected = false;
        try {
            fileWriter.writeFile(TEST_FILE_NAME, DATA);
            expected = true;
        } catch (FileException e) {
            throw new FileException("Can't write data to file: " + TEST_FILE_NAME, e);
        }
        assertTrue(expected);
    }

    @Test
    void write_nullData_NotOk() {
        assertThrows(NullPointerException.class,
                () -> fileWriter.writeFile(TEST_FILE_NAME, null),
                "Report can't be null");
    }
}
