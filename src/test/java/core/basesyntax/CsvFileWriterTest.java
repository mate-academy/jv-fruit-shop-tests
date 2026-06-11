package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import core.basesyntax.service.FileWriter;
import core.basesyntax.service.impl.CsvFileWriterImpl;
import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvFileWriterTest {
    private static final String FILE_PATH = "src/test/resources/finalReport.csv";
    private static final String FILE_CONTENT = "type,fruit,quantity\n" + "b,banana,20";
    private static FileWriter writer;

    @BeforeAll
    public static void setUp() {
        writer = new CsvFileWriterImpl();
    }

    @BeforeEach
    public void beforeEach() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void write_NullPath_NotOk() {
        assertThrows(NullPointerException.class, () -> writer.write(null, FILE_CONTENT));
    }

    @Test
    public void write_NullContent_NotOk() {
        assertThrows(NullPointerException.class, () -> writer.write(FILE_PATH, null));
    }

    @Test
    public void write_EmptyPath_NotOk() {
        assertThrows(RuntimeException.class, () -> writer.write("", FILE_CONTENT));
    }

    @Test
    public void write_EmptyContent_Ok() {
        assertDoesNotThrow(() -> writer.write(FILE_PATH, ""));
        File file = new File(FILE_PATH);
        assertTrue(file.exists());
        assertDoesNotThrow(() -> {
            String text = Files.readString(file.toPath());
            assertTrue(text.isEmpty());
        });
        file.delete();
    }

    @Test
    public void write_Complete_Ok() {
        assertDoesNotThrow(() -> writer.write(FILE_PATH, FILE_CONTENT));
        List<String> expected = Arrays.asList(FILE_CONTENT.split(System.lineSeparator()));
        File file = new File(FILE_PATH);
        assertTrue(file.exists());
        assertDoesNotThrow(() -> {
            List<String> actual = Files.readAllLines(file.toPath());
            assertEquals(expected, actual);
        });
        file.delete();
    }

    @Test
    public void write_ReadOnly_NotOk() {
        File file = new File(FILE_PATH);
        assertDoesNotThrow(() -> file.createNewFile());
        file.setReadOnly();
        assertThrows(RuntimeException.class, () -> writer.write(FILE_PATH, FILE_CONTENT));
        file.delete();
    }
}
