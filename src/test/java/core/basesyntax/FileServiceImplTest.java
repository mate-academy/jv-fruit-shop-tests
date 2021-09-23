package core.basesyntax;

import core.basesyntax.service.impl.FileServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileServiceImplTest {
    private static final String INPUT_FILE_PATH = "src/test/resources/inputFile_test";
    private static final String REPORT_FILE_PATH = "src/test/resources/reportFile_test";
    private static final String INCORRECT_FILE_PATH = "";
    private static FileServiceImpl fileService;

    @BeforeClass
    public static void beforeClass() {
        fileService = new FileServiceImpl();
    }

    @Test
    public void readFromFile_equalsData_ok() {
        List<String> dataList = new ArrayList<>();
        dataList.add("b,apple,1");
        Assert.assertEquals(dataList, fileService.readFromFile(INPUT_FILE_PATH));
    }

    @Test
    public void readFromFile_differentData_ok() {
        Assert.assertFalse(fileService.readFromFile(INPUT_FILE_PATH).contains("#"));
    }

    @Test
    public void readFromFile_notOk() {
        Assert.assertThrows(RuntimeException.class, () ->
                fileService.readFromFile(INCORRECT_FILE_PATH));
    }

    @Test
    public void writeToFile_ok() {
        Assert.assertTrue(fileService.writeToReportFile("Data", REPORT_FILE_PATH));
    }

    @Test
    public void writeToFile_notOk() {
        Assert.assertThrows(RuntimeException.class, () ->
                fileService.writeToReportFile("", INCORRECT_FILE_PATH));
    }

}
