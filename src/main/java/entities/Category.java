package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Category {
private  int CategoryId;
private  String Product_Type;
private  String Product_Source;

    public Category() {
    }


    public  int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public  String getProduct_Type() {
        return Product_Type;
    }

    public void setProduct_Type(String product_Type) {
        Product_Type = product_Type;
    }

    public  String getProduct_Source() {
        return Product_Source;
    }

    public void setProduct_Source(String product_Source) {
        Product_Source = product_Source;
    }



    public Category(int categoryId, String product_Type, String product_Source) {
        CategoryId = categoryId;
        Product_Type = product_Type;
        Product_Source = product_Source;
    }
    public Category(String product_Type, String product_Source) {
        Product_Type = product_Type;
        Product_Source = product_Source;
    }


    @Override
    public String toString() {
        return "Category{" +
                "CategoryId=" + CategoryId +
                ", Product_Type='" + Product_Type + '\'' +
                ", Product_Source='" + Product_Source +
                '}';
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Category category = (Category) obj;
        return Objects.equals(((Category) obj).getProduct_Source(), category.getProduct_Type()) &&
                Objects.equals(((Category) obj).getProduct_Source(), category.getProduct_Source());
    }

}
