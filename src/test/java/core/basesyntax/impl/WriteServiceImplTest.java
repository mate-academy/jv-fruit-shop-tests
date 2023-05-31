package core.basesyntax.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.services.WriteService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriteServiceImplTest {
    private static String OUTPUT_FILE_PATH;
    private static String WRONG_FILE_PATH;
    private static String CHECK_STRING;
    private static WriteService writeService;

    @BeforeClass
    public static void initialize_var() {
        OUTPUT_FILE_PATH = "src/test/java/resources/report";
        WRONG_FILE_PATH = "src/test/java/reoures/report";
        CHECK_STRING = "check string";
        writeService = new WriteServiceImpl();
    }

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
    public void writeToFile_WithWrongPath_notOk() {
        assertThrows(RuntimeException.class, () -> writeService
                .writeToFile(CHECK_STRING, WRONG_FILE_PATH));
    }
}
