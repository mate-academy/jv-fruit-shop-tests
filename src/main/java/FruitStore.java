import dao.TransactionDaoImpl;
import dao.TransactionsDao;
import java.util.List;
import java.util.Map;
import java.nio.file.Paths;
import model.FruitTransaction;
import service.CsvParseService;
import service.CsvReadService;
import service.CsvTransactionService;
import service.CsvWriteService;

public class FruitStore {
    private static final String OUTPUT_FILE_NAME = "outputFile";
    private static final String INPUT_FILE_NAME = "inputFile";

    public static void main(String[] args) {
        TransactionsDao transactionDao = new TransactionDaoImpl();

        CsvParseService csvParseService = new CsvParseService();
        CsvReadService csvReadService = new CsvReadService();

        String inputFilePath = Paths.get("src", "main", "resources", INPUT_FILE_NAME).toString();
        String outputFilePath = Paths.get("src", "main", "resources", OUTPUT_FILE_NAME).toString();

        List<String> lines = csvReadService.readTransactionsFromCsv(inputFilePath);
        List<FruitTransaction> transactions = lines.stream()
                .map(csvParseService::parseTransaction).toList();
        Map<String, Integer> allTransactions = transactionDao.getAll();
        CsvTransactionService csvTransactionService = new CsvTransactionService(
                transactionDao, transactions
        );

        csvTransactionService.processCsv();
        CsvWriteService reportGenerator = new CsvWriteService(allTransactions);
        reportGenerator.exportToCsv(outputFilePath);
    }
}
