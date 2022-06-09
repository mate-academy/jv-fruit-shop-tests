package service;

public interface ShopService {
    boolean execProductTransaction(String productname,
                                   Integer productAmount,
                                   String operationString);
}
