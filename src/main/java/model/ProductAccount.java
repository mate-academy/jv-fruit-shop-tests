package model;

public class ProductAccount {
    private String name;
    private Integer amount = 0;

    public ProductAccount(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getAmount() {

        return this.amount;
    }

    public ProductAccount setAmount(Integer amount) {

        this.amount = amount;
        return this;
    }

}
