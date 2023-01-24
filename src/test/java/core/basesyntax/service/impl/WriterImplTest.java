package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.Writer;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WriterImplTest {

    @Before
    public void cleanBefore() {
        Storage.fruits.clear();
    }

    @After
    public void cleanAfter() {
        Storage.fruits.clear();
    }

    @Test
    public void writer_writeInFile_Ok() {
        Writer writer = new WriterImpl();
        String expected = "fruit,quantity\r\n"
                + "banana,2700\r\n"
                + "apple,300";
        writer.writeInFile(expected,"src/test/resources/report.csv");
        List<String> actualList = new ArrayList<>();
        try {
            File file = new File("src/test/resources/report.csv");
            List<String> activities = Files.readAllLines(file.toPath());
            actualList = activities;
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file", e);
        }
        String actual = actualList
                .stream().map(String::valueOf)
                .collect(Collectors.joining(System.lineSeparator()));
        Assert.assertEquals(actual, expected);

    }
}
