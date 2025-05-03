package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Test;

public class WriterServiceImplTest {
    @Test(expected = RuntimeException.class)
    public void constructor_pathIsNull_notOk() {
        WriterService writerService = new WriterServiceImpl(null);
    }

    @Test
    public void write_pathExist_ok() {
        String report = "first row" + System.lineSeparator() + "second row";
        String path = "src/test/resources/report.csv";
        WriterService writerService = new WriterServiceImpl(path);
        writerService.write(report);
        String result;

        try {
            result = Files.readAllLines(Path.of(path))
                    .stream()
                    .collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new RuntimeException("Error reading result file: " + path, e);
        }

        Assert.assertEquals("message should be the same", report, result);
    }
}
