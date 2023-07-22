package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriterService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
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
        String content = "Test content";
        FileWriterService fileWriter = new FileWriterImpl();
        fileWriter.writeReportToFile(content, inputFile);
        File file = new File(inputFile);
        List<String> stringList;
        try {
            stringList = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
        String actualContent = String.join("", stringList);
        assertEquals(content, actualContent);
    }
}
