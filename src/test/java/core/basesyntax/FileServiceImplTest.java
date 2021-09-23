package core.basesyntax;

import core.basesyntax.service.impl.FileServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileServiceImplTest {
    private static final String INPUT_FILE_PATH = "src/test/java/resources/inputFile_test";
    private static final String REPORT_FILE_PATH = "src/test/java/resources/reportFile_test";
    private static final String INCORRECT_FILE_PATH = "";
    private static FileServiceImpl fileService;

    @BeforeAll
    public static void beforeAll() {
        fileService = new FileServiceImpl();
    }

    @Test
    public void readFromFile_equalsData_ok() {
        List<String> dataList = new ArrayList<>();
        dataList.add("b,apple,1");
        Assertions.assertEquals(dataList, fileService.readFromFile(INPUT_FILE_PATH));
    }

    @Test
    public void readFromFile_differentData_ok() {
        Assertions.assertFalse(fileService.readFromFile(INPUT_FILE_PATH).contains("#"));
    }

    @Test
    public void readFromFile_notOk() {
        Assertions.assertThrows(RuntimeException.class, () ->
                fileService.readFromFile(INCORRECT_FILE_PATH));
    }

    @Test
    public void writeToFile_ok() {
        Assertions.assertTrue(fileService.writeToReportFile("Data", REPORT_FILE_PATH));
    }

    @Test
    public void writeToFile_notOk() {
        Assertions.assertThrows(RuntimeException.class, () ->
                fileService.writeToReportFile("", INCORRECT_FILE_PATH));
    }

}
