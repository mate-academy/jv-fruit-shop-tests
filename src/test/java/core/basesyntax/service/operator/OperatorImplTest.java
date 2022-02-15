package core.basesyntax.service.operator;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OperatorImplTest {
    private static final String INPUT_FILE_NAME = "src/main/resources/input.csv";
    private static final String OUTPUT_FILE_NAME = "src/main/resources/output.csv";
    private static final String FILES_TO_DELETE = "src/main/resources/*.*";
    private static final String RECOVER_INPUT_FILE_NAME = "src/test/resources/input.csv";
    private static final String RECOVER_OUTPUT_FILE_NAME = "src/test/resources/output.csv";
    private static final String INPUT_EMPTY_DATA_FILE_NAME
            = "src/test/resources/input_empty_data.csv";
    private static final String INPUT_NO_B_TRANSACTIONS_FILE_NAME
            = "src/test/resources/input_no_b_transactions.csv";
    private static final String INPUT_NORMAL_DATA_FILE_NAME
            = "src/test/resources/input_normal_data.csv";
    private static final String INPUT_ONLY_B_TRANSACTIONS_FILE_NAME
            = "src/test/resources/input_only_b_transactions.csv";
    private static final String INPUT_ONLY_MINUS_TRANSACTIONS_FILE_NAME
            = "src/test/resources/input_only_minus_transactions.csv";
    private static final Operator OPERATOR = new OperatorImpl();

    @Before
    public void setUp() {
        Storage.storage.clear();
    }

    @Test
    public void openShift_normalData_Ok() {
        inputFileSetup(INPUT_NORMAL_DATA_FILE_NAME);
        OPERATOR.openShift();
        int expected = 5;
        assertEquals(expected, Storage.storage.size());
    }

    @Test
    public void openShift_emptyData_Ok() {
        inputFileSetup(INPUT_EMPTY_DATA_FILE_NAME);
        OPERATOR.openShift();
        int expected = 0;
        assertEquals(expected, Storage.storage.size());
    }

    @Test
    public void openShift_noBTransactionsInput_Ok() {
        inputFileSetup(INPUT_NO_B_TRANSACTIONS_FILE_NAME);
        OPERATOR.openShift();
        int expected = 4;
        assertEquals(expected, Storage.storage.size());
    }

    @Test
    public void openShift_onlyBTransactionsInput_Ok() {
        inputFileSetup(INPUT_ONLY_B_TRANSACTIONS_FILE_NAME);
        OPERATOR.openShift();
        int expected = 4;
        assertEquals(expected, Storage.storage.size());
    }

    @Test
    public void openShift_onlyMinusTransactionsInput_Ok() {
        inputFileSetup(INPUT_ONLY_MINUS_TRANSACTIONS_FILE_NAME);
        OPERATOR.openShift();
        int expected = 0;
        assertEquals(expected, Storage.storage.size());
    }

    @Test
    public void closeShift_emptyDb_Ok() {
        inputFileSetup(INPUT_EMPTY_DATA_FILE_NAME);
        OPERATOR.openShift();
        OPERATOR.closeShift();
        List<String> outputList = readOutput();
        int expected = 1;
        assertEquals(expected, outputList.size());
    }

    @Test
    public void closeShift_normalData_Ok() {
        inputFileSetup(INPUT_NORMAL_DATA_FILE_NAME);
        OPERATOR.openShift();
        OPERATOR.closeShift();
        List<String> outputList = readOutput();
        int expected = 6;
        assertEquals(expected, outputList.size());
    }

    @After
    public void tearDown() throws Exception {
        try {
            Files.deleteIfExists(Path.of(FILES_TO_DELETE));
            Files.copy(Path.of(RECOVER_INPUT_FILE_NAME),
                    Path.of(INPUT_FILE_NAME), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Path.of(RECOVER_OUTPUT_FILE_NAME),
                    Path.of(OUTPUT_FILE_NAME), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Can't provide files recovering.");
        }
        Storage.storage.clear();
    }

    private void inputFileSetup(String fileName) {
        try {
            Files.copy(Path.of(fileName),
                    Path.of(INPUT_FILE_NAME), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Can't copy file '"
                    + fileName + "'", e);
        }
    }

    private List<String> readOutput() {
        List<String> outputList;
        try {
            outputList = Files.readAllLines(Path.of(OUTPUT_FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
        return outputList;
    }
}
