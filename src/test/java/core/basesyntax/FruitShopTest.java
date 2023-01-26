package core.basesyntax;

import core.basesyntax.db.Storage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class FruitShopTest {

    private StringBuilder builder = new StringBuilder();
    private StringBuilder expectedBuilder = new StringBuilder();
    private String expected = expectedBuilder.append("fruit,quantity")
            .append(System.lineSeparator())
            .append("banana,2700")
            .append(System.lineSeparator())
            .append("apple,300").toString();
    private FruitShop fruitShop = new FruitShop();

    @After
    public void cleanAfter() {
        Storage.fruits.clear();
    }

    @Test
    public void testFruitShop_processing_Ok() {

        fruitShop.processing("src/test/resources/inputFruitShop.csv",
                "src/test/resources/reportFruitShop.csv");
        try {
            File file = new File("src/test/resources/reportFruitShop.csv");
            List<String> list = Files.readAllLines(file.toPath());
            String actual = list.stream().map(Object::toString)
                    .collect(Collectors.joining(System.lineSeparator()));
            Assert.assertEquals(expected, actual);
        } catch (IOException e1) {
            throw new RuntimeException("Can't read from file", e1);
        }
    }
}
