package core.basesyntax.service.csvfileservice;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileWriterServiceImplTest {
    private static final String NO_FILE_TO_WRITE = "src/...";
    private static final String FILE_TO_WRITE = "src/main/resources/output_test_data.csv";

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    private FileWriterService fileWriterService = new FileWriterServiceImpl();
    private String fruitsReport = "fruit,quantity" + System.lineSeparator()
            + "banana,100" + System.lineSeparator()
            + "orange,70" + System.lineSeparator()
            + "apple,50";

    @Test
    public void writeToFileNotExisted_notOk() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Can't write data to the file " + NO_FILE_TO_WRITE);
        fileWriterService.writeToFile(fruitsReport, NO_FILE_TO_WRITE);
    }

    @Test
    public void writeToFile_Ok() {
        fileWriterService.writeToFile(fruitsReport, FILE_TO_WRITE);
    }
}
