package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReaderServiceImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceTest {
    private static final String FILE_NAME = "src/test/resources/input.csv";
    private static final String EMPTY_FILE_NAME = "src/test/resources/emptyFile.csv";
    private static final String WRONG_FILE_NAME = "not_exist.csv";
    private static File testFile;
    private final ReaderServiceImpl readerService = new ReaderServiceImpl();

    @BeforeClass
    public static void setUpBeforeClass() {
        testFile = new File(FILE_NAME);
        try {
            testFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @After
    public void cleanStorageAfterEach() {
        Storage.STORAGE.clear();
    }

    @Test
    public void readInfoFromFile_ok() {
        List<String> expected;
        try {
            expected = Files.readAllLines(testFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<String> actual = readerService.readFileToList(testFile);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readInfoFromFile_notExistFile_notOk() {
        File file = new File(WRONG_FILE_NAME);
        readerService.readFileToList(file);
    }

    @Test
    public void readInfoFromFile_emptyFile_notOk() {
        List<String> expected = new ArrayList<>();
        File file = new File(EMPTY_FILE_NAME);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<String> actual = readerService.readFileToList(file);
        assertEquals("Test failed, List must be empty!", expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readInfoFromFile_fileIsNull_notOk() {
        readerService.readFileToList(null);
        fail("The file cannot be null!");
    }
}
