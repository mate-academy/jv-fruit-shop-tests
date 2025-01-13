package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String TEMP_DIRECTORY = "src" + File.separator
            + "test" + File.separator
            + "resources" + File.separator;
    private static final String FILE_NAME = TEMP_DIRECTORY + "finalReport.csv";
    private static final Path FILE_PATH = Path.of(FILE_NAME);
    private static final String EMPTY_REPORT_TO_SAVE = "";
    private static final String REPORT_TO_SAVE = """
                type,fruit,quantity
                b,banana,20
                b,apple,100
                s,banana,100
                p,banana,115
                r,apple,10
                p,apple,20
                p,banana,5
                s,banana,50""";
    private static final List<String> EXPECTED_RESULT = new ArrayList<>();
    private static final List<String> EMPTY_RESULT = new ArrayList<>();

    @BeforeEach
    void setUp() {
        EXPECTED_RESULT.add("type,fruit,quantity");
        EXPECTED_RESULT.add("b,banana,20");
        EXPECTED_RESULT.add("b,apple,100");
        EXPECTED_RESULT.add("s,banana,100");
        EXPECTED_RESULT.add("p,banana,115");
        EXPECTED_RESULT.add("r,apple,10");
        EXPECTED_RESULT.add("p,apple,20");
        EXPECTED_RESULT.add("p,banana,5");
        EXPECTED_RESULT.add("s,banana,50");
    }

    @AfterEach
    void reset() {
        EXPECTED_RESULT.clear();
    }

    @Test
    void saveReport_OK() {
        FileWriter fileWork = new FileWriterImpl(FILE_NAME);
        fileWork.write(REPORT_TO_SAVE);
        try {
            List<String> actualResult = Files.readAllLines(FILE_PATH);
            Assertions.assertEquals(EXPECTED_RESULT, actualResult);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void saveEmptyReport_OK() {
        FileWriter fileWork = new FileWriterImpl(FILE_NAME);
        fileWork.write(EMPTY_REPORT_TO_SAVE);
        try {
            List<String> actualResult = Files.readAllLines(FILE_PATH);
            Assertions.assertEquals(EMPTY_RESULT, actualResult);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void throwsRuntimeExceptionsWhileFileNameIsNull_OK() {
        FileWriter fileWork = new FileWriterImpl(FILE_NAME);
        Assertions.assertThrows(RuntimeException.class, () -> fileWork.write(null));
    }
}
