package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.Collections;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceTest {
    private static final String VALID_FILE = "src/test/java/core/basesyntax/resources/"
                                                                + "reader/valid_data_file.csv";
    private static final String EMPTY_FILE = "src/test/java/core/basesyntax/resources/"
                                                                + "reader/empty_file.csv";
    private static final String NON_EXISTENT_FILE = "src/test/this_file_doesnt_exist.csv";
    private static ReaderService readerService;

    @BeforeClass
    public static void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFromFile_ValidFile_Ok() {
        List<String> expected = List.of("type,fruit,quantity",
                                        "b,banana,20",
                                        "b,apple,100",
                                        "s,banana,100",
                                        "p,banana,13",
                                        "r,apple,10",
                                        "p,apple,20",
                                        "p,banana,5",
                                        "s,banana,50");
        List<String> actual = readerService.readFromFile(VALID_FILE);
        assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_EmptyFile_Ok() {
        List<String> expected = Collections.EMPTY_LIST;
        List<String> actual = readerService.readFromFile(EMPTY_FILE);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_FileDoesntExist_NotOk() {
        readerService.readFromFile(NON_EXISTENT_FILE);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_EmptyFilePathString_NotOk() {
        readerService.readFromFile("");
    }

    @Test (expected = NullPointerException.class)
    public void readFromFile_NullFilePath_NotOk() {
        readerService.readFromFile(null);
    }
}
