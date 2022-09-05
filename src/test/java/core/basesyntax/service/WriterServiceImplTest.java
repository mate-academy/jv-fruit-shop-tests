package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String DIRECTORY = "src/test/resources";
    private static final String FILE_NAME_ACTUAL_REPORT = "src/test/resources/actualReport.csv";
    private static final String REPORT = "fruit,quantity"
            + System.lineSeparator()
            + "banana,152"
            + System.lineSeparator()
            + "apple,90";
    private static WriterService writerService;

    @BeforeClass
    public static void beforeClass() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void write_correctReport_ok() throws IOException {
        writerService.writeToFile(REPORT, FILE_NAME_ACTUAL_REPORT);
        List<String> lines = Files.readAllLines((Paths.get(FILE_NAME_ACTUAL_REPORT)));
        StringBuilder builderActual = new StringBuilder();
        for (String value : lines) {
            builderActual.append(value).append(System.lineSeparator());
        }
        assertEquals(REPORT, builderActual.toString().trim());
    }

    @Test (expected = RuntimeException.class)
    public void write_nullReport_notOk() {
        File file = new File(DIRECTORY, "test.txt");
        writerService.writeToFile(null, file.getName());
    }

    @Test (expected = RuntimeException.class)
    public void write_nullFile_notOk() {
        writerService.writeToFile(REPORT, null);
    }

    @Test (expected = RuntimeException.class)
    public void write_invalidFileName_notOk() {
        String invalidFileName = "";
        writerService.writeToFile(REPORT, invalidFileName);
    }
}
