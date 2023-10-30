package core.basesyntax.serviceimpl.transactionimpl;

import core.basesyntax.strategy.serviceintrface.operation.model.FruitTransaction;
import core.basesyntax.strategy.serviceintrface.transaction.TransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FruitTransactionParserImplTest {
    private TransactionParser fruitTransaction = new FruitTransactionParserImpl();

    @Test
    void check_Data_Null_Exception() {
        List<String> dataList = null;
        Assertions.assertThrows(RuntimeException.class, ()
                -> fruitTransaction.parseTransactions(dataList));
    }

    @Test
    void check_InputData_Empty_Exception() {
        List<String> dataList = new ArrayList<>();
        Assertions.assertThrows(RuntimeException.class, ()
                -> fruitTransaction.parseTransactions(dataList));
    }

    @Test
    void check_IncorrectInputData_Exception() {
        List<String> dataList = new ArrayList<>();
        dataList.add("1");
        dataList.add("2");
        Assertions.assertThrows(RuntimeException.class, ()
                -> fruitTransaction.parseTransactions(dataList));
    }

    @Test
    void check_InputData_OutputData_Value_OK() {
        List<String> dataList = new ArrayList<>();
        dataList.add("b,banana,20");
        List<FruitTransaction> fruitTransactionList = fruitTransaction.parseTransactions(dataList);
        Assertions.assertEquals(dataList.size(), fruitTransactionList.size());
        Assertions.assertEquals(FruitTransaction.Operation.BALANCE,
                fruitTransactionList.get(0).getOperation());
        Assertions.assertEquals(20, fruitTransactionList.get(0).getQuantity());
        Assertions.assertEquals("banana", fruitTransactionList.get(0).getFruit());
    }

}
