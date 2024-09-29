package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private FileWriter fileWriter;

    @Test
    void write_correctFile_Ok() throws IOException {
        fileWriter = new FileWriterImpl();
        Path testFile = Files.createTempFile("report",".csv");
        String data = "fruit,quantity\napple,5";
        fileWriter.write(data,testFile.toString());
        List<String> listFromTestFile = Files.readAllLines(testFile);
        Assertions.assertEquals("fruit,quantity", listFromTestFile.get(0));
        Assertions.assertEquals("apple,5", listFromTestFile.get(1));
        Files.delete(testFile);
    }

    @Test
    void write_incorrectFile_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fileWriter.write("input","invalidName"));
    }
}
