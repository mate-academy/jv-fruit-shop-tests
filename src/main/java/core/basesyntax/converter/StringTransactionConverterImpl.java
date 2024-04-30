package core.basesyntax.converter;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class StringTransactionConverterImpl implements StringTransactionConverter {
    private static final String COMA = ",";

    @Override
    public List<Transaction> convert(List<String> stringTransactions) {
        if (stringTransactions == null) {
            throw new RuntimeException("List " + stringTransactions + " can not be null");
        }
        List<Transaction> transactions = new ArrayList<>();

        for (int i = 1; i < stringTransactions.size(); i++) {
            String[] fields = stringTransactions.get(i).split(COMA);
            if (fields.length != 3) {
                throw new RuntimeException("Record of transaction is not correct,"
                        + " cant convert to transaction");
            }

            Transaction transaction = new Transaction();
            transaction.setTransactionType(Transaction.TransactionType
                    .getTransactionTypeByCode(fields[0]));
            if ((fields[1] == null) || (fields[1].equals(""))) {
                throw new RuntimeException("no fruit name found in transaction record");
            }
            transaction.setFruit(new Fruit(fields[1]));

            if (Integer.parseInt(fields[2]) < 0) {
                throw new RuntimeException("transaction amount can not be less than 0");
            }
            transaction.setAmount(Integer.parseInt(fields[2]));
//            Transaction transaction = new Transaction(Transaction.TransactionType
//                    .getTransactionTypeByCode(fields[0]), fruit, Integer.parseInt(fields[2]));
            transactions.add(transaction);
        }
        return transactions;
    }
}
