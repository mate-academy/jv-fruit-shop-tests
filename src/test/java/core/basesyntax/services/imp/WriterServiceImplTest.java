package core.basesyntax.services.imp;

import core.basesyntax.services.FileWriteService;
import org.junit.Test;

public class WriterServiceImplTest {
    private final FileWriteService writerService = new WriterServiceImpl();

    @Test(expected = RuntimeException.class)
    public void not_ExistReport() {
        String filePath = "abc";
        String report = null;
        writerService.writeReportToFile(filePath, report);
    }

    @Test(expected = RuntimeException.class)
    public void not_ExistFilePath() {
        String filePath = null;
        String report = "abc";
        writerService.writeReportToFile(filePath, report);
    }

    @Test
    public void write_correctFile_OK() {
        String filePath = "abc";
        String report = "def";
        writerService.writeReportToFile(filePath, report);
    }

}
