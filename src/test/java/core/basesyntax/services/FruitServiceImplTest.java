package core.basesyntax.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import utilities.Constants;

public class FruitServiceImplTest {
    private static final String FIRST_PATH_RAW_REPORT =
            "src/test/resources/SampleSourceCSVSmallQuantities.csv";
    private static final String FIRST_PATH_RAW_REPORT_NAME = "SampleSourceCSVSmallQuantities";
    private static final String SECOND_PATH_RAW_REPORT =
            "src/test/resources/SampleSourceCSV2BiggerQuantities.csv";
    private static final String SECOND_PATH_RAW_REPORT_NAME = "SampleSourceCSV2BiggerQuantities";
    private static final String THIRD_PATH_RAW_REPORT =
            "src/test/resources/SampleSourceCSV3ExtremelyBigQuantities.csv";
    private static final String THIRD_PATH_RAW_REPORT_NAME =
            "SampleSourceCSV3ExtremelyBigQuantities";
    private static final String FIRST_CURRENT_REPORT_NAME =
            Constants.FILE_PATH + FIRST_PATH_RAW_REPORT_NAME + Constants.REPORT_TIME;
    private static final String SECOND_CURRENT_REPORT_NAME =
            Constants.FILE_PATH + SECOND_PATH_RAW_REPORT_NAME + Constants.REPORT_TIME;
    private static final String THIRD_CURRENT_REPORT_NAME =
            Constants.FILE_PATH + THIRD_PATH_RAW_REPORT_NAME + Constants.REPORT_TIME;
    private static final String SMALL_QUANTITIES_EXPECTED_RESULT =
            "fruit,quantity" + System.lineSeparator()
                    + "banana,150" + System.lineSeparator()
                    + "apple,262";
    private static final String BIGGER_QUANTITIES_EXPECTED_RESULT =
            "fruit,quantity" + System.lineSeparator()
                    + "banana,30000" + System.lineSeparator()
                    + "apple,52500";
    private static final String EXTREMELY_BIG_QUANTITIES_EXPECTED_RESULT =
            "fruit,quantity" + System.lineSeparator()
                    + "banana,2080000000" + System.lineSeparator()
                    + "apple,2042500000";

    @AfterEach
    void clearStorage() {
        Storage.getFruits().clear();
    }

    @Test
    void processReport_thenRetrieveIt_Ok1() {
        FruitService.initVars(FIRST_PATH_RAW_REPORT);
        FruitService.processReport(FIRST_PATH_RAW_REPORT_NAME);
        checkWrittenData(FIRST_CURRENT_REPORT_NAME, SMALL_QUANTITIES_EXPECTED_RESULT);
    }

    @Test
    void processReport_thenRetrieveIt_Ok2() {
        FruitService.initVars(SECOND_PATH_RAW_REPORT);
        FruitService.processReport(SECOND_PATH_RAW_REPORT_NAME);
        checkWrittenData(SECOND_CURRENT_REPORT_NAME, BIGGER_QUANTITIES_EXPECTED_RESULT);
    }

    @Test
    void processReport_thenRetrieveIt_Ok3() {
        FruitService.initVars(THIRD_PATH_RAW_REPORT);
        FruitService.processReport(THIRD_PATH_RAW_REPORT_NAME);
        checkWrittenData(THIRD_CURRENT_REPORT_NAME, EXTREMELY_BIG_QUANTITIES_EXPECTED_RESULT);
    }

    private void checkWrittenData(String currentReportName, String expectedResult) {
        Path path = Paths.get(currentReportName);
        List<String> actualContent;
        try {
            actualContent = Files.readAllLines(path);
        } catch (IOException ioe) {
            throw new RuntimeException("Can't read from file " + currentReportName, ioe);
        }
        assertEquals(expectedResult,
                String.join(System.lineSeparator(), actualContent));
    }
}
