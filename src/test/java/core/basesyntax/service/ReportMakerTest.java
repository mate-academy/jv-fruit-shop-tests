package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportMakerImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportMakerTest {
    private static ReportMaker reportMaker;
    private String head;
    private String bananaFruit;
    private String appleFruit;
    private int fruitQuantity;

    @BeforeClass
    public static void beforeClass() throws Exception {
        reportMaker = new ReportMakerImpl();
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }

    @Before
    public void setUp() throws Exception {
        head = "fruit,quantity";
        bananaFruit = "banana";
        appleFruit = "apple";
        fruitQuantity = 10;

    }

    @Test
    public void createReport_StorageWithValidData_Ok() {
        Storage.fruits.put(bananaFruit, fruitQuantity);
        Storage.fruits.put(appleFruit, fruitQuantity);
        String expected = new StringBuilder()
                .append(head).append(System.lineSeparator())
                .append("banana,10").append(System.lineSeparator())
                .append("apple,10").toString();
        String actual = reportMaker.createReport();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void createReport_EmptyStorage_Ok() {
        String expected = head;
        String actual = reportMaker.createReport();
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void createReport_StorageContainsNull_NotOk() {
        Storage.fruits.put(null,fruitQuantity);
        Storage.fruits.put(bananaFruit, null);
        reportMaker.createReport();
    }
}
