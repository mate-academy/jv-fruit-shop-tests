package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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

    @Test
    void writeToFile_correctPath_Ok() throws IOException {
        fileWriterService.writeToFile(report, outFileName);
        byte[] f1 = Files.readAllBytes(file.toPath());
        byte[] f2 = Files.readAllBytes(comparedFile.toPath());
        assertTrue(file.exists());
        assertArrayEquals(f1, f2);
    }

    @Test
    void writeToFile_fileNull_notOk() {
        Assertions.assertThrows(RuntimeException.class, ()
                -> fileWriterService.writeToFile(report,null));
    }

    @Test
    void writeToFile_reportNull_notOk() {
        Assertions.assertThrows(RuntimeException.class, ()
                -> fileWriterService.writeToFile(null,outFileName));
    }

    @Test
    void writeToFile_fileEmpty_notOk() {
        Assertions.assertThrows(RuntimeException.class, ()
                -> fileWriterService.writeToFile(report,""));
    }

    @Test
    void writeToFile_reportEmpty_notOk() {
        Assertions.assertThrows(RuntimeException.class, ()
                -> fileWriterService.writeToFile(report,""));
    }
}
