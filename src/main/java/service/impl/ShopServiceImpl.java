package service.impl;

import db.Storage;
import java.util.List;
import java.util.Map;
import model.FruitTransaction;
import report.ReportGenerator;
import report.impl.ReportGeneratorImpl;
import service.ReaderService;
import service.ShopService;
import service.WriterService;
import strategy.BalanceImpl;
import strategy.Operation;
import strategy.PurchaseImpl;
import strategy.ReturnImpl;
import strategy.SupplyImpl;

public class ShopServiceImpl implements ShopService {
    private final Map<FruitTransaction.Operation, Operation> operationMap;

    public ShopServiceImpl(Map<FruitTransaction.Operation, Operation> operationMap) {
        this.operationMap = operationMap;
    }

    @Override
    public void run(String inputFilePath, String outputFilePath) {
        Storage.storage.clear();

        ParseServiceImpl parseService = new ParseServiceImpl();
        ReaderService readerService = new ReaderServiceImpl(parseService);

        operationMap.put(FruitTransaction.Operation.BALANCE, new BalanceImpl());
        operationMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseImpl());
        operationMap.put(FruitTransaction.Operation.SUPPLY, new SupplyImpl());
        operationMap.put(FruitTransaction.Operation.RETURN, new ReturnImpl());

        List<FruitTransaction> transactions = readerService.readFromFile(inputFilePath);
        processTransactions(transactions);

        ReportGenerator generator = new ReportGeneratorImpl();
        WriterService writerService = new WriterServiceImpl();
        writerService.writeReport(generator.generateReport(), outputFilePath);
    }

    @Override
    public void processTransactions(List<FruitTransaction> transactions) {
        for (FruitTransaction transaction : transactions) {
            Operation handler = operationMap.get(transaction.getOperationType());
            if (handler != null) {
                handler.execute(transaction.getFruit(), transaction.getQuantity());
            } else {
                throw new RuntimeException("No handler for operation: " + transaction
                        .getOperationType());
            }
        }
    }
}
