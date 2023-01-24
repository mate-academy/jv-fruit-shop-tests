package core.basesyntax;

import core.basesyntax.db.Storage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitShopTest {

    @Before
    public void cleanBefore() {
        Storage.fruits.clear();
    }

    @After
    public void cleanAfter() {
        Storage.fruits.clear();
    }

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

        try (BufferedWriter bufferedWriter =
                     new BufferedWriter(new FileWriter("src/test/resources/input.csv"))) {
            bufferedWriter.write(input);
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file", e);
        }
        FruitShop fruitShop = new FruitShop();
        fruitShop.processing("src/test/resources/input.csv", "src/test/resources/report.csv");
        try {
            File file = new File("src/test/resources/report.csv");
            List<String> list = Files.readAllLines(file.toPath());
            String actual = list.stream().map(Object::toString)
                    .collect(Collectors.joining(System.lineSeparator()));
            String expected = "fruit,quantity\r\n"
                    + "banana,2700\r\n"
                    + "apple,300";
            Assert.assertEquals(expected, actual);
        } catch (IOException e1) {
            throw new RuntimeException("Can't read from file", e1);
        }
    }
}
