package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.Writer;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WriterImplTest {
    private Writer writer = new WriterImpl();
    private String expected;

    @Before
    public void prepareBefore() {
        StringBuilder expectedBuilder = new StringBuilder();
        expected = expectedBuilder.append("fruit,quantity")
                .append(System.lineSeparator())
                .append("banana,2700")
                .append(System.lineSeparator())
                .append("apple,300").toString();
    }

    @After
    public void cleanAfter() {
        Storage.fruits.clear();
    }

    @Test
    public void writer_writeInFile_Ok() {
        writer.writeInFile(expected,"src/test/resources/report.csv");
        try {
            File file = new File("src/test/resources/report.csv");
            List<String> actualList = Files.readAllLines(file.toPath());
            String actual = actualList
                    .stream().map(String::valueOf)
                    .collect(Collectors.joining(System.lineSeparator()));
            Assert.assertEquals(actual, expected);
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file", e);
        }
    }

    @Test(expected = RuntimeException.class)
    public void writer_writeInDirectory_NotOk() {
        writer.writeInFile(expected,"src/test/resources");
    }
}
