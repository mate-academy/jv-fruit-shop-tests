package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static FileWriter fileWriter = new FileWriterImpl();
    private static String report;
    private static String path;

    @BeforeClass
    public static void beforeClass() {
        report = "fruit,quantity" + System.lineSeparator()
                + "banana,100" + System.lineSeparator();
    }

    @Test
    public void writeFile_validReportFile_ok() {
        path = "src/test/resources/reportForTests";
        fileWriter.writeFile(path, report);
        int expected = 2;
        int actual = 0;
        try {
            actual = (Files.readAllLines(Path.of(path))).size();
        } catch (IOException e) {
            throw new RuntimeException("Can't read report", e);
        }
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeFile_invalidPath_notOk() {
        path = "";
        fileWriter.writeFile(path, report);
    }
}
