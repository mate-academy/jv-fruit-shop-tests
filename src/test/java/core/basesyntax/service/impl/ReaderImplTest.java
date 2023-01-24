package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.Reader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReaderImplTest {
    private Reader reader = new ReaderImpl();

    @Before
    public void cleanBefore() {
        Storage.fruits.clear();
    }

    @After
    public void cleanAfter() {
        Storage.fruits.clear();
    }

    @Test
    public void reader_readFromFile_Ok() {
        List<FruitTransaction> expectedList = new ArrayList<>();
        expectedList.add(new FruitTransaction(Operation.BALANCE,"banana",500));
        expectedList.add(new FruitTransaction(Operation.RETURN,"apple",500));
        expectedList.add(new FruitTransaction(Operation.SUPPLY,"banana",2000));

        String input = "type,fruit,quantity\r\n"
                + "b,banana,500\r\n"
                + "r,apple,500\r\n"
                + "s,banana,2000";

        try (BufferedWriter bufferedWriter =
                     new BufferedWriter(new FileWriter("src/test/resources/input.csv"))) {
            bufferedWriter.write(input);
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file", e);
        }
        Reader reader = new ReaderImpl();
        List<FruitTransaction> actualList = reader.readFromFile("src/test/resources/input.csv");
        String expected = expectedList.stream()
                .map(i -> String.valueOf(i.getOperation()) + String.valueOf(i.getFruit())
                        + String.valueOf(i.getQuantity())).collect(Collectors.joining());
        String actual = actualList.stream()
                .map(i -> String.valueOf(i.getOperation()) + String.valueOf(i.getFruit())
                        + String.valueOf(i.getQuantity())).collect(Collectors.joining());
        Assert.assertEquals(actual, expected);
    }
}
