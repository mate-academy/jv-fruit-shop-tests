package core.basesyntax.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
//TODO Continue create test for fileWriter,fruitService,reportService,TransactionService,Strategies
class FileWriterServiceImplTest {
    private static final String outFileName = "src/test/java/resources/testFile.csv";
    private static final String comparedFileName = "src/test/java/resources/comparedFile.csv";
    private static FileWriterServiceImpl fileWriterService;
    private static File file;
    private static File comparedFile;
    private String report = "fruits,quantity\r\nbanana,152\r\n";

    @BeforeAll
    static void beforeAll() {
        fileWriterService = new FileWriterServiceImpl();
        file = new File(outFileName);
        comparedFile = new File(comparedFileName);
    }

    @AfterEach
    void tearDown() {
        file.delete();
    }

    @Test
    void writeToFile_CorrectPath_Ok() throws IOException {
        fileWriterService.writeToFile(report, outFileName);
        byte[] f1 = Files.readAllBytes(file.toPath());
        byte[] f2 = Files.readAllBytes(comparedFile.toPath());
        assertTrue(file.exists());
        assertArrayEquals(f1, f2);
    }
}