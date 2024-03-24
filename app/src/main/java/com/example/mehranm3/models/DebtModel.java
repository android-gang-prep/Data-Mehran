package com.example.mehranm3.models;

public class DebtModel {
    private String debtor;
    private long idDebtor;
    private String creditor;
    private long idCreditor;
    public double dong;
    public String unit;

    public DebtModel(String debtor, String creditor, double dong, String unit) {
        this.debtor = debtor;
        this.creditor = creditor;
        this.dong = dong;
        this.unit = unit;
    }

    public DebtModel(String debtor, long idDebtor, String creditor, long idCreditor, double dong, String unit) {
        this.debtor = debtor;
        this.idDebtor = idDebtor;
        this.creditor = creditor;
        this.idCreditor = idCreditor;
        this.dong = dong;
        this.unit = unit;
    }

    public String getDebtor() {
        return debtor;
    }

    public void setDebtor(String debtor) {
        this.debtor = debtor;
    }

    public long getIdDebtor() {
        return idDebtor;
    }

    public void setIdDebtor(long idDebtor) {
        this.idDebtor = idDebtor;
    }

    public String getCreditor() {
        return creditor;
    }

    public void setCreditor(String creditor) {
        this.creditor = creditor;
    }

    public long getIdCreditor() {
        return idCreditor;
    }

    public void setIdCreditor(long idCreditor) {
        this.idCreditor = idCreditor;
    }

    public String getDong() {
        return String.format("%.2f %s", dong, unit);
    }

    public void setDong(double dong) {
        this.dong = dong;
    }
}
