public class Main {
    public static void main(String[] args) {
        /*ProductDao dao = new ProductDaoImpl();

        Map<ProductTransaction.Operation, OperationHandler> operationMap = new HashMap<>();
        operationMap.put(ProductTransaction.Operation.BALANCE, new BalanceOperation(dao));
        operationMap.put(ProductTransaction.Operation.PURCHASE, new PurchaseOperation(dao));
        operationMap.put(ProductTransaction.Operation.SUPPLY, new SupplyOperation(dao));
        operationMap.put(ProductTransaction.Operation.RETURN, new ReturnOperation(dao));

        OperationStrategy strategy = new OperationStrategyImpl(operationMap);
        FileReaderService reader = new FileReaderServiceImpl();
        ProductTransactionMapper mapper = new ProductTransactionMapperImpl();
        FileWriterService writer = new FileWriterServiceImpl();
        ReportService report = new ReportServiceImpl();
        TransactionService trService = new TransactionServiceImpl(strategy);

        FruitService fruitService =
                new FruitServiceImpl(reader, writer, mapper, dao, report, trService);

        String fromFile = "src/main/java/data.csv";
        String toFile = "report.csv";
        fruitService.run(fromFile, toFile);

         */
    }
}
