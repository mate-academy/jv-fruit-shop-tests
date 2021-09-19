package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String OUTPUT_FILE_NAME = "src/main/resources/report.csv";
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void initialize() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void readingInformationFromFile_Ok() {
        String text = "fruit,quantity\nbanana,152\napple,90";
        fileWriterService.writeToFile(text, OUTPUT_FILE_NAME);
    }
}
