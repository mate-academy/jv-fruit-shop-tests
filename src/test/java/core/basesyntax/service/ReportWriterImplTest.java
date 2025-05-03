package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReportWriterImplTest {
    private static final String FILE_NAME = "src/test/resources/writeTest.csv";
    private static final String WRONG_FILE_NAME = "src/test/resources/badFileName.csv";
    private List<String> lines = new ArrayList<>();
    private ReportWriter reportWriter = new ReportWriterImpl();

    @Before
    public void setUp() throws Exception {
        lines.add("type,fruit,quantity");
        lines.add("b,banana,20");
        lines.add("b,apple,100");
    }

    @After
    public void tearDown() {
        lines.clear();
        try {
            new File(FILE_NAME).delete();
            new File(WRONG_FILE_NAME).delete();
        } catch (Exception e) {
            throw new RuntimeException("Can't delete file", e);
        }
    }

    @Test
    public void writeToFile_validValues_ok() {
        reportWriter.writeToFile(lines, FILE_NAME);
        assertEquals(lines,readLinesFromFile(FILE_NAME));
    }

    @Test
    public void writeToFile_nullValue_notOk() {
        assertThrows(RuntimeException.class, () ->
                        reportWriter.writeToFile(null, FILE_NAME),
                "Can't write NULL to file by path: " + FILE_NAME);
    }

    @Test
    public void writeToFile_nullFileName_notOk() {
        String nullFileName = null;
        assertThrows(RuntimeException.class, () ->
                        reportWriter.writeToFile(lines, nullFileName),
                "Can't write data to file by path: " + nullFileName);
    }

    public List<String> readLinesFromFile(String filePath) {
        List<String> dataFromFile;
        try {
            dataFromFile = Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't find file by path: " + filePath, e);
        }
        return dataFromFile;
    }
}
