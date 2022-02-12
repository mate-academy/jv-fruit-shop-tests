package core.basesyntax.tranzactions;

public class DefineTheTransaction {
    public void definition(String k,String fruit,Integer value) {
        if (k.equals("b")) {
            new BalanceTransaction().applyOperations(fruit, value);
            return;
        }
        if (k.equals("s")) {
            new SupplyTransaction().applyOperations(fruit, value);
            return;
        }
        if (k.equals("p")) {
            new PurchaseTransaction().applyOperations(fruit, value);
            return;
        }
        if (k.equals("r")) {
            new ReturnTransaction().applyOperations(fruit, value);
            return;
        }
        throw new RuntimeException("Invalid name of transaction");
    }
}
