package core.basesyntax.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class WriterServiceImplTest {
    @Test
    public void writeReport_Ok() {
        String report = new StringBuilder().append("First line")
                .append(System.lineSeparator())
                .append("Second Line")
                .append(System.lineSeparator())
                .append("Third Line")
                .toString();
        String fileName = "src/test/resources/Report.csv";
        new WriterServiceImpl().writeToFile(report, fileName);
        List<String> expected = Arrays.stream(report.split(System.lineSeparator()))
                .collect(Collectors.toList());
        try {
            Assert.assertEquals(expected, Files.readAllLines(Path.of(fileName)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @After
    public void tearDown() {
        File fileName = new File("src/test/resources/Report.csv");
        fileName.delete();
    }
}
