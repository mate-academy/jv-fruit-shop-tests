package core.basesyntax.service.impl;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.Converter;
import core.basesyntax.service.TransactionDao;
import java.util.ArrayList;
import java.util.List;

public class ConverterCsvImpl implements Converter {
    private static final int OPERATION_TYPE = 0;
    private static final int PRODUCT_NAME = 1;
    private static final int QUANTITY = 2;
    private static final int REQUIRED_LENGTH = 3;
    private final TransactionDao dao;
    
    public ConverterCsvImpl(TransactionDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Transaction> convertCsvLines(List<String> lines) {
        if (lines == null || lines.isEmpty()) {
            throw new IllegalArgumentException("lines must not be empty or null");
        }
        
        List<Transaction> transactions = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            
            if (parts.length != REQUIRED_LENGTH) {
                throw new IllegalArgumentException("Wrong number of columns in line " + i);
            }
            
            Transaction transaction = new Transaction(
                    Transaction.Operation.getOperationByCode(parts[OPERATION_TYPE].trim()),
                    parts[PRODUCT_NAME].trim(),
                    Integer.parseInt(parts[QUANTITY].trim())
            );
            transactions.add(transaction);
        }
        return transactions;
    }
}
