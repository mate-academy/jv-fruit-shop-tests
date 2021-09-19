package core.basesyntax;

import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileWriterServiceTest {
    private static final String OUTPUT_FILE_NAME = "src/main/resources/report.csv";
    private static FileWriterService fileWriterService;

    @BeforeAll
    public static void initialize() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void readingInformationFromFile_Ok() {
        String text = "fruit,quantity\nbanana,152\napple,90";
        fileWriterService.writeToFile(text, OUTPUT_FILE_NAME);
    }
}
