package entities;

public class ProductDonation {
    private int dpId;
    private DonationP Donation_Key;
    private Product Product_Key;
public ProductDonation(){}
    public ProductDonation(int DpId,DonationP donation_Key,Product product_Key){
    dpId=DpId;
    Donation_Key=donation_Key;
    Product_Key=product_Key;
    }
    public ProductDonation(DonationP donation_Key,Product product_Key){
        Donation_Key=donation_Key;
        Product_Key=product_Key;
    }

    public int getDpId() {
        return dpId;
    }

    public void setDpId(int dpId) {
        this.dpId = dpId;
    }

    public  DonationP getDonation_Key() {
        return Donation_Key;
    }

    public void setDonation_Key(DonationP donation_Key) {
        Donation_Key = donation_Key;
    }

    public Product getProduct_Key() {
        return Product_Key;
    }

    public void setProduct_Key(Product product_Key) {
        Product_Key = product_Key;
    }

    @Override
    public String toString() {
        return "ProductDonation{" +
                "dpId=" + dpId +
                ", Donation_Key=" + Donation_Key +
                ", Product_Key=" + Product_Key +
                '}';
    }
}
