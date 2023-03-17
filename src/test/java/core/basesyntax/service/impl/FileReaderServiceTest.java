package core.basesyntax.service.impl;

import core.basesyntax.service.FileReaderService;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceTest {
    private static FileReaderService fileReaderService;
    private static final File TEST_FILE = new File("src/test/resources/fileForFileReaderTest.csv");
    private static final File NON_EXISTENT_FILE = new File("src/test/resources/bandera.csv");
    private List<String> expected;

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Before
    public void before() {
        expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,durian,100");
        expected.add("b,papaya,55");
        expected.add("p,durian,28");
        expected.add("s,papaya,45");
    }

    @Test
    public void readFromFile_validInputData_ok() {
        List<String> actual = fileReaderService.readFromFile(TEST_FILE);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_nonExistentFile_notOk() {
        fileReaderService.readFromFile(NON_EXISTENT_FILE);
    }
}
