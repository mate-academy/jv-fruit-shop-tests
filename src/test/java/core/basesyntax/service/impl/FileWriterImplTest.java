package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static FileWriter fileWriter = new FileWriterImpl();
    private static String report;

    @BeforeClass
    public static void beforeClass() {
        report = "fruit,quantity" + System.lineSeparator()
                + "banana,100" + System.lineSeparator();
    }

    @Test
    public void writeFile_validReportFile_ok() {
        String path = "src/test/resources/reportForTests";
        fileWriter.writeFile(path, report);
        List<String> actualList;
        try {
            actualList = Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException("Can't read report", e);
        }
        StringBuilder actual = new StringBuilder();
        for (String line : actualList) {
            actual.append(line).append(System.lineSeparator());
        }
        String actualString = actual.toString();
        assertEquals(report, actualString);
    }

    @Test(expected = RuntimeException.class)
    public void writeFile_invalidPath_notOk() {
        fileWriter.writeFile("", report);
    }
}
