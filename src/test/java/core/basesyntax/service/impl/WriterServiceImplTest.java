package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String INVALID_PATH = "";
    private static final String EXPECTED_PATH = "src/test/resources/actualOutput.csv";
    private static final String ACTUAL_PATH = "src/test/resources/actualOutput.csv";
    private static final String REPORT = "fruit,quantity" + System.lineSeparator() + "banana,152";
    private final WriterService writer = new WriterServiceImpl();

    @Test
    public void write_ToValidFile_Ok() {
        writer.writeToFile(REPORT, ACTUAL_PATH);
        write(REPORT);
        Assert.assertEquals(read(EXPECTED_PATH), read(ACTUAL_PATH));
    }

    @Test(expected = RuntimeException.class)
    public void write_EmptyReport_Ok() {
        writer.writeToFile("", ACTUAL_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void write_ToInvalidPath_NotOk() {
        writer.writeToFile(REPORT, INVALID_PATH);
    }

    private String read(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException("Read failed", e);
        }
    }

    private void write(String report) {
        try {
            Files.write(Path.of(EXPECTED_PATH), report.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Write failed");
        }
    }
}
