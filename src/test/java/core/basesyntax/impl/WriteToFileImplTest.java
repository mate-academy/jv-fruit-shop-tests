package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.ReadFileService;
import core.basesyntax.service.WriteToFileService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriteToFileImplTest {
    private static List<String> report;
    private static final String PATH_TO_FILE = "src/test/resources/reportWriteToFileImplTest.csv";
    private static ReadFileService readFileService;
    private static WriteToFileService writeToFileService;

    @BeforeAll
    static void beforeAll() {
        readFileService = new ReadFileImpl();
        writeToFileService = new WriteToFileImpl();
        report = new ArrayList<>();
        report.add("fruit,quantity");
        report.add("banana,1");
        report.add("apple,2");
    }

    @Test
    void writeToFile_correctPath_ok() {
        writeToFileService.writeToFile(PATH_TO_FILE, String.join(System.lineSeparator(), report));
        List<String> actualReport = readFileService.readFile(PATH_TO_FILE);
        assertEquals(actualReport, report);
    }
}
