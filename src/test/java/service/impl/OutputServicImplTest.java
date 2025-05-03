package service.impl;

import db.Storage;
import model.Fruit;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.OutputService;

public class OutputServicImplTest extends OutputServiceImpl {
    private static OutputService outputService;

    @BeforeClass
    public static void setUp() {
        outputService = new OutputServiceImpl();
    }

    @Test
    public void getOutput_valid_ok() {
        Storage.storage.put(new Fruit("banana"), 140);
        Storage.storage.put(new Fruit("papaya"), 750);
        Storage.storage.put(new Fruit("ananas"), 14);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,140" + System.lineSeparator()
                + "papaya,750" + System.lineSeparator()
                + "ananas,14";
        String actual = outputService.getOutput();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getOutput_empty_notOk() {
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = outputService.getOutput();
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
