package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.FileWriter;

class FileWriterImplTest {
    private static final String PATH = "src/test/java/resources/finalReportTest.csv";
    private static final String CONTENT = "fruit,quantity"
            + System.lineSeparator()
            + "apple,104"
            + System.lineSeparator()
            + "banana,57";
    private FileWriter fileWriter;
    private File file;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
        file = new File(PATH);
    }

    @Test
    void writeFile_pathIsNull_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.writeFile(null, CONTENT));
    }

    @Test
    void writeFile_pathIsIncorrect_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter
                        .writeFile("src/test/resources/ReportTest.csv",
                                CONTENT));
    }

    @Test
    void writeFile_validReport_Ok() throws IOException {
        fileWriter.writeFile(PATH, CONTENT);
        List<String> stringList = Files.readAllLines(file.toPath());
        assertEquals("fruit,quantity", stringList.get(0));
        assertEquals("banana,57", stringList.get(2));
    }
}
