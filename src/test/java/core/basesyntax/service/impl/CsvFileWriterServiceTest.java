package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvFileWriterServiceTest {

    private static CsvFileWriterService fileWriter;
    private static final String PATH = "src/test/resources/output-ok.csv";

    @BeforeAll
    static void beforeAll() {
        fileWriter = new CsvFileWriterService();
    }

    @BeforeEach
    void setUp() {
        File createdFile = new File(PATH);
        if (createdFile.exists()) {
            createdFile.delete();
        }
    }

    @Test
    void writeToFile_Ok() {
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"one", "two", "three"});
        fileWriter.writeToFile(PATH, data);
        File result = new File(PATH);
        assertTrue(result.exists());
    }

    @AfterEach
    void tearDown() {
        File createdFile = new File(PATH);
        if (createdFile.exists()) {
            createdFile.delete();
        }
    }
}
