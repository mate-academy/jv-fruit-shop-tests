package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderCvsImplTest {
    private static final String TEMP_TEST_FILE_PATH =
            "src/test/resources/test_activities.csv";
    private final FileReader fileReader = new FileReaderCvsImpl();

    @BeforeAll
    public static void initAll() {
        try {
            Files.writeString(Paths.get(TEMP_TEST_FILE_PATH), "");
        } catch (IOException e) {
            throw new RuntimeException("Error cleaning data in file: " + TEMP_TEST_FILE_PATH);
        }
    }

    @Test
    public void read_generalData_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("sfas");
        expected.add("adffas");
        expected.add("dfas");
        expected.add("fwas");
        expected.add("fawf");

        writeDataInFile(convertListToString(expected));

        List<String> actual = fileReader.read(TEMP_TEST_FILE_PATH);

        assertEquals(expected, actual);
    }

    @Test
    public void read_generalDataWithEmptyLine_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("sfas");
        expected.add("");
        expected.add("dfas");
        expected.add("");
        expected.add("fawf");

        writeDataInFile(convertListToString(expected));

        List<String> actual = fileReader.read(TEMP_TEST_FILE_PATH);

        assertEquals(expected, actual);
    }

    @Test
    public void read_empty_ok() {
        List<String> expected = new ArrayList<>();

        writeDataInFile(convertListToString(expected));

        List<String> actual = fileReader.read(TEMP_TEST_FILE_PATH);

        assertEquals(expected, actual);
    }

    @Test
    public void read_wrongPath_throwsException() {
        String wrongPath = "src/test/java/core/basesyntax/resources/unExistTestFile.csv";

        assertThrows(RuntimeException.class, () -> {
            fileReader.read(wrongPath);
        });
    }

    @Test
    public void read_nullPath_throwsException() {
        assertThrows(RuntimeException.class, () -> {
            fileReader.read(null);
        });
    }

    private void writeDataInFile(String text) {
        try (BufferedWriter buf = new BufferedWriter(
                new java.io.FileWriter(TEMP_TEST_FILE_PATH, false))) {
            buf.write(text);
        } catch (IOException e) {
            throw new RuntimeException("Can't read file by path: " + TEMP_TEST_FILE_PATH, e);
        }
    }

    private String convertListToString(List<String> list) {
        return list.stream().reduce((a, b) -> a + "\n" + b).orElse("");
    }
}
