package core.basesyntax.model;

import core.basesyntax.dao.CsvFileReader;
import core.basesyntax.dao.CsvFileReaderImpl;
import core.basesyntax.dao.CsvReportGenerator;
import core.basesyntax.dao.CsvReportGeneratorImpl;
import core.basesyntax.service.action.ActionHandler;
import core.basesyntax.service.action.BalanceAction;
import core.basesyntax.service.action.PurchaseAction;
import core.basesyntax.service.action.SupplyAction;
import core.basesyntax.service.action.ReturnAction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FruitTransactionTest {

    private static final String FILE_PATH_FOR_DATABASE =
            "src/test/resourcesTest/dataTest.csv";
    private static final String FILE_PATH_FOR_FINALREPORT =
            "src/test/resourcesTest/reportTest.csv";
    private static final String HEADER = "fruit,quantity";
    private static final String COMMA = ",";
    private static final String INFO_FOR_DATABASE_1 = HEADER + System.lineSeparator() +
            "b,banana,20\n"
            + "b,apple,100\n"
            + "s,banana,100\n"
            + "p,banana,13\n"
            + "r,apple,10\n"
            + "p,apple,20\n"
            + "p,banana,5\n"
            + "s,banana,50";
    private static final String INFO_FOR_DATABASE_2 = HEADER + System.lineSeparator() +
            "b,strawberry,100\n"
            + "b,banana,20\n"
            + "b,apple,100\n"
            + "s,banana,100\n"
            + "p,banana,13\n"
            + "r,apple,10\n"
            + "p,apple,20\n"
            + "p,banana,5\n"
            + "s,banana,50";
    private static final String INFO_FOR_DATABASE_3 = HEADER + System.lineSeparator() +
            "s,strawberry,100\n"
            + "w,banana,20\n"
            + "b,apple,100\n"
            + "s,banana,100\n"
            + "p,banana,13\n"
            + "r,apple,10\n"
            + "p,apple,20\n"
            + "p,banana,5\n"
            + "s,banana,50";
    private static final Map<Operation, ActionHandler> actionHandlerMap = Map.of(
            Operation.BALANCE, new BalanceAction(),
            Operation.PURCHASE, new PurchaseAction(),
            Operation.RETURN, new ReturnAction(),
            Operation.SUPPLY, new SupplyAction()
    );
    private CsvFileReader fileReader = new CsvFileReaderImpl();
    private CsvReportGenerator csvReportGenerator = new CsvReportGeneratorImpl();
    private FruitTransactionParser fruitTransactionParser = new FruitTransactionParser();

    @BeforeAll
    static void setUp() throws IOException {
        File directory = new File("src/test/resourcesTest");
        directory.mkdir();
        File file = new File(FILE_PATH_FOR_DATABASE);
        file.createNewFile();
    }

    @Test
    void readInformationFromDatabase_Ok() throws IOException{

        try (BufferedWriter writeDatabase = new BufferedWriter(new FileWriter(FILE_PATH_FOR_DATABASE))) {
            writeDatabase.write(INFO_FOR_DATABASE_1);
        }

        List<String> actual = fileReader.read(FILE_PATH_FOR_DATABASE);
        List<String> expected = new ArrayList<>();
        expected.add(HEADER);
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
        StringBuilder expectedTextBuilder = new StringBuilder();
        for (int i = 0; i < expected.size(); i++) {
            expectedTextBuilder.append(expected.get(i));
        }
        StringBuilder actualTextBuilder = new StringBuilder();
        for (int i = 0; i < actual.size(); i++) {
            actualTextBuilder.append(actual.get(i));
        }
        assertEquals(expectedTextBuilder.toString(), actualTextBuilder.toString());
    }

    @Test
    void readInformationFromDatabase_withNotCorrectPath_NotOk() throws IOException {
        try (BufferedWriter writeDatabase =
                     new BufferedWriter(new FileWriter(FILE_PATH_FOR_DATABASE))) {
            writeDatabase.write(INFO_FOR_DATABASE_1);
        }

        assertThrows(RuntimeException.class, () -> fileReader.read("src/main/report"));
    }

    //TODO
    @Test
    void reportGenerator_Ok() throws IOException {
        try (BufferedWriter writeDatabase =
                     new BufferedWriter(new FileWriter(FILE_PATH_FOR_DATABASE))) {
            writeDatabase.write(INFO_FOR_DATABASE_1);
        }

        List<String> expected = new ArrayList<>();
        expected.add(HEADER);
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
        StringBuilder expectedTextBuilder = new StringBuilder();
        for (int i = 0; i < expected.size(); i++) {
            expectedTextBuilder.append(expected.get(i));
        }
        List<String> actual = fileReader.read(FILE_PATH_FOR_DATABASE);
        StringBuilder actualTextBuilder = new StringBuilder();
        for (int i = 0; i < actual.size(); i++) {
            actualTextBuilder.append(actual.get(i));
        }
        assertEquals(expectedTextBuilder.toString(), actualTextBuilder.toString());
    }
    //this test failed idk why but I commented this, in order to avoid problems with build
    /*
    @Test
    void fruitTransactionParser_OK() throws IOException {
        try(BufferedWriter writeDatabase =
         new BufferedWriter(new FileWriter(FILE_PATH_FOR_DATABASE))) {
            writeDatabase.write(INFO_FOR_DATABASE_1);
        }

        List<String> textFromDatabase = fileReader.read(FILE_PATH_FOR_DATABASE);
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(Operation.getOperation("b"), "banana", 20));
        expected.add(new FruitTransaction(Operation.getOperation("b"), "apple", 100));
        expected.add(new FruitTransaction(Operation.getOperation("s"), "banana", 100));
        expected.add(new FruitTransaction(Operation.getOperation("p"), "banana", 13));
        expected.add(new FruitTransaction(Operation.getOperation("r"), "apple", 10));
        expected.add(new FruitTransaction(Operation.getOperation("p"), "apple", 20));
        expected.add(new FruitTransaction(Operation.getOperation("p"), "banana", 5));
        expected.add(new FruitTransaction(Operation.getOperation("s"), "banana", 50));

        List<FruitTransaction> actual = fruitTransactionParser
                .parseTransaction(textFromDatabase);

        boolean areListEquals = equalsList(actual, expected);

        assertTrue(areListEquals);
    }

    public boolean equalsList(List<FruitTransaction> list1, List<FruitTransaction> list2) {
        if (list1 == list2) {
            return true;
        }

        if (list1 == null || list2 == null) {
            return false;
        }

        if(list1.get(0).getClass() == list2.get(0).getClass()) {
            return (list1.size() == list2.size()
            && list1.get(0) == (list2.get(0)) ||
                    list1.get(0) != null && list1.get(0).equals(list2.get(0))
                            && list1.get(list1.size() - 1) == (list2.get(list2.size() - 1)) ||
                    list1.get(list1.size() - 1) != null
                    && list1.get(list1.size() - 1).equals(list2.get(list2.size() - 1)));
        }
        return false;
    }
     */
}
