package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriteService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriteServiceImplTest {
    private static final String VALID_OUTPUT = "src/main/resources/output.csv";
    private static FileWriteService fileWriteService;

    @BeforeClass
    public static void setUp() {
        fileWriteService = new FileWriteServiceImpl();
    }

    @Test
    public void writeReportToFile_validReport_ok() throws IOException {
        fileWriteService.writeReportToFile("report", VALID_OUTPUT);
        List<String> expected = List.of("report");
        List<String> actual = Files.readAllLines(Path.of(VALID_OUTPUT));
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeReportToFile_invalidPath_notOk() {
        fileWriteService.writeReportToFile("report",
                "src/non_existing_folder/non_existent_path.csv");
    }

    @Test(expected = RuntimeException.class)
    public void writeReportToFile_nullFilePath_notOk() {
        fileWriteService.writeReportToFile("fruit,quantity\n"
                + "banana,152\n"
                + "apple,90", null);
    }

    @Test
    public void writeReportToFile_emptyReport_ok() throws IOException {
        fileWriteService.writeReportToFile("", VALID_OUTPUT);
        List<String> expected = new ArrayList<>();
        List<String> actual = Files.readAllLines(Path.of(VALID_OUTPUT));
        assertEquals(expected,actual);
    }
}
