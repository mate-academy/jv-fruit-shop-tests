package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReaderService;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String FILE_PATH = "src" + File.separator
            + "test" + File.separator
            + "resources" + File.separator
            + "test.csv";
    private static final List<String> EXPECTED_DATA
            = List.of("First string",
            "Second string",
            "Third string");
    private static final String TEST_DATA
            = "First string" + System.lineSeparator()
            + "Second string" + System.lineSeparator()
            + "Third string";
    private static ReaderService readerService;

    @BeforeClass
    public static void beforeClass() {
        readerService = new ReaderServiceImpl();
    }

    @Before
    public void setUp() {
        writToFile();
    }

    @Test
    public void readerService_equalsReadDataFromFile_Ok() {
        List<String> actual = readerService.readFromFile(FILE_PATH);
        assertEquals(EXPECTED_DATA, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readerService_invalidPath_NotOk() {
        readerService.readFromFile("Invalid path");
    }

    @Test(expected = RuntimeException.class)
    public void readerService_nullFilePath_NotOk() {
        readerService.readFromFile(null);
    }

    private void writToFile() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_PATH))) {
            bufferedWriter.write(TEST_DATA);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file: " + FILE_PATH, e);
        }
    }
}
