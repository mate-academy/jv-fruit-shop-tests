package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.ReaderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReaderImplTest {
    private static final String CORRECT_TRANSACTIONS_FILE_PATH =
            "src/main/resources/transactions.csv";
    private static final String INCORRECT_TRANSACTIONS_FILE_PATH =
            "src/main/resources/incorrect-transactions.csv";

    private Reader reader;

    @BeforeEach
    void setUp() {
        reader = new ReaderImpl();
    }

    @Test
    public void read_correctFilePath_ok() {
        reader.read(CORRECT_TRANSACTIONS_FILE_PATH);
    }

    @Test
    public void read_incorrectFilePath_notOk() {
        assertThrows(RuntimeException.class,
                () -> reader.read(INCORRECT_TRANSACTIONS_FILE_PATH),
                "Incorrect path to file: " + INCORRECT_TRANSACTIONS_FILE_PATH);
    }
}
