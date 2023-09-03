package core.basesyntax.dao;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.File;
import org.junit.jupiter.api.Test;

class WriteDataToFileImplTest {
    private static final String FILE_PATH = "src/test/resources/Report.csv";
    private static final String WRONG_FILE_PATH = "src/main/main/resources/Report.csv";

    private WriteDataToFile writeDataToFile = new WriteDataToFileImpl();
    private String report = "fruit,quantity" + System.lineSeparator() + "banana,152"
            + System.lineSeparator() + "apple,90";

    @Test
    void writeDataToFile_correctData_Ok() {
        writeDataToFile.writeDataToFile(report, FILE_PATH);
        File file = new File(FILE_PATH);
        boolean actual = file.exists();
        assertTrue(actual);
    }

    @Test
    void writeDataToFile_notExistingPath_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            writeDataToFile.writeDataToFile(report, WRONG_FILE_PATH);
        });
    }

    @Test
    void writeDataToFile_nullReport_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            writeDataToFile.writeDataToFile(null, WRONG_FILE_PATH);
        });
    }

    @Test
    void writeDataToFile_nullPath_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            writeDataToFile.writeDataToFile(report, null);
        });
    }
}
