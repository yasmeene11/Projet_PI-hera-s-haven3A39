package entities;

import java.util.Date;
//import java.sql.Date;

public class CashRegister {
    private int cashRegisterId;
    private int input;
    private int output;
    private Date date_transaction;
    private String type;
    private Float somme;
    private int id_entity;

    public CashRegister() {
    }

    public CashRegister(int input, int output, Date date_transaction, String type, Float somme, int id_entity) {
        this.input = input;
        this.output = output;
        this.date_transaction = date_transaction;
        this.type = type;
        this.somme = somme;
        this.id_entity = id_entity;
    }

    public CashRegister(int cashRegisterId, int input, int output, Date date_transaction, String type, Float somme, int id_entity) {
        this.cashRegisterId = cashRegisterId;
        this.input = input;
        this.output = output;
        this.date_transaction = date_transaction;
        this.type = type;
        this.somme = somme;
        this.id_entity = id_entity;
    }

    public int getCashRegisterId() {
        return cashRegisterId;
    }

    public void setCashRegisterId(int cashRegisterId) {
        this.cashRegisterId = cashRegisterId;
    }

    public int getInput() {
        return input;
    }

    public void setInput(int input) {
        this.input = input;
    }

    public int getOutput() {
        return output;
    }

    public void setOutput(int output) {
        this.output = output;
    }

    public java.sql.Date getDate_transaction() {
        return new java.sql.Date(date_transaction.getTime());
    }

    public void setDate_transaction(Date date_transaction) {
        this.date_transaction = date_transaction;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getSomme() {
        return somme;
    }

    public void setSomme(Float somme) {
        this.somme = somme;
    }

    public int getId_entity() {
        return id_entity;
    }

    public void setId_entity(int id_entity) {
        this.id_entity = id_entity;
    }
}
