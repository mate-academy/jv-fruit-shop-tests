package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class ReaderImplTest {
    private Reader reader = new ReaderImpl();

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
        String expected;
        Reader reader = new ReaderImpl();
        List<FruitTransaction> actualList = reader.readFromFile("src/test/resources/input.csv");
        expected = expectedList.stream()
                .map(i -> String.valueOf(i.getOperation()) + String.valueOf(i.getFruit())
                        + String.valueOf(i.getQuantity())).collect(Collectors.joining());
        String actual = actualList.stream()
                .map(i -> String.valueOf(i.getOperation()) + String.valueOf(i.getFruit())
                        + String.valueOf(i.getQuantity())).collect(Collectors.joining());
        Assert.assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void reader_readFromEmptyFile_NotOk() {
        Reader reader = new ReaderImpl();
        List<FruitTransaction> actualList =
                reader.readFromFile("src/test/resources/inputEmpty.csv");
    }

    @Test(expected = RuntimeException.class)
    public void reader_readFromNotExistingFile_NotOk() {
        Reader reader = new ReaderImpl();
        List<FruitTransaction> actualList = reader.readFromFile("src/test/resources");
    }
}
