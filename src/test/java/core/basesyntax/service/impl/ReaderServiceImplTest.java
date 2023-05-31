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
    private static final String TEST_DATA
            = "b,banana,20" + System.lineSeparator()
            + "b,apple,100" + System.lineSeparator()
            + "s,banana,100" + System.lineSeparator()
            + "p,banana,13" + System.lineSeparator()
            + "r,apple,10" + System.lineSeparator()
            + "p,apple,20" + System.lineSeparator()
            + "p,banana,5" + System.lineSeparator()
            + "s,banana,50";
    private static final List<String> EXPECTED_DATA
            = List.of("b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50");
    private static ReaderService readerService;

    @BeforeClass
    public static void beforeClass() {
        readerService = new ReaderServiceImpl();
    }

    @Before
    public void setUp() {
        writeToFile();
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

    private void writeToFile() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_PATH))) {
            bufferedWriter.write(TEST_DATA);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file: " + FILE_PATH, e);
        }
    }
}
