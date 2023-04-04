package core.basesyntax.services;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.services.transaction.ProductTransactionMapper;
import core.basesyntax.services.transaction.TransactionService;
import core.basesyntax.services.transaction.model.ProductTransaction;
import java.util.List;

public class FruitServiceImpl implements FruitService {
    private final FileReaderService reader;
    private final FileWriterService writerService;
    private final ProductTransactionMapper mapper;
    private final ProductDao productDao;
    private final TransactionService transactionService;
    private final ReportService reportService;

    public FruitServiceImpl(FileReaderService reader, FileWriterService writerService,
                            ProductTransactionMapper mapper, ProductDao productDao,
                            ReportService reportService, TransactionService transactionService) {
        this.reader = reader;
        this.writerService = writerService;
        this.mapper = mapper;
        this.productDao = productDao;
        this.reportService = reportService;
        this.transactionService = transactionService;
    }

    @Override
    public void run(String fromFile, String toFile) {
        List<String> allTransactionsStr = reader.read(fromFile);
        allTransactionsStr.remove(0); //deleted line with annotation text
        List<ProductTransaction> prTransactions = mapper.getProductTransactions(allTransactionsStr);
        transactionService.process(prTransactions);
        writerService.write(toFile, reportService.createReport(productDao.getAll()));
    }
}
