import core.basesyntax.checker.Checker;
import core.basesyntax.db.InMemoryStorage;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.Converter;
import core.basesyntax.service.TransactionDao;
import core.basesyntax.service.impl.ConverterCsvImpl;
import core.basesyntax.service.impl.TransactionDaoImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConverterCsvTest {
    private Storage storage;
    private Checker checker;
    private TransactionDao dao;
    private Converter converter;
    
    @BeforeEach
    public void setup() {
        storage = new InMemoryStorage();
        checker = new Checker(storage);
        dao = new TransactionDaoImpl(storage, checker);
        converter = new ConverterCsvImpl(dao);
    }
    
    @Test
    public void convertCsvLines_correctLines_ok() {
        List<String> lines = new ArrayList<>();
        lines.add("type,fruit,quantity");
        lines.add("b,banana,10");
        lines.add("s,apple,20");
        lines.add("r,orange,30");
        
        List<Transaction> transactions = converter.convertCsvLines(lines);
        
        Assertions.assertEquals(3, transactions.size());
        
        Assertions.assertEquals(Transaction.Operation.BALANCE, transactions.get(0).getOperation());
        Assertions.assertEquals("banana", transactions.get(0).getType());
        Assertions.assertEquals(10, transactions.get(0).getQuantity());

        Assertions.assertEquals(Transaction.Operation.SUPPLY, transactions.get(1).getOperation());
        Assertions.assertEquals("apple", transactions.get(1).getType());
        Assertions.assertEquals(20, transactions.get(1).getQuantity());

        Assertions.assertEquals(Transaction.Operation.RETURN, transactions.get(2).getOperation());
        Assertions.assertEquals("orange", transactions.get(2).getType());
        Assertions.assertEquals(30, transactions.get(2).getQuantity());
    }
    
    @Test
    public void convertCsvLines_emptyLines_notOk() {
        List<String> lines = new ArrayList<>();
        Assertions.assertThrows(IllegalArgumentException.class, 
                () -> converter.convertCsvLines(lines));
    }
    
    @Test
    public void convertCsvLines_nullLines_notOk() {
        List<String> lines = null;
        Assertions.assertThrows(IllegalArgumentException.class, 
                () -> converter.convertCsvLines(lines));
    }
    
    @Test
    public void convertCsvLines_wrongLength_notOk() {
        List<String> lines = new ArrayList<>();
        lines.add("type,fruit,quantity");
        lines.add("b,banana,10,30");
        
        Assertions.assertThrows(IllegalArgumentException.class, 
                () -> converter.convertCsvLines(lines));
    }

    @Test
    public void convertCsvLines_wrongOperation_notOk() {
        List<String> lines = new ArrayList<>();
        lines.add("type,fruit,quantity");
        lines.add("x,banana,10");

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> converter.convertCsvLines(lines));
    }
}
