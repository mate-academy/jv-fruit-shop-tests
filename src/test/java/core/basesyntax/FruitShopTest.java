package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileService;
import core.basesyntax.service.FileServiceImpl;
import core.basesyntax.service.ReportBuilder;
import core.basesyntax.service.ReportBuilderImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class FruitShopTest {
    private final FileService fileService = new FileServiceImpl();
    private final ReportBuilder report = new ReportBuilderImpl();
    private final FruitShop shop = new FruitShop();

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
