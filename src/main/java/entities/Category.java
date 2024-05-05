package entities;

public class Category {
  int CategoryId;
  String Product_Type;
  String Product_Source;

    public Category() {
    }
    public Category(int categoryId, String product_Type, String product_Source) {
        this.CategoryId = categoryId;
        this.Product_Type = product_Type;
        this.Product_Source = product_Source;
    }
    public Category(String product_Type, String product_Source) {
        this.Product_Type = product_Type;
        this.Product_Source = product_Source;
    }


       public int getCategoryId() {
           return CategoryId;
       }

       public void setCategoryId(int categoryId) {
           CategoryId = categoryId;
       }

       public String getProduct_Type() {
           return Product_Type;
       }

       public void setProduct_Type(String product_Type) {
           Product_Type = product_Type;
       }

       public String getProduct_Source() {
           return Product_Source;
       }

       public void setProduct_Source(String product_Source) {
           Product_Source = product_Source;
       }


    @Override
    public String toString() {
        return "Category{" +
                "CategoryId=" + CategoryId +
                ", Product_Type='" + Product_Type + '\'' +
                ", Product_Source='" + Product_Source + '\'' +
                '}';
    }
}
