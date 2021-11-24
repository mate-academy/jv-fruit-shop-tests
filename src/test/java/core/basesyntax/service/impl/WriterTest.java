package core.basesyntax.service.impl;

import core.basesyntax.service.Writer;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterTest {
    private static Writer writer;

    @BeforeClass
    public static void init() {
        writer = new WriterImpl();
    }

    @Test
    public void write_Ok() {
        Assert.assertTrue(writer.write("src/main/resources/report.csv", "Test"));
    }
}
