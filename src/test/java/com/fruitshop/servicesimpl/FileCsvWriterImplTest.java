package com.fruitshop.servicesimpl;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fruitshop.services.FileCsvWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileCsvWriterImplTest {

    private final FileCsvWriter writer = new FileCsvWriterImpl();
    private final File fileToWriteIn = new File("src/test/resources/testFileToWRiteIn.csv");
    private final File wrongPath = new File("src/wrongpath/resources/wrongFile.csv");
    private String message;

    @BeforeEach
    void setUp() throws IOException {
        fileToWriteIn.createNewFile();
        message = "balance, return supply";
    }

    @AfterEach
    void tearDown() {
        fileToWriteIn.delete();
    }

    @Test
    void writeInFile_Ok() throws IOException {
        writer.writeInFile(message, fileToWriteIn.getPath());
        assertTrue(Files.readAllLines(fileToWriteIn.toPath())
                .stream().allMatch(x -> x.contains(message)));
    }

    @Test
    void writeInFIle_WrongPath() {
        assertThrows(RuntimeException.class, () -> writer.writeInFile(message,wrongPath.getPath()));
    }
}
