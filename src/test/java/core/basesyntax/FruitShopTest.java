package core.basesyntax;

import core.basesyntax.service.FileService;
import core.basesyntax.service.FileServiceImpl;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Test;

public class FruitShopTest {

    @Test
    public void testFruitShop_processing_Ok() {
        String input = "type,fruit,quantity\r\n"
                + "b,banana,500\r\n"
                + "b,apple,500\r\n"
                + "s,banana,2000\r\n"
                + "p,banana,1300\r\n"
                + "r,apple,500\r\n"
                + "p,apple,700\r\n"
                + "p,banana,500\r\n"
                + "s,banana,2000";

        FileService fileService = new FileServiceImpl();
        fileService.writeInFile(input, "activities.csv");
        FruitShop fruitShop = new FruitShop();
        fruitShop.processing();
        List<String> test = fileService.readFromFile("report.csv");
        String expected = "fruit,quantity\r\n"
                + "banana,2700\r\n"
                + "apple,300";
        String actual = test.stream().map(Object::toString)
                .collect(Collectors.joining(System.lineSeparator()));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testFileService_writeRead_ok() {
        String input = "type,trIger,Crypta\r\n"
                + "Orange,banana,2023\r\n"
                + "b,C,%";
        FileService fileService = new FileServiceImpl();
        fileService.writeInFile(input, "store.csv");
        List<String> test = fileService.readFromFile("store.csv");
        String expected = "type,trIger,Crypta\r\n"
                + "Orange,banana,2023\r\n"
                + "b,C,%";
        String actual = test.stream().map(Object::toString)
                .collect(Collectors.joining(System.lineSeparator()));
        Assert.assertEquals(expected, actual);
    }
}
