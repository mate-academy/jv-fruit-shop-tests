package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.rules.TemporaryFolder;

class WriterServiceImplTest {

    private static WriterServiceImpl writeToFileService;
    private static final String NO_WRITABLE_PATh = "/root/whatever/report.csv";
    private static final String TITLE = "fruit,quantity" + System.lineSeparator();
    private static final String APPLE_AND_QUANTITY = "apple,20" + System.lineSeparator();
    private static final String BANAN_AND_QUANTITY = "banana,50" + System.lineSeparator();
    private static final String ORANGE_AND_QUANTITY = "orange,30" + System.lineSeparator();
    private static final String REPORT_CSV = "report.csv";

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @TempDir
    private Path tempDir;

    @BeforeEach
    void setUp() {
        writeToFileService = new WriterServiceImpl();
    }

    @Test
    public void writeToFile_Ok() throws IOException {
        String expectedReport = TITLE
                + APPLE_AND_QUANTITY
                + BANAN_AND_QUANTITY
                + ORANGE_AND_QUANTITY;
        File outputFile = tempDir.resolve(REPORT_CSV).toFile();
        FileWriter fileWriter = new FileWriter(outputFile);
        fileWriter.write(expectedReport);
        fileWriter.close();
        List<String> expectedLines = Arrays.asList(expectedReport.split(System.lineSeparator()));
        List<String> actualLines = Files.readAllLines(outputFile.toPath());
        assertLinesMatch(expectedLines, actualLines);
    }

    @Test
    void writeDataToFile_NonWritableLocation_notOk() {
        String report = TITLE
                + APPLE_AND_QUANTITY
                + BANAN_AND_QUANTITY
                + ORANGE_AND_QUANTITY;
        File nonWritableFile = new File(NO_WRITABLE_PATh);
        assertThrows(RuntimeException.class,
                () -> writeToFileService.writeReport(report, String.valueOf(nonWritableFile)));
    }

    @Test
    void writeDataToFile_CorrectContentAndPath_ok() throws IOException {
        String report = TITLE
                + APPLE_AND_QUANTITY
                + BANAN_AND_QUANTITY
                + ORANGE_AND_QUANTITY;
        Path outputPath = tempDir.resolve(REPORT_CSV);

        writeToFileService.writeReport(report, outputPath.toString());

        List<String> expectedLines = Arrays.asList(report.split(System.lineSeparator()));
        List<String> actualLines = Files.readAllLines(outputPath);
        assertEquals(expectedLines, actualLines);
    }
}
