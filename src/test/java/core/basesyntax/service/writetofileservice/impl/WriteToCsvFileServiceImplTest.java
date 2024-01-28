package core.basesyntax.service.writetofileservice.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.writetofileservice.WriteToFileService;
import java.io.File;
import org.junit.jupiter.api.Test;

class WriteToCsvFileServiceImplTest {
    private final WriteToFileService writeToFileService = new WriteToCsvFileServiceImpl();

    @Test
    void createFileWithReportIsCorrect_ok() {
        String reportForAddToFile = "fruit,quantity\r\norange,50\r\nbanana,80\r\nblueberry,20\r\n";
        writeToFileService.writeDataToFile(reportForAddToFile);
        String filePath = "src/main/resources/report_file.csv";
        File file = new File(filePath);
        assertTrue(file.exists());
    }
}
