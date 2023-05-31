package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.FileService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileServiceImplTest {
    private static final String TRANSACTION_FILE = "src/test/resources/transaction.csv";
    private static final String EMPTY_FILE = "src/test/resources/empty.csv";
    private static final String REPORT_FILE = "src/test/resources/report.csv";
    private static final String FILE_NOT_EXIST = "test/notExist.csv";
    private static FileService fileService;
    private String expected;

    @BeforeClass
    public static void beforeClass() {
        fileService = new FileServiceImpl();
    }

    @Before
    public void setUp() {
        expected = "b,banana,20";
    }

    @Test
    public void readFromFile_ok() {
        List<String> list = fileService.readFromFile(TRANSACTION_FILE);
        String actual = list.get(0);
        assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_emptyFile_ok() {
        List<String> list = fileService.readFromFile(EMPTY_FILE);
        assertTrue(list.isEmpty());
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_notExistingFile_notOk() {
        fileService.readFromFile(FILE_NOT_EXIST);
    }

    @Test
    public void writeToFile_ok() {
        fileService.writeToFile(expected, REPORT_FILE);
        List<String> list;
        try {
            list = Files.readAllLines(Path.of(REPORT_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String actual = list.get(0);
        assertEquals(expected, actual);
    }

    @Test
    public void writeToFile_nullData_notOk() {
        boolean actual = fileService.writeToFile(null, REPORT_FILE);
        assertFalse(actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_notExistingFile_notOk() {
        fileService.writeToFile(expected, FILE_NOT_EXIST);
    }
}
