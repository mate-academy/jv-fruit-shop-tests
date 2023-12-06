package core.basesyntax;

import core.basesyntax.db.FruitTransactionDb;
import core.basesyntax.db.FruitTransactionDbImpl;
import core.basesyntax.service.file.actions.ReaderFile;
import core.basesyntax.service.file.actions.WriterFile;
import core.basesyntax.service.file.actions.impl.ReaderFileImpl;
import core.basesyntax.service.file.actions.impl.WriterFileImpl;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class WriterFileTest {
    private static final String REPORT_FILE_PATH = "src/test/java/core/basesyntax/resources"
            + "/reportTest.csv";
    private static final String WRONG_FILE_PATH = "invalid/path/that/does/not/exist.csv";
    private static final List<String> EXPECTED_REPORT = List.of("banana,152", "apple,90");
    private FruitTransactionDb fruitStore;
    private ReaderFile reader = new ReaderFileImpl();
    private WriterFile writer = new WriterFileImpl();

    @Test
    public void getIoException_notOk() {
        fruitStore = new FruitTransactionDbImpl();

        Assert.assertThrows(RuntimeException.class, () -> writer
                .writeReportFruitShop(fruitStore, WRONG_FILE_PATH));
    }

    @Test
    public void writeDataReport_Ok() {
        fruitStore = new FruitTransactionDbImpl();
        fruitStore.addQuantity("banana", 152);
        fruitStore.addQuantity("apple", 90);

        writer.writeReportFruitShop(fruitStore, REPORT_FILE_PATH);

        List<String> actualReport = reader.readFileFruitShop(REPORT_FILE_PATH);
        Assert.assertEquals(EXPECTED_REPORT, actualReport);
    }
}
