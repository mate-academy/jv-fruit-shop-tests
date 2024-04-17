package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterCvsImplTest {
    private static final String TEMP_TEST_FILE_PATH =
            "src/test/resources/test_report.csv";
    private final FileWriter fileWriter = new FileWriterCvsImpl();

    @BeforeAll
    public static void initAll() {
        try {
            Files.writeString(Paths.get(TEMP_TEST_FILE_PATH), "");
        } catch (IOException e) {
            throw new RuntimeException("Error cleaning data in file: " + TEMP_TEST_FILE_PATH);
        }
    }

    @Test
    public void write_generalData_ok() {
        String expected = "sadfads fasdfas \n"
                + "sadfasdf adfa.234f";
        fileWriter.write(TEMP_TEST_FILE_PATH, expected);

        String actual = readFromFile(TEMP_TEST_FILE_PATH);

        assertEquals(expected, actual);
    }

    @Test
    public void write_empty_ok() {
        String expected = "";
        fileWriter.write(TEMP_TEST_FILE_PATH, expected);

        String actual = readFromFile(TEMP_TEST_FILE_PATH);

        assertEquals(expected, actual);
    }

    @Test
    public void write_null_notOk() {
        String expect = null;

        assertThrows(RuntimeException.class, () -> {
            fileWriter.write(TEMP_TEST_FILE_PATH, expect);
        });
    }

    @Test
    public void read_wrongPath_notOk() {
        String wrongPath = "src/test/resources/unExistTestFile.csv";
        File wrongFile = new File(wrongPath);

        String expected = "sadfads fasdfas \n"
                + "sadfasdf adfa.234f";
        fileWriter.write(wrongPath, expected);

        assertTrue(wrongFile.exists());
        assertEquals(expected, readFromFile(wrongPath));

        wrongFile.delete();
    }

    @Test
    public void read_nullPath_throwsException() {
        String wrongPath = null;

        assertThrows(RuntimeException.class, () -> {
            fileWriter.write(wrongPath, "");
        });
    }

    private String readFromFile(String path) {
        String text;
        try {
            text = Files.readString(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file by path: " + path, e);
        }
        return text;
    }
}
