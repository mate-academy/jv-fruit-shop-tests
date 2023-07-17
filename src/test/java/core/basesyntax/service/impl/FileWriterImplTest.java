package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriterService;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private FileWriterService writer = new FileWriterImpl();

    @Test
    void writeReportToFile_CheckFileNameIsNull_NotOk() {
        String outputFileName = null;
        assertThrows(RuntimeException.class,
                () -> writer.writeReportToFile("content", outputFileName));
    }

    @Test
    void writeReportToFile_Ok() {
        String inputFile = "src/test/resources/test_2_input_file.csv";
        String content = "Test content\n";
        FileWriterService fileWriter = new FileWriterImpl();
        fileWriter.writeReportToFile(content, inputFile);
        FileReaderResult fileReader = new FileReaderImpl().readFile(inputFile);
        String actualContent = fileReader.getContent();
        assertEquals(content, actualContent);
    }
}
