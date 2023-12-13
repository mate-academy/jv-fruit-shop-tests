package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.WriteToFileServiceImpl;
import org.junit.jupiter.api.Test;

public class WriteToFileServiceTest {

    private WriterToFileService writerToFileService = new WriteToFileServiceImpl();

    @Test
    void writeReportToFile_notValidPath_notOk() {
        String path = "notValid";
        assertThrows(RuntimeException.class, () -> {
            writerToFileService.writeReportToFile(path);
        });
    }
}
