package entities;

import services.ServiceProduct;

import java.sql.SQLException;
import java.util.Date;

public class Product {
 int ProductId;
  int ProductQuantity;
  Category CategoryKey;
  String ProductName;
  String ProductLabel;
 public  Date ExpirationDate;
  double Rating;
  String ProductImage;

    public Product() {
    }


    public  int getProductId() {
        return ProductId;
    }
    public void setProductId(int productId) {
        ProductId = productId;
    }
    public  String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }
    public  String getProductLabel() {
        return ProductLabel;
    }

    public void setProductLabel(String productLabel) {
        ProductLabel = productLabel;
    }
    public  int getProductQuantity() {
        return ProductQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        ProductQuantity = productQuantity;
    }
    public  java.sql.Date getExpirationDate() {
        return (java.sql.Date) ExpirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        ExpirationDate = expirationDate;
    }
    public  Category getCategoryKey() {
        return CategoryKey;
    }

    public void setCategoryKey(Category categoryKey) {
        CategoryKey = categoryKey;
    }
    public  String getProductImage() {
        return ProductImage;
    }

    public  void setProductImage(String productImage) {
        ProductImage = productImage;
    }
    public  double getRating() {
        return Rating;
    }


    public void setRating(double rating) {
        Rating = rating;

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
                ", Rating=" + Rating +'\''+
                ", CategoryKey=" + CategoryKey +
                '}';
    }
    public Product(int productId,String productName,  String productLabel,int productQuantity, Date expirationDate, String productImage,Category categoryKey) {
        ProductId = productId;
        ProductName = productName;
        ProductQuantity = productQuantity;
        ProductLabel = productLabel;
        ExpirationDate = expirationDate;
        ProductImage= productImage;
        CategoryKey = categoryKey;
    }
   public Product(int productId,String productName,  String productLabel,int productQuantity, Date expirationDate, String productImage,Double rating,Category categoryKey) {
        ProductId = productId;
        ProductName = productName;
        ProductQuantity = productQuantity;
        ProductLabel = productLabel;
        ExpirationDate = expirationDate;
        ProductImage= productImage;
        Rating=rating;
        CategoryKey = categoryKey;
    }
    public Product(String productName, String productLabel,  int productQuantity,Date expirationDate,String productImage,Double rating, Category categoryKey) {
        ProductName = productName;
        ProductQuantity = productQuantity;
        ProductLabel = productLabel;
        ExpirationDate = expirationDate;
        ProductImage= productImage;
        Rating=rating;
        CategoryKey = categoryKey;
    }

    public Product(String productName, String productLabel,  int productQuantity,Date expirationDate,String productImage,Category categoryKey) {
        ProductName = productName;
        ProductQuantity = productQuantity;
        ProductLabel = productLabel;
        ExpirationDate = expirationDate;
        ProductImage= productImage;
        CategoryKey = categoryKey;
    }


}
