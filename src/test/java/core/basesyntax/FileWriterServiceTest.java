package core.basesyntax;

import static org.junit.Assert.assertThrows;

import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import org.junit.Test;

public class FileWriterServiceTest {
    private static final String FILE_PATH_NOT_EXIST = "src/main/files/report.csv";
    private final FileWriterService fileWriterService = new FileWriterServiceImpl();

    @Test
    public void readFromFile_NotExistFile_notOk() {
        String report = "wrong_report";
        assertThrows(RuntimeException.class, () -> {
            fileWriterService.writeToFile(FILE_PATH_NOT_EXIST, report);
        });
    }

}
