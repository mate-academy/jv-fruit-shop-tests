package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.exception.ValidationDataException;
import core.basesyntax.services.WriteFileService;
import core.basesyntax.services.impl.WriteFileServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class WriteFileTest {
    private static WriteFileService writeFileServiceTest;

    @BeforeAll
    static void createWriteFileService() {
        writeFileServiceTest = new WriteFileServiceImpl();
    }

    @Test
    void writeFile_nullData_notOk() {
        assertThrows(ValidationDataException.class,
                () -> writeFileServiceTest.writeToFile(null, "path"));
    }

    @Test
    void writeFile_emptyData_notOk() {
        assertThrows(ValidationDataException.class,
                () -> writeFileServiceTest.writeToFile(new String[0], "path"));
    }

    @Test
    void writeFile_nullPath_notOk() {
        String[] result = {"test", "test"};
        assertThrows(ValidationDataException.class,
                () -> writeFileServiceTest.writeToFile(result, null));
    }

    @Test
    void writeFile_emptyPath_notOk() {
        String[] result = {"test", "test"};
        assertThrows(ValidationDataException.class,
                () -> writeFileServiceTest.writeToFile(result, ""));
    }

    @Test
    void writeFile_correctTest_ok() {
        String[] result = {"test", "test"};
        boolean actual = writeFileServiceTest
                .writeToFile(result,
                        "resources/testFiles/outputFileTestWriteDone.csv");
        assertTrue(actual);
    }
}
