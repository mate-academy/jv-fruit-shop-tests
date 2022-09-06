package service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.WriterService;

public class WriteServiceImplTest {
    private static final String INCORRECT_PATH = "sadas/ass";
    private static final String EXPECTED_PATH = "src/test/resources/test_expected_report.csv";
    private static final String ACTUAL_PATH = "src/test/resources/test_actual_report.csv";
    private static final String REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator() + "apple,90";
    private WriterService writerService;

    @Before
    public void setUp() {
        writerService = new WriteServiceImpl();
    }

    @Test
    public void write_valid_ok() {
        writerService.writeTo(ACTUAL_PATH, REPORT);
        Assert.assertEquals(read(EXPECTED_PATH), read(ACTUAL_PATH));
    }

    @Test(expected = RuntimeException.class)
    public void write_incorrectPath_notOk() {
        writerService.writeTo(INCORRECT_PATH, REPORT);
    }

    private String read(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
