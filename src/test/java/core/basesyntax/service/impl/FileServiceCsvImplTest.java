package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileServiceCsvImplTest {
    private FileService fileService;
    private String testTransactionsFileName = "src/test/resources/file.csv";
    private String testReportFileName = "src/test/resources/report.csv";
    private String content = "type,fruit,quantity\nb,banana,20\n";
    private String reportString = "fruit,quantity\nbanana,20";
    private File transactionsFile = new File(testTransactionsFileName);
    private File reportFile = new File(testReportFileName);

    @BeforeEach
    void setUp() {
        fileService = new FileServiceCsvImpl();
        try {
            transactionsFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            Files.write(transactionsFile.toPath(), content.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void tearDown() {
        transactionsFile.delete();
        reportFile.delete();
    }

    @Test
    void readFile_validFile_Ok() {
        List<String> stringList = fileService.readFile(testTransactionsFileName);
        assertEquals(2, stringList.size(), "Two lines expected");
    }

    @Test
    void readFile_emptyFile_notOK() {
        try {
            Files.write(transactionsFile.toPath(), new ArrayList<>());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertThrows(RuntimeException.class,
                () -> fileService.readFile(testTransactionsFileName),
                "File containing transactions is expected, but was empty");
    }

    @Test
    void writeFile_FileNotEmpty_Ok() {
        fileService.writeFile(reportString, testReportFileName);
        try {
            assertTrue(Files.size(reportFile.toPath()) > 0, "File should not be empty");
        } catch (IOException e) {
            throw new RuntimeException("Cannot read file", e);
        }
        try {
            Files.deleteIfExists(Path.of(testReportFileName));
        } catch (IOException e) {
            throw new RuntimeException("File not found", e);
        }
    }

    @Test
    void writeFile_FileCreated_Ok() {
        fileService.writeFile(reportString, testReportFileName);
        assertTrue(Files.exists(reportFile.toPath()), "Report should be created");
        try {
            Files.deleteIfExists(Path.of(testReportFileName));
        } catch (IOException e) {
            throw new RuntimeException("File not found", e);
        }
    }

    @Test
    void readFile_FileNotExist_NotOk() {
        try {
            Files.deleteIfExists(Path.of(testTransactionsFileName));
        } catch (IOException e) {
            throw new RuntimeException("File not found", e);
        }
        assertThrows(RuntimeException.class,
                () -> fileService.readFile(testTransactionsFileName),
                "RuntimeException is expected");
    }
}
