package core.basesyntax.fileservice;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Test;

public class WriteServiceImplTest {
    private static final String INVALID_FILE_PATH = "";
    private static final String FILE_PATH = "src/test/java/resources/text123.csv";
    private static final String REPORT = "src/test/java/resources/text_check.csv";
    private static final WriteServiceImpl WRITE_SERVICE = new WriteServiceImpl();

    @Test
    public void write_validPath_Ok() {
        WRITE_SERVICE.writeToFile("fruit\n"
                + "quantity", FILE_PATH);
        try {
            assertEquals(Files.readAllLines(Path.of(REPORT)),
                    Files.readAllLines(Path.of(FILE_PATH)));
        } catch (IOException e) {
            throw new RuntimeException("Can't read the file...", e);
        }
    }

    @Test(expected = RuntimeException.class)
    public void write_invalidPath_NotOk() {
        WRITE_SERVICE.writeToFile("fruit\n"
                + "quantity", INVALID_FILE_PATH);
    }
}
