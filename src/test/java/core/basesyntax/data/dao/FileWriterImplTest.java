package core.basesyntax.data.dao;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.data.exeption.FileException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileWriterImplTest {
    private static final String TEST_FILE_NAME = "java/resources/testWrite.csv";
    private static final String DATA = "apple,40";
    private static final String NON_EXISTENT_FILE_NAME = "scr/java/nonExistent.csv";
    private static FileWriter fileWriter;

    @BeforeAll
    static void beforeAll() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void write_toNonExistentFile_NotOk() {
        assertThrows(FileException.class,
                () -> fileWriter.writeFile(NON_EXISTENT_FILE_NAME, DATA),
                "Path to file is wrong: " + NON_EXISTENT_FILE_NAME);
    }

    @Test
    void write_nullData_NotOk() {
        assertThrows(NullPointerException.class,
                () -> fileWriter.writeFile(TEST_FILE_NAME, null),
                "Report can't be null");
    }
}
