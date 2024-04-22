package entities;

import java.util.Date;

public class DonationP {
    private int donationPId;
    private String donation_product_name;
    private int donation_product_quantity;
    private String donation_product_label;
    private Date donation_product_expiration_date;
    private Date donation_p_date;
    private String donation_p_type;
    private int Account_Key;
    public DonationP(String donation_product_name, int donation_product_quantity, String donation_product_label, Date donation_product_expiration_date, Date donation_p_date, String donation_p_type, int account_Key) {
        this.donation_product_name = donation_product_name;
        this.donation_product_quantity = donation_product_quantity;
        this.donation_product_label = donation_product_label;
        this.donation_product_expiration_date = donation_product_expiration_date;
        this.donation_p_date = donation_p_date;
        this.donation_p_type = donation_p_type;
        Account_Key = account_Key;
    }

    public DonationP(String donation_product_name, int donation_product_quantity, String donation_product_label, Date donation_product_expiration_date, Date donation_p_date, String donation_p_type) {
        this.donation_product_name = donation_product_name;
        this.donation_product_quantity = donation_product_quantity;
        this.donation_product_label = donation_product_label;
        this.donation_product_expiration_date = donation_product_expiration_date;
        this.donation_p_date = donation_p_date;
        this.donation_p_type = donation_p_type;
    }

    public DonationP(int donationPId, String donation_product_name, int donation_product_quantity, String donation_product_label, Date donation_product_expiration_date, Date donation_p_date, String donation_p_type, int account_Key) {
        this.donationPId = donationPId;
        this.donation_product_name = donation_product_name;
        this.donation_product_quantity = donation_product_quantity;
        this.donation_product_label = donation_product_label;
        this.donation_product_expiration_date = donation_product_expiration_date;
        this.donation_p_date = donation_p_date;
        this.donation_p_type = donation_p_type;
        Account_Key = account_Key;
    }

    public DonationP() {
    }

    public int getDonationPId() {
        return donationPId;
    }

    public void setDonationPId(int donationPId) {
        this.donationPId = donationPId;
    }

    public String getDonation_product_name() {
        return donation_product_name;
    }

    public void setDonation_product_name(String donation_product_name) {
        this.donation_product_name = donation_product_name;
    }

    public int getDonation_product_quantity() {
        return donation_product_quantity;
    }

    public void setDonation_product_quantity(int donation_product_quantity) {
        this.donation_product_quantity = donation_product_quantity;
    }

    public String getDonation_product_label() {
        return donation_product_label;
    }

    public void setDonation_product_label(String donation_product_label) {
        this.donation_product_label = donation_product_label;
    }

    public java.sql.Date getDonation_product_expiration_date() {
        return new java.sql.Date(donation_product_expiration_date.getTime());
    }

    public void setDonation_product_expiration_date(Date donation_product_expiration_date) {
        this.donation_product_expiration_date = donation_product_expiration_date;
    }

    public java.sql.Date getDonation_p_date() {
        return new java.sql.Date(donation_p_date.getTime());
    }

    public void setDonation_p_date(Date donation_p_date) {
        this.donation_p_date = donation_p_date;
    }

    public String getDonation_p_type() {
        return donation_p_type;
    }

    public void setDonation_p_type(String donation_p_type) {
        this.donation_p_type = donation_p_type;
    }

    public int getAccount_Key() {
        return Account_Key;
    }

    public void setAccount_Key(int account_Key) {
        Account_Key = account_Key;
    }

    @Override
    public String toString() {
        return "DonationP{" +
                "donationPId=" + donationPId +
                ", donation_product_name='" + donation_product_name + '\'' +
                ", donation_product_quantity=" + donation_product_quantity +
                ", donation_product_label='" + donation_product_label + '\'' +
                ", donation_product_expiration_date=" + donation_product_expiration_date +
                ", donation_p_date=" + donation_p_date +
                ", donation_p_type='" + donation_p_type + '\'' +
                ", Account_Key=" + Account_Key +
                '}';
    }
}
