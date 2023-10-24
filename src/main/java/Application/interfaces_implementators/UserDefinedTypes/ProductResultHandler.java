package Application.interfaces_implementators.UserDefinedTypes;
import Application.entities.Product;
import Application.interfaces_implementators.QueryResultHandler;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductResultHandler implements QueryResultHandler<Product> {
  @Override
  public Product handle(ResultSet resultSet) throws SQLException {
    if (resultSet.next()) {
      Product product = new Product();
      product.setId(resultSet.getInt("id"));
      product.setName(resultSet.getString("name"));
      product.setCategory(resultSet.getString("category"));
      product.setPrice(resultSet.getDouble("price"));
      product.setQuantity(resultSet.getInt("quantity"));
      return product;
    }
    return null;
  }
}