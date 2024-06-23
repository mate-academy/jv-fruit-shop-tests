package core.basesyntax.service.impl;

import static org.junit.Assert.assertThrows;

import java.io.File;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    public static final String EXISTING_REPORT = "src/main/resources/files/reportToRead.csv";
    public static final String EMPTY_REPORT = "src/main/resources/files/emptyFile.csv";
    public static final String NON_EXISTING_FILE = "src/main/resources/files/nonExistingFile.csv";
    private static FileReaderImpl fileReader;
    private static List<String> report;

    @BeforeAll
    static void beforeAll() {
        fileReader = new FileReaderImpl();
        report = List.of("type,fruit,quantity", "b,banana,20", "b,apple,100",
                "s,banana,100", "p,banana,13", "r,apple,10",
                "p,apple,20", "p,banana,5", "s,banana,50");
    }

    @Test
    void read_existingReport_ok() {
        List<String> containText = fileReader.read(new File(EXISTING_REPORT));
        Assertions.assertEquals(report, containText);
    }

    @Test
    void read_nonExistingReport_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileReader.read(new File(NON_EXISTING_FILE)));
    }

    @Test
    void read_emptyFile_ok() {
        File empty = new File(EMPTY_REPORT);
        List<String> expected = Collections.emptyList();
        List<String> actual = fileReader.read(empty);
        Assertions.assertEquals(expected, actual);
    }
}
