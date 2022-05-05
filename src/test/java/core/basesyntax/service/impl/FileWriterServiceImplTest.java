package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.FileWriterService;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static FileWriterService fileWriterService;
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl();
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void writeToFile_Ok() {
        String toFileName = "testOutputFile";
        String report = "fruit,quantity\n"
                + "banana,52\n"
                + "apple,9\n";
        List<String> expected = List.of("fruit,quantity",
                "banana,52",
                "apple,9");
        fileWriterService.writeToFile(toFileName, report);
        File testOutputFile = new File("testOutputFile");
        List<String> actual = new ArrayList<>();
        actual = fileReaderService.readFromFile("testOutputFile");
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_NotOk() {
        String report = "fruit,quantity \n"
                + "banana,52\n"
                + "apple,9\n";
        fileWriterService.writeToFile("", report);
    }
}
