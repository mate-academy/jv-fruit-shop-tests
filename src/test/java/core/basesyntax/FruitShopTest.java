package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.createreport.CreateReport;
import core.basesyntax.gettingreport.GetFromReportImpl;
import core.basesyntax.gettingreport.GettingNewReportImpl;
import core.basesyntax.service.StorageServiceImpl;
import core.basesyntax.stoage.Storage;
import core.basesyntax.tranzactions.BalanceTransaction;
import core.basesyntax.tranzactions.DefineTheTransaction;
import core.basesyntax.tranzactions.MakingDailyTransactionsImpl;
import core.basesyntax.tranzactions.PurchaseTransaction;
import core.basesyntax.tranzactions.ReturnTransaction;
import core.basesyntax.tranzactions.SupplyTransaction;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitShopTest {
    private MakingDailyTransactionsImpl transactions;
    private GettingNewReportImpl toReport;
    private GetFromReportImpl getFromReport;
    private DefineTheTransaction definition;
    private BalanceTransaction balance;
    private PurchaseTransaction purchase;
    private SupplyTransaction supply;
    private ReturnTransaction returning;
    private StorageServiceImpl service;
    private CreateReport creating;

    @BeforeEach
    void setUp() {
        service = new StorageServiceImpl();
        transactions = new MakingDailyTransactionsImpl();
        toReport = new GettingNewReportImpl();
        getFromReport = new GetFromReportImpl();
        definition = new DefineTheTransaction();
        balance = new BalanceTransaction();
        purchase = new PurchaseTransaction();
        supply = new SupplyTransaction();
        returning = new ReturnTransaction();
        creating = new CreateReport();
        Storage storage = new Storage();
    }

    @Test
    public void transferringNonExistentFileName_NotOk() {
        assertThrows(RuntimeException.class,() -> getFromReport.getFromReport("Non-Existent.csv"),
                "File is not existent");
    } //good

    @Test
    public void transferringExistentFileName_Ok() {
        List<String> actual = getFromReport.getFromReport("recent.csv");
        List<String> excepted = List.of("b,banana,20","b,apple,100","s,banana,100","p,banana,13",
                "r,apple,10","p,apple,20","p,banana,5","s,banana,50");
        assertEquals(excepted,actual,"Excepted result is " + excepted.toString() + "but was "
                + actual.toString());
    } //good

    @Test
    public void transferringFileWithInvalidFormatAtFirstPosition_notOk() {
        assertThrows(RuntimeException.class,()
                -> getFromReport.getFromReport("invalidFormatAtFirstLine.csv"),
                "Values must be separated by commas");
    } //good

    @Test
    public void transferringFileWithInvalidFormatAtThirdPosition_notOk() {
        assertThrows(RuntimeException.class,()
                -> getFromReport.getFromReport("invalidFormatAtThirdLine.csv"),
                "Values must be separated by commas");
    } //good

    @Test
    public void fileWithOnlyCommas_notOk() {
        assertThrows(RuntimeException.class,()
                -> getFromReport.getFromReport("onlyCommas"),
                "All values must be specified in format t,fruit,count");
    } // good

    @Test
    public void notSetTransaction_notOk() {
        assertThrows(RuntimeException.class,()
                -> getFromReport.getFromReport("notSetTransaction"),
                "All values must be specified in format t,fruit,count");
    } //good

    @Test
    public void notSetFruit_notOk() {
        assertThrows(RuntimeException.class,()
                -> getFromReport.getFromReport("notSetFruit"),
                "All values must be specified in format t,fruit,count");
    } //good

    @Test
    public void notSetValue_notOk() {
        assertThrows(RuntimeException.class,()
                -> getFromReport.getFromReport("notSetValue"),
                "All values must be specified in format t,fruit,count");
    } // good

    @Test
    public void invalidNameOfTransaction_notOk() {
        assertThrows(RuntimeException.class,()
                -> definition.definition("m","banana",30),
                "Invalid name of transaction, it should be one of b,s,r,p");
    } //good

    @Test
    public void validNameOfTransaction_Ok() {
        definition.definition("b","banana",30);
        int excepted = 30;
        int actual = service.getFromStorage("banana");
        assertEquals(excepted,actual);
    } //good

    @Test
    public void invalidCount_notOk() {
        assertThrows(RuntimeException.class,()
                -> transactions.makeAllTransactions("invalidCount.csv"),
                "Invalid count of fruits");
    } //good

    @Test
    public void addAnExistingFruit_notOk() {
        balance.applyOperations("banana",30);
        assertThrows(RuntimeException.class,()
                -> balance.applyOperations("banana",10),
                "Some fruit has already existed");
    } //good

    @Test
    public void addFruit_Ok() {
        balance.applyOperations("banana",30);
        Integer actual = service.getFromStorage("banana");
        assertEquals(30,actual);
    } //good

    @Test
    public void purchaseMoreFruitThatWeHave_notOk() {
        balance.applyOperations("apple",10);
        assertThrows(RuntimeException.class,()
                -> purchase.applyOperations("apple",30),
                "We can`t solve more fruits that we have");
    } //good

    @Test
    public void purchaseFruit_Ok() {
        balance.applyOperations("apple",30);
        purchase.applyOperations("apple",10);
        int actual = service.getFromStorage("apple");
        assertEquals(20,actual);
    } //good

    @Test
    public void purchaseNonExistenceFruit_notOk() {
        balance.applyOperations("banana",30);
        assertThrows(RuntimeException.class,()
                -> purchase.applyOperations("orange",20),
                "We can`t solve non-existence fruit");
    } //good

    @Test
    public void supplyNonExistenceFruit_notOk() {
        balance.applyOperations("orange",10);
        assertThrows(RuntimeException.class,()
                -> supply.applyOperations("pineapple",20),
                "We can`t supply non-existence fruit");
    } //good

    @Test
    public void supplyExistenceFruit_Ok() {
        balance.applyOperations("orange",10);
        supply.applyOperations("orange",20);
        int actual = service.getFromStorage("orange");
        assertEquals(30,actual);
    } //good

    @Test
    public void returnNonExistenceFruit_notOk() {
        balance.applyOperations("orange",10);
        assertThrows(RuntimeException.class,()
                -> returning.applyOperations("pineapple",20),
                "Customer can`t return non-existence fruit");
    } //good

    @Test
    public void returnExistenceFruit_Ok() {
        balance.applyOperations("orange",10);
        returning.applyOperations("orange",20);
        int actual = service.getFromStorage("orange");
        assertEquals(30,actual);
    } //good

    @Test
    public void addToStorage_Ok() {
        service.addToStorage("apple",30);
        int size = service.getStorage().size();
        assertEquals(1,size,"Size would be 1");
    } //good

    @Test
    public void getFromStorage_Ok() {
        service.addToStorage("apple",30);
        Integer actual = service.getFromStorage("apple");
        assertEquals(30,actual,"Count would be 30");
    } //good

    @Test
    public void getStorage_Ok() {
        service.addToStorage("apple",30);
        Map<String,Integer> excepted = Map.of("apple",30);
        Map<String, Integer> actual = service.getStorage();
        assertEquals(excepted,actual,"Return map would be " + excepted.toString()
                + " but was " + actual.toString());
    } //good

    @Test
    public void getNewReport_Ok() {
        balance.applyOperations("apple",30);
        String actual = creating.createReport(service.getStorage());
        String excepted = "fruit,quantity" + System.lineSeparator() + "apple,30"
                + System.lineSeparator();
        assertEquals(excepted,actual,"Returning report would be " + excepted
                + " but was " + actual);
    } //good

    @Test
    public void getNewReportFromEmptyStorage_notOk() {
        assertThrows(RuntimeException.class,()
                -> creating.createReport(service.getStorage()),
                "Customer can`t return non-existence fruit");
    } //good
}
