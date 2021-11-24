package core.basesyntax.service.impl;

import core.basesyntax.service.Reader;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderTest {
    private static Reader reader;

    @BeforeClass
    public static void init() {
        reader = new ReaderImpl();
    }

    @Test
    public void readFromValidSource_Ok() {
        Assert.assertNotNull(reader.read("src/main/resources/daily_report.csv"));
    }

    @Test(expected = RuntimeException.class)
    public void readFromInvalidSource_NotOk() {
        reader.read("src/main/resources/sd.csv");
    }
}
