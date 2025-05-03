package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static FileReader fileReader;
    private static final String LEGIT_PATH = "src/main/resources/inputData.csv";
    private static final String INVALID_PATH = "src/main/resources/invalidData.csv";
    private static final String EMPTY_FILE_PATH = "src/main/resources/emptyData.csv";

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void read_emptyFilePath_Ok() {
        List<String> actual = fileReader.read(EMPTY_FILE_PATH);
        assertTrue(actual.isEmpty());
    }

    @Test
    public void read_properFilePath_Ok() {
        List<String> stringsExpected = testRead(LEGIT_PATH);
        List<String> stringsActual = fileReader.read(LEGIT_PATH);
        assertEquals(stringsExpected, stringsActual);
    }

    @Test(expected = RuntimeException.class)
    public void read_invalidFilePath_NotOk() {
        fileReader.read(INVALID_PATH);
    }

    private List<String> testRead(String path) {
        try {
            return Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException("Test reader failed", e);
        }
    }
}
