package core.basesyntax.service;

import org.junit.Test;

import static org.junit.Assert.*;

public class FileWriterServiceImplTest {
    @Test
    public void writeToFile_validFilePath_Ok() {
        FileWriterService fileWriterService = new FileWriterServiceImpl();
        String validFilePath = "/home/nata/Java/Projects/jv-fruit-shop-tests/testFile.csv";
        fileWriterService.writeToFile(validFilePath,"newTextForTest");

    }
}
