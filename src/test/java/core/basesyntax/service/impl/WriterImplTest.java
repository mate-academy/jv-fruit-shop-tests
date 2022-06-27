package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.Writer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterImplTest {
    private static String path;
    private static Writer writer;

    @BeforeClass
    public static void beforeAllClassMethods() {
        path = "src/test/java/resources/ActualReport.csv";
        writer = new WriterImpl();
    }

    @Test
    public void writeFruitsReport_validPath_ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,130" + System.lineSeparator() + "strawberry,100"
                + System.lineSeparator() + "passionfruit,55";
        writer.writeFruitsReport(expected, path);
        String actual = doReport(path);

        assertEquals(expected, actual);
    }

    private String doReport(String path) {
        List<String> report;
        try {
            report = Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException("Invalid path to file:" + path, e);
        }
        return report.stream()
                .map(s -> s + System.lineSeparator())
                .collect(Collectors.joining()).trim();
    }

    @Test(expected = RuntimeException.class)
    public void writeFruitsReport_invalidPath_ok() {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,130" + System.lineSeparator() + "strawberry,100"
                + System.lineSeparator() + "passionfruit,55";

        writer.writeFruitsReport(report, "src/jasda/3243");
    }

    @Test(expected = RuntimeException.class)
    public void writeFruitsReport_nullPath_ok() {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,130" + System.lineSeparator() + "strawberry,100"
                + System.lineSeparator() + "passionfruit,55";

        writer.writeFruitsReport(report, null);
    }
}
