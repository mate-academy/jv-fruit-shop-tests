package core.basesyntax;

import static org.junit.Assert.assertTrue;

import com.opencsv.exceptions.CsvException;
import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.impl.FileWriterImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterTest {
    private static final String RESULT_FILE = "src/test/resources/testwriter.csv";
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void beforeAll() {
        fileWriterService = new FileWriterImpl();
    }

    @After
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

        List<String[]> fileWrited = new ArrayList<>();
        try {
            List<String> resultWrited = Files.readAllLines(Path.of(RESULT_FILE));
            for (String line : resultWrited) {
                fileWrited.add(line.split(","));
            }
        } catch (IOException e) {
            throw new RuntimeException("somethin went wrong, not sure what: " + e);
        }

        for (int i = 0; i < reportToWrite.size(); i++) {
            String[] lineFromReport = reportToWrite.get(i);
            String[] lineFromFileWrited = fileWrited.get(i);
            for (int j = 0; j < lineFromReport.length; j++) {
                assertTrue(lineFromReport[j].equals(lineFromFileWrited[j]));
            }
        }

    }
}
