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
        StringBuilder builder = new StringBuilder();
        StringBuilder b = new StringBuilder();
        String expected = b.append("fruit,quantity")
                .append(System.lineSeparator())
                .append("banana,2700")
                .append(System.lineSeparator())
                .append("apple,300").toString();
        String input = builder.append("type,fruit,quantity")
                .append(System.lineSeparator())
                .append("b,banana,500")
                .append(System.lineSeparator())
                .append("b,apple,500")
                .append(System.lineSeparator())
                .append("s,banana,2000")
                .append(System.lineSeparator())
                .append("p,banana,1300")
                .append(System.lineSeparator())
                .append("r,apple,500")
                .append(System.lineSeparator())
                .append("p,apple,700")
                .append(System.lineSeparator())
                .append("p,banana,500")
                .append(System.lineSeparator())
                .append("s,banana,2000").toString();

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
            Assert.assertEquals(expected, actual);
        } catch (IOException e1) {
            throw new RuntimeException("Can't read from file", e1);
        }
    }
}
