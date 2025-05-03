package core.basesyntax.file;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import exception.FileException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String TEST_FILE_NAME = "src/main/resources/testWrite.csv";
    private static final String NON_EXISTENT_FILE_NAME = "scr/java/nonExistent.csv";
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
            fileWriter.write(TEST_FILE_NAME, DATA);
            expected = true;
        } catch (FileException e) {
            throw new FileException("Can't write data to file: " + TEST_FILE_NAME, e);
        }
        assertTrue(expected);
    }

    @Test
    void write_nullData_NotOk() {
        assertThrows(NullPointerException.class,
                () -> fileWriter.write(TEST_FILE_NAME, null),
                "Report can't be null");
    }

    @Test
    void write_toNonExistentFile_NotOk() {
        assertThrows(FileException.class,
                () -> fileWriter.write(NON_EXISTENT_FILE_NAME, DATA),
                "Path to file is wrong: " + NON_EXISTENT_FILE_NAME);
    }
}
