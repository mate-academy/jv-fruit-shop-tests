package core.basesyntax.service.file.csv;

import core.basesyntax.service.csvfileservice.FileWriterService;
import core.basesyntax.service.csvfileservice.FileWriterServiceImpl;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileWriterServiceImplTest {
    private static final String NO_FILE_TO_WRITE = "";
    private static final String FILE_TO_WRITE = "src/test/resources/output_test_data.csv";
    private static final String FRUITS_REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana,100" + System.lineSeparator()
            + "orange,70" + System.lineSeparator()
            + "apple,50";
    private static FileWriterService fileWriterService;

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void writeToFile_fileNotExisted_notOk() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Can't write data to the file " + NO_FILE_TO_WRITE);
        fileWriterService.writeToFile(FRUITS_REPORT, NO_FILE_TO_WRITE);
    }

    @Test
    public void writeToFile_fileExisted_Ok() {
        fileWriterService.writeToFile(FRUITS_REPORT, FILE_TO_WRITE);
    }
}
