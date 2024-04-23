package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static FileWriter fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void fileWriter_validFile_Ok() {
        fileWriter.write(
                "fruit,quantity\nbanana,117\napple,30",
                "test_OutPut"
        );
        Path newFile = Paths.get("src/main/resources/test_OutPut.csv");
        Path checkFile = Paths.get("src/test/resources/checkFile.csv");
        try {
            List<String> newContent = Files.readAllLines(newFile);
            List<String> checkContent = Files.readAllLines(checkFile);
            assertEquals(newContent,checkContent);
        } catch (IOException e) {
            throw new RuntimeException("Unable to read the file: " + e);
        }
    }
}
