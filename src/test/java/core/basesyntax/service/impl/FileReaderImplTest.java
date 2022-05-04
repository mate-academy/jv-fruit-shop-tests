package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileReaderImplTest {
    private static FileReader fileReader;
    private static final String LEGIT_PATH = "src/main/resources/inputData.csv";
    private static final String INVALID_PATH = "src/main/resources/invalidData.csv";
    private static final String EMPTY_FILE_PATH = "src/main/resources/emptyData.csv";

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void read_EmptyFilePath_Ok() {
        List<String> actual = fileReader.read(EMPTY_FILE_PATH);
        assertTrue(actual.isEmpty());
    }

    @Test
    public void read_ProperFilePath_Ok() {
        try {
            List<String> stringsExpected = Files.readAllLines(Paths.get(LEGIT_PATH));
            List<String> stringsActual = fileReader.read(LEGIT_PATH);
            assertFalse(stringsActual.isEmpty());
            assertEquals(stringsExpected, stringsActual);
        } catch (IOException e) {
            throw new RuntimeException("Test reader failed", e);
        }
    }

    @Test
    public void read_InvalidFilePath_NotOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Can`t read from file in FileReaderImpl from " + INVALID_PATH);
        fileReader.read(INVALID_PATH);
    }
}
