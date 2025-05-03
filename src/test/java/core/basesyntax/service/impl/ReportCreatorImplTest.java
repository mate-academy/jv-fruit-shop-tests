package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportCreator;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class ReportCreatorImplTest {
    private static final ReportCreator reportCreator = new ReportCreatorImpl();
    private static final List<String> expectedList
            = new ArrayList<>(List.of("fruit,quantity", "apple,50"));

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }

    @Test
    public void reportCreator_Ok() {
        Storage.storage.put(new Fruit("apple"), 50);
        List<String> actual = reportCreator.createReport();
        Assert.assertEquals(expectedList, actual);
    }

    @Test
    public void reportCreator_SeveralFruits_Ok() {
        Storage.storage.put(new Fruit("apple"), 50);
        Storage.storage.put(new Fruit("banana"), 100);
        List<String> actual = reportCreator.createReport();
        expectedList.add(1, "banana,100");
        Assert.assertEquals(expectedList, actual);
    }

    @Test(expected = AssertionError.class)
    public void reportCreator_notOk() {
        Storage.storage.put(new Fruit("incorrect"), 124);
        List<String> actual = reportCreator.createReport();
        Assert.assertEquals(expectedList, actual);
    }
}
