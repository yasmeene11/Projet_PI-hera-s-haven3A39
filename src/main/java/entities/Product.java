package entities;

import java.util.Date;

public class Product {
static int ProductId;
 static int ProductQuantity;
 static Category CategoryKey;
 static String ProductName;
 static String ProductLabel;
 public static Date ExpirationDate;
 static int rating;

    public static int getRating() {
        return rating;
    }

    public static void setRating(int rating) {
        Product.rating = rating;
    }

    static String ProductImage;

   /* public Product(int productId,String productName,  String productLabel,int productQuantity, Date expirationDate, String ProductImage,Category categoryKey) {
        ProductId = productId;
        ProductName = productName;
        ProductQuantity = productQuantity;
        ProductLabel = productLabel;
        ExpirationDate = expirationDate;
        ProductImage= ProductImage;
        CategoryKey = categoryKey;
    }*/
    public Product(int productId,String productName,  String productLabel,int productQuantity, Date expirationDate,Category categoryKey) {
        ProductId = productId;
        ProductName = productName;
        ProductQuantity = productQuantity;
        ProductLabel = productLabel;
        ExpirationDate = expirationDate;
        CategoryKey = categoryKey;
    }


   /* public Product(String productName, String productLabel,  int productQuantity,Date expirationDate,String ProductImage, Category categoryKey) {
        ProductName = productName;
        ProductQuantity = productQuantity;
        ProductLabel = productLabel;
        ExpirationDate = expirationDate;
        ProductImage= ProductImage;
        CategoryKey = categoryKey;
    }
*/
    public Product(String productName, String productLabel, int productQuantity, Date expirationDate, Category categoryKey) {
        ProductName = productName;
        ProductQuantity = productQuantity;
        ProductLabel = productLabel;
        ExpirationDate = expirationDate;
        CategoryKey = categoryKey;
    }
    public Product() {
    }


    public static int getProductId() {
        return ProductId;
    }
    public void setProductId(int ProductId) {
        Product.ProductId = ProductId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "ProductId=" + ProductId +
                ", ProductQuantity=" + ProductQuantity +
                ", CategoryKey=" + CategoryKey +
                ", ProductName='" + ProductName + '\'' +
                ", ProductLabel='" + ProductLabel + '\'' +
                ", ExpirationDate=" + ExpirationDate +'\''+
                ", ProductImage=" + ProductImage +'\''+
                ", CategoryKey=" + CategoryKey +
                '}';
    }
    public static String getProductImage() {
        return ProductImage;
    }

    public static void setProductImage(String productImage) {
        ProductImage = productImage;
    }

    public static int getProductQuantity() {
        return ProductQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        ProductQuantity = productQuantity;
    }

    public static Category getCategoryKey() {
        return CategoryKey;
    }

    public void setCategoryKey(Category categoryKey) {
        CategoryKey = categoryKey;
    }

    public static String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public static String getProductLabel() {
        return ProductLabel;
    }

    public void setProductLabel(String productLabel) {
        ProductLabel = productLabel;
    }

    public static java.sql.Date getExpirationDate() {
        return (java.sql.Date) ExpirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        ExpirationDate = expirationDate;
    }


}
