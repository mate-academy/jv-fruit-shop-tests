package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriterService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String REPORT = "fruit,quantity, banana,152, apple,90";
    private static final String HEADER = "fruit,quantity";
    private static final String FIRST_FRUIT = "banana,152";
    private static final String SECOND_FRUIT = "apple,90";
    private static FileWriterService fileWriterService;
    private static String pathToWriteFileOk;
    private static String pathToWriteFileNotOk;
    private static List<String> expected;

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl();
        pathToWriteFileOk = "src"
                + File.separator + "main"
                + File.separator + "resources"
                + File.separator + "inputFile.csv";
        pathToWriteFileNotOk = "";
        expected = new ArrayList<>();
        expected.add(HEADER);
        expected.add(FIRST_FRUIT);
        expected.add(SECOND_FRUIT);
    }

    @Test
    public void writeReportAndAudit_Ok() {
        File file = new File(pathToWriteFileOk);
        fileWriterService.writeToFile(pathToWriteFileOk, REPORT);
        List<String> actual;
        try {
            actual = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can not read file: " + pathToWriteFileOk, e);
        }
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void writeFile_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriterService.writeToFile(pathToWriteFileNotOk, REPORT));
    }
}
