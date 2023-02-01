package core.basesyntax.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.services.WriteService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Test;

public class WriteServiceImplTest {
    private static final String OUTPUT_FILE_PATH = "src/test/java/resources/report";
    private static final String WRONG_FILE_PATH = "src/test/java/reoures/report";
    private static final String CHECK_STRING = "check string";
    private final WriteService writeService = new WriteServiceImpl();

    @Test
    public void writeToFile_ok() {
        writeService.writeToFile(CHECK_STRING, OUTPUT_FILE_PATH);
        String actual;
        try {
            actual = Files.readString(Path.of(OUTPUT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Couldn`t read from file by + " + OUTPUT_FILE_PATH);
        }
        assertEquals(CHECK_STRING, actual);
    }

    @Test
    public void writeToFileWithWrongPath_notOk() {
        assertThrows(RuntimeException.class, () -> writeService
                .writeToFile(CHECK_STRING, WRONG_FILE_PATH));
    }

    @Test
    public void writeToFileWithNullPath_notOk() {
        assertThrows(NullPointerException.class, () -> writeService
                .writeToFile(CHECK_STRING, null));
    }
}
