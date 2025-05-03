package core.basesyntax.shopservice;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String DATA = "Hello" + System.lineSeparator()
            + "mates" + 47 + System.lineSeparator() + "!";
    private static final String FILE_PATH_ACTUAL
            = "src/test/resources/testFileWriterServiceActual.csv";
    private static final String INVALID_FILE_PATH_ACTUAL = "src/test/resources/InvalidPath.csv";
    private static final String FILE_PATH_EXPECTED
            = "src/test/resources/testFileWriterServiceExpected.csv";

    @Test
    public void writeToFile_ExistingPath_Ok() {
        new FileWriterServiceImpl().writeToFile(DATA, FILE_PATH_ACTUAL);
        try {
            assertEquals(Files.readAllLines(Path.of(FILE_PATH_EXPECTED)),
                    Files.readAllLines(Path.of(FILE_PATH_ACTUAL)));
        } catch (IOException e) {
            throw new RuntimeException("Can't write to path " + "\"" + FILE_PATH_ACTUAL + "\"", e);
        }
    }

    @Test
    public void writeToFile_NonExistingFile_Ok() {
        new FileWriterServiceImpl().writeToFile(DATA, INVALID_FILE_PATH_ACTUAL);
        try {
            assertEquals(Files.readAllLines(Path.of(FILE_PATH_EXPECTED)),
                    Files.readAllLines(Path.of(INVALID_FILE_PATH_ACTUAL)));
        } catch (IOException e) {
            throw new RuntimeException("Can't write to path " + "\""
                    + INVALID_FILE_PATH_ACTUAL + "\"", e);
        }
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_EmptyPath_Bad() {
        new FileWriterServiceImpl().writeToFile(DATA, "");
    }
}
