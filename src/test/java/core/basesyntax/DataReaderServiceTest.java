package core.basesyntax;

import core.basesyntax.service.DataReaderService;
import core.basesyntax.service.impl.DataReaderServiceImpl;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataReaderServiceTest {
    private static final List<String> EMPTY_LIST_RESULT = new ArrayList<>(0);
    private static final List<String> RESULT_FROM_FIRST_FILE =
            List.of("type,fruit,quantity", "b,orange,20", "b,pineapple,10",
            "s,orange,10", "p,orange,13", "r,apple,20", "p,pineapple,5", "s,pineapple,50");
    private static final List<String> RESULT_FROM_SECOND_FILE =
            List.of("Hello, world!", "Hello, mate!");
    private static final String PATH_TO_EMPTY_FILE = "src/test/resources/emptyFile.csv";
    private static final String PATH_TO_FIRST_TEST_FILE = "src/test/resources/test1.csv";
    private static final String PATH_TO_SECOND_TEST_FILE = "src/test/resources/test2.csv";
    private static final String PATH_TO_THIRD_INVALID_FILE = "src/test/resources/test3";
    private static DataReaderService dataReaderService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        dataReaderService = new DataReaderServiceImpl();
        Files.write(Path.of(PATH_TO_EMPTY_FILE), "".getBytes(StandardCharsets.UTF_8));
        Files.write(Path.of(PATH_TO_FIRST_TEST_FILE), ("type,fruit,quantity\n"
                + "b,orange,20\n" + "b,pineapple,10\n" + "s,orange,10\n"
                + "p,orange,13\n" + "r,apple,20\n" + "p,pineapple,5\n"
                + "s,pineapple,50\n").getBytes(StandardCharsets.UTF_8));
        Files.write(Path.of(PATH_TO_SECOND_TEST_FILE), ("Hello, world!\n"
                + "Hello, mate!\n").getBytes(StandardCharsets.UTF_8));
    }

    @Test
    public void readData_fromEmptyFile_ok() {
        List<String> actualResult = dataReaderService.readData(PATH_TO_EMPTY_FILE);
        Assert.assertEquals("Incorrect result list from empty file",
                EMPTY_LIST_RESULT, actualResult);
    }

    @Test
    public void readData_fromFirstAndSecondFiles_ok() {
        List<String> actualFirstResult = dataReaderService.readData(PATH_TO_FIRST_TEST_FILE);
        Assert.assertEquals("Incorrect result list from the file. Should be ",
                RESULT_FROM_FIRST_FILE, actualFirstResult);
        Assert.assertEquals(RESULT_FROM_FIRST_FILE.size(), actualFirstResult.size());
        List<String> actualSecondResult = dataReaderService.readData(PATH_TO_SECOND_TEST_FILE);
        Assert.assertEquals("Incorrect result list from the file. Should be ",
                RESULT_FROM_SECOND_FILE, actualSecondResult);
        Assert.assertEquals(RESULT_FROM_SECOND_FILE.size(), actualSecondResult.size());
    }

    @Test
    public void readData_fromInvalidFile_exceptionExpected_ok() {
        try {
            dataReaderService.readData(PATH_TO_THIRD_INVALID_FILE);
        } catch (RuntimeException e) {
            Assert.assertTrue("You should throw a Runtime Exception", true);
        }
    }
}
