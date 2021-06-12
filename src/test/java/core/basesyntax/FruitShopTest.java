package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.FileService;
import core.basesyntax.service.FileServiceImpl;
import core.basesyntax.service.ReportBuilder;
import core.basesyntax.service.ReportBuilderImpl;
import core.basesyntax.service.Validator;
import core.basesyntax.service.ValidatorImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class FruitShopTest {
    private final FileService fileService = new FileServiceImpl();
    private final Validator validator = new ValidatorImpl();
    private final ReportBuilder report = new ReportBuilderImpl();
    private final FruitShop shop = new FruitShop();

    @Test
    public void readFileByNullFileName() {
        try {
            fileService.readFile(null);
        } catch (NullPointerException e) {
            return;
        }
        fail("Method have to throw NullPointerException.");
    }

    @Test
    public void readFileByWrongFileName() {
        String fileName = "anotherFileName";
        try {
            fileService.readFile(fileName);
        } catch (RuntimeException e) {
            return;
        }
        fail("Method have to throw RuntimeException.");
    }

    @Test
    public void readFileByCorrectName_Ok() {
        String fileName = "input.txt";
        List<String> excepted = Arrays.asList("1","2","3","4","5");
        List<String> actual = fileService.readFile(fileName);
        assertEquals(excepted,actual);
    }

    @Test
    public void writeFileByNullFileName() {
        String content = "some content";
        try {
            fileService.writeFile(null, content);
        } catch (NullPointerException e) {
            return;
        }
        fail("Method have to throw NullPointerException.");
    }

    @Test
    public void writeFileByWrongFileName() {
        String content = "some content";
        String wrongFilename = "";
        try {
            fileService.writeFile(wrongFilename,content);
        } catch (RuntimeException e) {
            return;
        }
        fail("Method have to throw RuntimeException.");
    }

    @Test
    public void writeFileByCorrectName_Ok() {
        String fileName = "output.txt";
        String outputFileNewContent = "5\n4\n3\n2\n1";
        List<String> excepted = Arrays.asList("5","4","3","2","1");
        fileService.writeFile(fileName, outputFileNewContent);
        List<String> actual = fileService.readFile(fileName);
        assertEquals(excepted,actual);
    }

    @Test
    public void validateCorrectString_Ok() {
        List<String> testList = Arrays.asList("operationName,fruitName,10");
        validator.validate(testList);
    }

    @Test
    public void validateWrongString_NotOk() {
        List<String> testList = Arrays.asList(",fruitName,10");
        try {
            validator.validate(testList);
        } catch (RuntimeException e) {
            return;
        }
        fail("Method have to throw RunTimeException.");
    }

    @Test
    public void validateNull() {
        try {
            validator.validate(null);
        } catch (NullPointerException e) {
            return;
        }
        fail("Method have to throw NullPointerException.");
    }

    @Test
    public void validateEmptyList() {
        List<String> emptyList = new ArrayList<>();
        try {
            validator.validate(emptyList);
        } catch (RuntimeException e) {
            return;
        }
        fail("Method have to throw RunTimeException.");
    }

    @Test
    public void buildReportWithNull() {
        try {
            report.buildReport(null);
        } catch (NullPointerException e) {
            return;
        }
        fail("Method have to throw NullPointerException.");
    }

    @Test
    public void buildReportWithEmptyList() {
        List<String> emptyList = new ArrayList<>();
        try {
            report.buildReport(emptyList);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        fail("Method have to throw NullPointerException.");
    }

    @Test
    public void buildReportWithListWithSizeOne() {
        List<String> wrongDataList = new ArrayList<>();
        wrongDataList.add("b,banana,10");
        try {
            report.buildReport(wrongDataList);
        } catch (RuntimeException e) {
            return;
        }
        fail("Method have to throw RunTimeException.");
    }

    @Test
    public void buildReportWithWrongData_NotOk() {
        List<String> wrongDataList = new ArrayList<>();
        wrongDataList.add("Heading");
        wrongDataList.add("wrongOperation,banana,10");
        try {
            report.buildReport(wrongDataList);
        } catch (NullPointerException e) {
            return;
        }
        fail("Method have to throw NullPointerException.");
    }

    @Test
    public void buildReportWithCorrectData_Ok() {
        List<String> correctDataList = createListWithCorrectData();
        String expectedReport = "fruit,quantity\nbanana,10"
                + "\nsomeUnknownFruit,1\n";
        String actualReport = report.buildReport(correctDataList);
        assertEquals(expectedReport,actualReport);
    }

    @Test
    public void storeSendReport_Ok() {
        String fileName = "output.txt";
        List<String> correctDataList = createListWithCorrectData();
        String actualReport = report.buildReport(correctDataList);
        shop.getFileService().writeFile(fileName, actualReport);
        List<String> excepted = Arrays.asList("fruit,quantity", "banana,10", "someUnknownFruit,1");
        List<String> actual = fileService.readFile(fileName);
        assertEquals(excepted,actual);
    }

    List<String> createListWithCorrectData() {
        List<String> correctDataList = new ArrayList<>();
        correctDataList.add("Heading");
        correctDataList.add("b,banana,10");
        correctDataList.add("b,someUnknownFruit,1");
        return correctDataList;
    }
}
