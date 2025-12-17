package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.*;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;


@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {

    public MySqlShoppingCartDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ShoppingCart getByUserId(int userId) {
        String sql = "SELECT p.product_id, " +
                " p.name, " +
                " p.price, " +
                " p.category_id, " +
                " p.description, " +
                " p.subcategory, " +
                " p.stock, " +
                " p.featured, " +
                " p.image_url, " +
                " sc.quantity " +
                " FROM shopping_cart sc " +
                " JOIN products p ON sc.product_id = p.product_id " +
                " WHERE sc.user_id = ? ";

        ShoppingCart cart = new ShoppingCart();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setInt(1, userId);

            ResultSet row = statement.executeQuery();
            while (row.next())
            {
                ShoppingCartItem item = mapRow(row);
                cart.add(item);
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return cart;
    }

    @Override
    public void addProduct(int userId, int productId) {
        String sql = " INSERT INTO shopping_cart(user_id, product_id, quantity) " +
                " VALUES (?, ?, 1) ";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setInt(1, userId);
            statement.setInt(2, productId);

           statement.executeUpdate();

        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void updateQuantity(int userId, int productId, int quantity) {

        String sql = " UPDATE shopping_cart" +
                "SET quantity = ? " +
                "WHERE user_id = ? AND product_id = ? " ;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setInt(1,quantity);
            statement.setInt(2, userId);
            statement.setInt(3, productId);

            statement.executeUpdate();

        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void clearCart(int userId) {
        String sql = "DELETE FROM shopping_cart WHERE user_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setInt(1, userId);
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }


    private ShoppingCartItem mapRow(ResultSet row) throws SQLException {

        Product product = new Product(
                row.getInt("product_id"),
                row.getString("name"),
                row.getBigDecimal("price"),
                row.getInt("category_id"),
                row.getString("description"),
                row.getString("subcategory"),
                row.getInt("stock"),
                row.getBoolean("featured"),
                row.getString("image_url")
        );

        ShoppingCartItem item = new ShoppingCartItem();
        item.setProduct(product);
        item.setQuantity(row.getInt("quantity"));
        item.setDiscountPercent(BigDecimal.ZERO);

        return item;
    }
}
