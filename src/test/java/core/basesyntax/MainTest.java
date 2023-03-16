package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.ReportMakerServiceImpl;
import core.basesyntax.service.impl.TransactionParserServiceImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.ActionStrategy;
import core.basesyntax.strategy.ActionStrategyImpl;
import core.basesyntax.strategy.actions.ActionHandler;
import core.basesyntax.strategy.actions.BalanceActionHandler;
import core.basesyntax.strategy.actions.PurchaseActionHandler;
import core.basesyntax.strategy.actions.ReturnActionHandler;
import core.basesyntax.strategy.actions.SupplyActionHandler;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class MainTest {

    @Test
    public void main_total_ok() {
        String from = "src/main/resources/test.csv";
        String to = "src/main/resources/out.csv";
        Main.main(from,
                to);
        try {
            Assert.assertEquals(Files.readString(Path.of(to)),
                    "fruit, quantity" + System.lineSeparator()
                            + "banana, 5" + System.lineSeparator()
                            + "apple, 25");

        } catch (IOException e) {
            throw new RuntimeException("Can't read the output file");
        }
    }

    @Test(expected = RuntimeException.class)
    public void reader_read_nonExistentFile_exception() {
        String from = "";
        new ReaderServiceImpl().read(from);
    }

    @Test
    public void reader_read_ok() {
        String from = "src/main/resources/test.csv";
        Assert.assertEquals(new ReaderServiceImpl().read(from),
                List.of("action,fruit,amount",
                        "b,banana,10",
                        "b,apple,20",
                        "p,banana,5",
                        "s,apple,5")
        );
    }

    @Test
    public void transactionParser_parse_ok() {
        List<String> read = new ArrayList<>();
        read.add("action,fruit,amount");
        read.add("b,banana,10");
        read.add("b,apple,20");
        read.add("p,banana,5");
        read.add("s,apple,5");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.getOperationByCode("b"),
                "banana",
                10));
        expected.add(new FruitTransaction(FruitTransaction.Operation.getOperationByCode("b"),
                "apple",
                20));
        expected.add(new FruitTransaction(FruitTransaction.Operation.getOperationByCode("p"),
                "banana",
                5));
        expected.add(new FruitTransaction(FruitTransaction.Operation.getOperationByCode("s"),
                "apple",
                5));
        List<FruitTransaction> actual = new TransactionParserServiceImpl().parse(read);
        for (int i = 0; i < expected.size(); i++) {
            Assert.assertEquals(expected.get(i), actual.get(i));
        }
    }

    @Test(expected = NullPointerException.class)
    public void transactionParser_parse_emptyInput_exception() {
        List<String> read = new ArrayList<>();
        new TransactionParserServiceImpl().parse(read);
    }

    @Test
    public void reporterMaker_report_ok() {
        Map<String, Integer> base = new HashMap<>();
        base.put("banana", 5);
        base.put("apple", 25);
        String expected = "fruit, quantity" + System.lineSeparator()
                + "banana, 5" + System.lineSeparator()
                + "apple, 25";
        String actual = new ReportMakerServiceImpl().report(base);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void writer_write_ok() {
        String toWrite = "fruit, quantity" + System.lineSeparator()
                + "banana, 5" + System.lineSeparator()
                + "apple, 25";
        String to = "src/main/resources/out.csv";
        new WriterServiceImpl().write(toWrite, to);
        try {
            Assert.assertEquals(toWrite, Files.readString(Path.of(to)));
        } catch (IOException e) {
            throw new RuntimeException("Can't read the output file");
        }
    }

    @Test(expected = RuntimeException.class)
    public void writer_write_nonExistentFile_exception() {
        new WriterServiceImpl().write("Writing something in the file",
                "");
    }

    @Test
    public void actionStrategy_get_ok() {
        ActionStrategy strategy =
                new ActionStrategyImpl(Map.of(
                        FruitTransaction.Operation.BALANCE, new BalanceActionHandler(),
                        FruitTransaction.Operation.PURCHASE, new PurchaseActionHandler(),
                        FruitTransaction.Operation.SUPPLY, new SupplyActionHandler(),
                        FruitTransaction.Operation.RETURN, new ReturnActionHandler()
                ));
        ActionHandler actual = strategy.get(FruitTransaction.Operation.getOperationByCode("b"));
        ActionHandler expected = new BalanceActionHandler();
        Assert.assertEquals(actual.getClass(), expected.getClass());
    }

    @Test(expected = RuntimeException.class)
    public void actionStrategy_get_invalidActionKey_notOk() {
        ActionStrategy strategy =
                new ActionStrategyImpl(Map.of(
                        FruitTransaction.Operation.BALANCE, new BalanceActionHandler(),
                        FruitTransaction.Operation.PURCHASE, new PurchaseActionHandler(),
                        FruitTransaction.Operation.SUPPLY, new SupplyActionHandler(),
                        FruitTransaction.Operation.RETURN, new ReturnActionHandler()
                ));
        strategy.get(FruitTransaction.Operation.getOperationByCode("k"));
    }

    @Test
    public void balanceAction_ok() {
        Storage.getFruits().clear();
        new BalanceActionHandler().apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana",
                10));
        Assert.assertEquals(10L, (long) Storage.getFruits().get("banana"));
    }

    @Test
    public void purchaseAction_ok() {
        Storage.getFruits().clear();
        new BalanceActionHandler().apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",
                        10));
        new PurchaseActionHandler().apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",
                        5));
        Assert.assertEquals(5L, (long) Storage.getFruits().get("banana"));
    }

    @Test(expected = RuntimeException.class)
    public void purchaseAction_nonExistentFruit_notOk() {
        Storage.getFruits().clear();
        new BalanceActionHandler().apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",
                        10));
        new PurchaseActionHandler().apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "apple",
                        5));
    }

    @Test(expected = RuntimeException.class)
    public void purchaseAction_purchaseTooMuch_notOk() {
        Storage.getFruits().clear();
        new BalanceActionHandler().apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",
                        10));
        new PurchaseActionHandler().apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",
                        15));
    }

    @Test
    public void returnAction_ok() {
        Storage.getFruits().clear();
        new BalanceActionHandler().apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",
                        10));
        new ReturnActionHandler().apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",
                        5));
        Assert.assertEquals(15L, (long) Storage.getFruits().get("banana"));
    }

    @Test
    public void returnAction_supplyingNonExistent_ok() {
        Storage.getFruits().clear();
        new ReturnActionHandler().apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "apple",
                        5));
        Assert.assertEquals(5L, (long) Storage.getFruits().get("apple"));
    }

    @Test
    public void supplyAction_ok() {
        Storage.getFruits().clear();
        new BalanceActionHandler().apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "apple",
                        10));
        new SupplyActionHandler().apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "apple",
                        5));
        Assert.assertEquals(15L, (long) Storage.getFruits().get("apple"));
    }

    @Test
    public void supplyAction_supplyingNonExistent_ok() {
        Storage.getFruits().clear();
        new SupplyActionHandler().apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "apple",
                        5));
        Assert.assertEquals(5L, (long) Storage.getFruits().get("apple"));
    }
}
