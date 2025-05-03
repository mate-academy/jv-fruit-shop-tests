package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private FileWriter fileWriter;
    private Path tempFile;

    @BeforeEach
    void beforeEach() {
        fileWriter = new FileWriterImpl();
        try {
            tempFile = Files.createTempFile("report",".csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void afterEach() {
        try {
            Files.delete(tempFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void write_correctFile_Ok() {
        String data = "fruit,quantity\napple,5";
        fileWriter.write(data,tempFile.toString());
        List<String> listFromTestFile = null;
        try {
            listFromTestFile = Files.readAllLines(tempFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals("fruit,quantity", listFromTestFile.get(0));
        Assertions.assertEquals("apple,5", listFromTestFile.get(1));
    }

    @Test
    void write_incorrectFile_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fileWriter.write("report","/invalid/path/file.csv"));
    }
}
