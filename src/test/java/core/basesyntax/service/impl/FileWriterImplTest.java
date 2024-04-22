package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileWriterImplTest {
    private static FileWriter fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void validFile_OK() {
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