package ru.unn.agile.PersonalFinance.Model;

public class ExternalTransaction implements Transaction {
    public ExternalTransaction(int amount, String description) {
        this.amount = amount;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public boolean isExternal() {
        return true;
    }

    @Override
    public boolean isTransfer() {
        return false;
    }

    private String description;
    private int amount;
}
