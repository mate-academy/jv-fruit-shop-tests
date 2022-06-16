package core.basesyntax.service.imp;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImpTest {
    private static final String VALID_FILE_DESTINATION = "src/test/resources/TestReport.csv";
    private static final String INVALID_FILE_DESTINATION = "/";
    private static WriterService writerService;
    private static List<String> records;

    @BeforeClass
    public static void beforeClass() {
        writerService = new WriterServiceImp();
        records = new ArrayList<>();
        records.add("somedata");
    }

    @Test
    public void writeValidDestination_ok() {
        Path path = Path.of(VALID_FILE_DESTINATION);
        writerService.writeToFile(records, path);
        List<String> readedLines;
        try {
            readedLines = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Can`t read from test file: " + path, e);
        }
        Assert.assertEquals(readedLines, records);
    }

    @Test(expected = RuntimeException.class)
    public void writeInvalidDestination_notOk() {
        writerService.writeToFile(records, Path.of(INVALID_FILE_DESTINATION));
    }
}
