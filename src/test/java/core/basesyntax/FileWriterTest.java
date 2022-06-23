package core.basesyntax;

import static org.junit.Assert.assertTrue;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.impl.FileWriterImpl;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;

public class FileWriterTest {
    private static final String EXPECTED_FILE = "src/test/resources/result.csv";
    private static final String RESULT_FILE = "src/test/resources/testwriter.csv";
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void beforeAll() {
        fileWriterService = new FileWriterImpl();
    }

    @AfterEach
    public void deleteCreatedFile() throws IOException {
        Files.delete(Path.of(RESULT_FILE));
    }

    @Test
    public void wrightFile_OK() throws IOException, CsvException {
        List<String[]> reportToWrite = new ArrayList<>();
        reportToWrite.add(new String[]{"fruit", "quantity"});
        reportToWrite.add(new String[]{"banana", "105"});
        reportToWrite.add(new String[]{"apple", "120"});

        fileWriterService.write(new File(RESULT_FILE), reportToWrite);

        List<String[]> fileWritedList = new CSVReader(new FileReader(RESULT_FILE)).readAll();

        for (int i = 0; i < reportToWrite.size(); i++) {
            String[] fromReport = reportToWrite.get(i);
            String[] fromFileWrited = fileWritedList.get(i);
            for (int j = 0; j < fromReport.length; j++) {
                assertTrue(fromReport[j].equals(fromFileWrited[j]));
            }
        }
    }
}
