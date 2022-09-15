package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static WriterService writerService;
    private static List<String> list;
    private static final String OUTPUT_NORMAL_FILE
            = "src/main/java/core/basesyntax/resources/reportFile.csv";
    private static final String OUTPUT_WRONG_FILE = "/";

    @BeforeClass
    public static void beforeClass() {
        writerService = new WriterServiceImpl();
        list = new ArrayList<>();
        list.add("b,banana,20");
    }

    @Test
    public void writeToFile_Ok() {
        writerService.writeToFile(list, OUTPUT_NORMAL_FILE);
        List<String> reader;
        try {
            reader = Files.readAllLines(Path.of(OUTPUT_NORMAL_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file:" + OUTPUT_NORMAL_FILE);
        }
        Assert.assertEquals(reader, list);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_NotOk() {
        writerService.writeToFile(list, OUTPUT_WRONG_FILE);
    }
}
