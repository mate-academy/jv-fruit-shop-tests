package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.WriterToFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterToFileCsvImplTest {
    private static WriterToFile writer;
    private String defaultPath = "src/test/resources/";
    private File file = new File(defaultPath + "report.csv");

    @BeforeClass
    public static void beforeClass() {
        writer = new WriterToFileCsvImpl();
    }

    @Test
    public void write_fileNotExist_ok() {
        List<String> actual = List.of("fruit,quantity",
                "banana,152", "apple,90", "pineapple,20");
        List<String> expected;
        writer.write(actual, file);
        try {
            expected = Files.readAllLines(new File(defaultPath + "report.csv").toPath());
        } catch (IOException e) {
            fail("Test couldn't find the file!!!");
            return;
        }
        assertEquals(expected, actual);
    }

    @Test
    public void write_fileExists_ok() {
        List<String> actual = List.of("fruit,quantity",
                "banana,207", "apple,90");
        List<String> expected;
        writer.write(actual, file);
        try {
            expected = Files.readAllLines(new File(defaultPath + "report.csv").toPath());
        } catch (IOException e) {
            fail("Test couldn't find the file!!!");
            return;
        }
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void write_filePathNotExists_notOk() {
        List<String> actual = List.of("fruit,quantity",
                "banana,207", "apple,90");
        writer.write(actual, new File("sadasdfsf/@" + "report.csv"));
    }
}
