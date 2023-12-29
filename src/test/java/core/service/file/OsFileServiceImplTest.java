package core.service.file;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OsFileServiceImplTest {
    private static final String PATH_INPUT = "src/test/resources/report_input.csv";
    private static final String PATH_TO_EMPTY_FILE = "src/test/resources/empty_file.csv";
    private static final String PATH_WRONG = "src/test/rjkdf9f9/empty_file.csv";
    private static final String PATH_EMPTY = "";
    private static FileService fileService;
    private static List<String> expected;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fileService = new OsFileServiceImpl();
        expected = new ArrayList<>();
    }

    @Before
    public void setUp() throws Exception {
        fileService = new OsFileServiceImpl();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
    }

    @Test
    public void readFile_Ok() {
        List<String> actualReadFile = fileService.readFile(PATH_INPUT);
        Assert.assertEquals(expected, actualReadFile);
    }

    @Test(expected = RuntimeException.class)
    public void readFileFromEmptyFile_NotOk() {
        List<String> actualReadFile = fileService.readFile(PATH_TO_EMPTY_FILE);
        Assert.assertEquals(expected, actualReadFile);
    }

    @Test(expected = RuntimeException.class)
    public void readFileFromWrongPath_NotOk() {
        List<String> actualReadFile = fileService.readFile(PATH_WRONG);
        Assert.assertEquals(expected, actualReadFile);
    }

    @Test(expected = RuntimeException.class)
    public void readFileFromEmptyPath_NotOk() {
        List<String> actualReadFile = fileService.readFile(PATH_EMPTY);
        Assert.assertEquals(expected, actualReadFile);
    }

    @After
    public void tearDown() throws Exception {
        expected.clear();
    }
}
