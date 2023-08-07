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
    void writeFile_isDataIsNull_notOk() {
        assertThrows(ValidationDataException.class,
                () -> writeFileServiceTest.writeToFile(null, "path"));
    }

    @Test
    void writeFile_isDataIsEmpty_notOk() {
        assertThrows(ValidationDataException.class,
                () -> writeFileServiceTest.writeToFile(new String[0], "path"));
    }

    @Test
    void writeFile_isPathIsNull_notOk() {
        String[] result = {"test", "test"};
        assertThrows(ValidationDataException.class,
                () -> writeFileServiceTest.writeToFile(result, null));
    }

    @Test
    void writeFile_isIsEmpty_notOk() {
        String[] result = {"test", "test"};
        assertThrows(ValidationDataException.class,
                () -> writeFileServiceTest.writeToFile(result, ""));
    }

    @Test
    void writeFile_isCorrect_ok() {
        String[] result = {"test", "test"};
        boolean actual = writeFileServiceTest
                .writeToFile(result,
                        "resources/testFiles/outputFileTestWriteDone.csv");
        assertTrue(actual);
    }
}
