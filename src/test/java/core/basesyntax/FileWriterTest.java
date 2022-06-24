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
    public void write_File_OK() throws IOException, CsvException {
        List<String[]> expected = new ArrayList<>();
        expected.add(new String[]{"fruit", "quantity"});
        expected.add(new String[]{"banana", "105"});
        expected.add(new String[]{"apple", "120"});

        fileWriterService.write(new File(RESULT_FILE), expected);

        List<String[]> actual = new ArrayList<>();
        try {
            List<String> tempActual = Files.readAllLines(Path.of(RESULT_FILE));
            for (String line : tempActual) {
                actual.add(line.split(","));
            }
        } catch (IOException e) {
            throw new RuntimeException("somethin went wrong, not sure what: " + e);
        }

        for (int i = 0; i < expected.size(); i++) {
            String[] lineFromReport = expected.get(i);
            String[] lineFromFileWrited = actual.get(i);
            for (int j = 0; j < lineFromReport.length; j++) {
                assertTrue(lineFromReport[j].equals(lineFromFileWrited[j]));
            }
        }

    }
}
