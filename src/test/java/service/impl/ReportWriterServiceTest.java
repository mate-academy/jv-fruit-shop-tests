package service.impl;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReportWriterServiceTest {
    @Test
    void writeDataToFile_ok() {
        ReportWriterServiceImpl reportWriterService = new ReportWriterServiceImpl();
        String actual = "fruit,quantity"
                + System.lineSeparator()
                + "banana,152"
                + System.lineSeparator()
                + "apple,90";
        String filePath = "src/test/java/resources/Report.csv";

        reportWriterService.writeToFile(filePath, actual);

        List<String> actualLines = new FileReaderService().readFromFile(filePath);
        String expectedLine1 = "banana,152";
        String expectedLine2 = "apple,90";

        Assertions.assertEquals(expectedLine1, actualLines.get(0));
        Assertions.assertEquals(expectedLine2, actualLines.get(1));
    }

    @Test
    void writeDataToFileWithWrongPathFile_notOk() {
        ReportWriterServiceImpl reportWriterService = new ReportWriterServiceImpl();
        String actual = "fruit,quantity"
                + System.lineSeparator()
                + "banana,152"
                + System.lineSeparator()
                + "apple,90";
        String filePath = "";

        Assertions.assertThrows(RuntimeException.class,
                () -> reportWriterService.writeToFile(filePath, actual));
    }
}
