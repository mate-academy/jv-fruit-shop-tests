package core.basesyntax.transaction;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

class TransactionConverterImplTest {
    private TransactionConverter transactionConverter;
    List<String> lines = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        transactionConverter = new TransactionConverterImpl();
    }

    @AfterEach
    public void tearDown() throws IOException {
        lines.clear();
        Storage.storage.clear();
    }

    @Test
    void testWriteToStorageWithNullContent() {
        assertThrows(RuntimeException.class, () -> transactionConverter.convert(null));
    }

    @Test
    void testWriteToStorageWithEmptyContent() {
        assertDoesNotThrow(() -> transactionConverter.convert(lines));
    }

    @Test
    void testWriteToStorageOneElement() {
        lines.add("type,fruit,quantity");
        assertDoesNotThrow(() -> transactionConverter.convert(lines));
    }
}