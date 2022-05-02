package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriteFileService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriteFileServiceImplTest {
    private static WriteFileService writeFileService;

    @BeforeClass
    public static void beforeClass() {
        writeFileService = new WriteFileServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void write_Wrong_Path_To_File_NotOk() {
        String report = "fruit,quantity\nbanana,14\r\napple,10\r\norange,67\r\n";
        writeFileService.write(report, "src/test/resources/EmptyInput.csv");
    }

    @Test (expected = RuntimeException.class)
    public void write_Empty_Report_NotOk() {
        String report = "";
        writeFileService.write(report, "src/test/java/resources/ToFile.csv");
    }

    @Test
    public void write_Correct_Report_Ok() {
        String report = "fruit,quantity\nbanana,14\r\napple,10\r\norange,67\r\n";
        writeFileService.write(report, "src/test/java/resources/ToFile.csv");
        List<String> actual;
        List<String> expected;
        try {
            actual = Files.readAllLines(Path.of("src/test/java/resources/ToFile.csv"));
            expected = Files.readAllLines(Path.of("src/test/java/resources/ExpectedFile.csv"));
        } catch (IOException e) {
            throw new RuntimeException();
        }
        assertEquals(actual, expected);
    }
}
