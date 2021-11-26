package core.basesyntax.service;

import core.basesyntax.bd.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.ParsedLine;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.FruitCounter;

public class FruitCounterImplTeset {
    private static List<ParsedLine> parsedLineList;
    private static FruitCounter fruitCounter;

    @BeforeClass
    public static void init() {
        fruitCounter = new FruitCounterImpl();
        parsedLineList = new ArrayList<>();
    }

    @Test
    public void fruit_counter_ok() {
        parsedLineList.add(new ParsedLine("b", "banana", 20));
        parsedLineList.add(new ParsedLine("b", "apple", 25));
        fruitCounter.fruitCounter(parsedLineList);
        Fruit expected = new Fruit("banana", 20);
        Assert.assertEquals(expected, Storage.storage.get(0));
        Fruit expected2 = new Fruit("apple", 25);
        Assert.assertEquals(expected2, Storage.storage.get(1));
    }

    @AfterClass
    public static void storage_clear() {
        Storage.storage.clear();
    }
}
