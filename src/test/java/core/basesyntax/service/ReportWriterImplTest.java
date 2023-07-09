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
    private String fileName = "src/main/resources/writeTest.csv";
    private String wrongFileName = "src/main/resources/badFileName.csv";
    private List<String> lines = new ArrayList<>();

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
            new File(fileName).delete();
            new File(wrongFileName).delete();
        } catch (Exception e) {
            throw new RuntimeException("Can't delete file", e);
        }
    }

    @Test
    public void writeToFile_validValues_ok() {
        new ReportWriterImpl().writeToFile(lines,fileName);
        assertEquals(lines,readLinesFromFile(fileName));
    }

    @Test
    public void writeToFile_nullValue_notOk() {
        assertThrows(RuntimeException.class, () ->
                        new ReportWriterImpl().writeToFile(null, fileName),
                "Can't write NULL to file by path: " + fileName);
    }

    @Test
    public void writeToFile_nullFileName_notOk() {
        String nullFileName = null;
        assertThrows(RuntimeException.class, () ->
                        new ReportWriterImpl().writeToFile(lines, nullFileName),
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
