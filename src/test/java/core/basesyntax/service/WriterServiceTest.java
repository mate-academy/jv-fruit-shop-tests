package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceTest {
    private static WriterService writerService;
    private static List<String> expected;
    private static StringBuilder report;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Storage.fruits.clear();
        Storage.fruits.put("banana", 20);
        Storage.fruits.put("apple", 90);
        expected = new ArrayList<>();
        expected.add("fruit,quantity");
        expected.add("banana,20");
        expected.add("apple,90");
        report = new StringBuilder("fruit,quantity");
        report.append(System.lineSeparator()).append("banana,20");
        report.append(System.lineSeparator()).append("apple,90");
        writerService = new WriterServiceImpl();
    }

    @Test
    public void writeToFile_Ok() {
        String trueFilePath = "src" + File.separator
                             + "test" + File.separator
                             + "resources" + File.separator
                             + "report.csv";
        System.out.println(trueFilePath);
        writerService.writeToFile(trueFilePath, report.toString());
        File file = new File(trueFilePath);
        List<String> actual;
        try {
            actual = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void emptyPath_NotOk() {
        String emptyFilePath = "";
        writerService.writeToFile(emptyFilePath, report.toString());
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.fruits.clear();
    }
}
