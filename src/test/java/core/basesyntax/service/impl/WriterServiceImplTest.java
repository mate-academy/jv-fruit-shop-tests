package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String INVALID_PATH = "***1";
    private static final String ACTUAL_PATH = "src/test/resources/actualOutput.csv";
    private static final String EXPECTED_PATH = "src/test/resources/expectedOutput.csv";
    private static final String REPORT = "fruit,quantity\n" + "banana,152";
    private final WriterService writer = new WriterServiceImpl();

    @Test
    public void write_ToValidFile_Ok() {
        writer.writeToFile(REPORT, ACTUAL_PATH);
        Assert.assertEquals(read(EXPECTED_PATH), read(ACTUAL_PATH));
    }

    @Test(expected = RuntimeException.class)
    public void write_ToInvalidPath_NotOk() {
        writer.writeToFile(REPORT, INVALID_PATH);
    }

    private String read(String path) {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            return "";
        }
        return builder.toString();
    }
}
