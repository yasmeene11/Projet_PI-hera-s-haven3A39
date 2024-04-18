package entities;

import java.util.Date;

public class DonationM {
    private int donationMId;
    private float donationAmount;
    private Date donationMDate;
    private int accountKey;

    public DonationM(int donationMId, float donationAmount, Date donationMDate, int accountKey) {
        this.donationMId = donationMId;
        this.donationAmount = donationAmount;
        this.donationMDate = donationMDate;
        this.accountKey = accountKey;
    }

    public DonationM(float donationAmount, Date donationMDate, int accountKey)
    {
        this.donationAmount=donationAmount;
        this.donationMDate=donationMDate;
        this.accountKey=accountKey;
    }

    public DonationM() {
    }

    public int getDonationMId() {
        return donationMId;
    }

    public void setDonationMId(int donationMId) {
        this.donationMId = donationMId;
    }

    public float getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(float donationAmount) {
        this.donationAmount = donationAmount;
    }

    public java.sql.Date getDonationMDate() {
        return new java.sql.Date(donationMDate.getTime());
    }

    public void setDonationMDate(Date donationMDate) {
        this.donationMDate = donationMDate;
    }

    public int getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(int accountKey) {
        this.accountKey = accountKey;
    }

    @Override
    public String toString() {
        return "donationM{" +
                "donationMId=" + donationMId +
                ", donationAmount=" + donationAmount +
                ", donationMDate='" + donationMDate + '\'' +
                ", accountKey=" + accountKey +
                '}';
    }
}
