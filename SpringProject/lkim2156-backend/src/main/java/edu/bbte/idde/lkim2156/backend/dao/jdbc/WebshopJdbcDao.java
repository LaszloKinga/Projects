package edu.bbte.idde.lkim2156.backend.dao.jdbc;

import edu.bbte.idde.lkim2156.backend.dao.NotFoundException;
import edu.bbte.idde.lkim2156.backend.dao.WebshopDao;
import edu.bbte.idde.lkim2156.backend.model.Webshop;
import edu.bbte.idde.lkim2156.backend.util.PropertyProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WebshopJdbcDao implements WebshopDao {
    private static final Logger LOG = LoggerFactory.getLogger(WebshopJdbcDao.class);
    private final ConnectionPool connectionPool;

    public WebshopJdbcDao() {
        connectionPool = ConnectionPool.getInstance();
        Boolean createTable = Boolean.valueOf(PropertyProvider.getProperty("jdbc_create_tables"));
        if (createTable) {
            Connection connection = connectionPool.getConnection();

            try (Statement statement = connection.createStatement()) {
                LOG.info("Creating table for DAO");

                statement.executeUpdate("CREATE TABLE Webshop ("
                        + "id INT PRIMARY KEY AUTO_INCREMENT,"
                        + "orderDate DATE,"
                        + "address VARCHAR(128),"
                        + "totalAmount FLOAT,"
                        + "paymentMethod VARCHAR(128),"
                        + "isShipped TINYINT(1)"
                        + ");");
                statement.close();
            } catch (SQLException e) {
                LOG.error("Error creating table", e);
            } finally {
                connectionPool.returnConnection(connection);
            }
        }

    }

    private Webshop mapRowToWebshop(ResultSet resultSet) throws SQLException {
        Webshop webshop = new Webshop();
        webshop.setId(resultSet.getInt(1));
        webshop.setOrderDate(resultSet.getDate("orderDate").toLocalDate());
        webshop.setAddress(resultSet.getString(3));
        webshop.setTotalAmount(resultSet.getDouble(4));
        webshop.setPaymentMethod(resultSet.getString(5));
        webshop.setShipped(resultSet.getBoolean(6));
        return webshop;
    }


    @Override
    public List<Webshop> findAll() throws NotFoundException {
        Connection connection = connectionPool.getConnection();
        List<Webshop> webshopList = new ArrayList<>();
        String query = "SELECT id, orderDate, address, totalAmount, paymentMethod, isShipped FROM Webshop";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Webshop webshop = mapRowToWebshop(resultSet);
                webshopList.add(webshop);
            }
        } catch (SQLException e) {
            LOG.error("Error fetching records", e);
            throw new NotFoundException("Error retrieving records", e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return webshopList;
    }

    @Override
    public Webshop findById(Integer id) throws NotFoundException {
        Connection connection = connectionPool.getConnection();
        String query = "SELECT * FROM Webshop WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                return mapRowToWebshop(resultSet);

            } else {
                throw new NotFoundException("Record with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            LOG.error("Error finding record by ID", e);
            throw new NotFoundException("Error retrieving record with ID " + id, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Webshop updateById(Integer id, Webshop entity) throws NotFoundException {
        Connection connection = connectionPool.getConnection();
        String query = "UPDATE Webshop SET orderDate = ?, address = ?, totalAmount = ?,"
                + " paymentMethod = ?, isShipped = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setDate(1, entity.getOrderDate() != null ? Date.valueOf(entity.getOrderDate()) : null);
            preparedStatement.setString(2, entity.getAddress());
            preparedStatement.setDouble(3, entity.getTotalAmount());
            preparedStatement.setString(4, entity.getPaymentMethod());
            preparedStatement.setBoolean(5, entity.isShipped());
            preparedStatement.setInt(6, id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new NotFoundException("Record with ID " + id + " not found for update.");
            }
            LOG.info("Successfully updated");
            return entity;
        } catch (SQLException e) {
            LOG.error("Error updating record with ID " + id, e);
            throw new NotFoundException("Error updating record with ID " + id, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Webshop create(Webshop entity) throws NotFoundException {
        Connection connection = connectionPool.getConnection();
        String query = "INSERT INTO Webshop (orderDate, address, totalAmount, paymentMethod, isShipped) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                query, Statement.RETURN_GENERATED_KEYS
        )) {

            preparedStatement.setDate(1, entity.getOrderDate() != null ? Date.valueOf(entity.getOrderDate()) : null);
            preparedStatement.setString(2, entity.getAddress());
            preparedStatement.setDouble(3, entity.getTotalAmount());
            preparedStatement.setString(4, entity.getPaymentMethod());
            preparedStatement.setBoolean(5, entity.isShipped());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new NotFoundException("Creating record failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt(1));
                } else {
                    throw new NotFoundException("Creating record failed, no ID obtained.");
                }
            }
            LOG.info("Successfully created");
            return entity;
        } catch (SQLException e) {
            LOG.error("Error creating record", e);
            throw new NotFoundException("Error creating new record", e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void deleteById(Integer id) throws NotFoundException {
        Connection connection = connectionPool.getConnection();
        String query = "DELETE FROM Webshop WHERE id = ?";
        LOG.info("Delete item with id number " + id);
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                LOG.error("Record with this ID not found for deletion.");
                throw new NotFoundException("Record with ID " + id + " not found for deletion.");
            }
        } catch (SQLException e) {
            LOG.error("Error deleting record with ID " + id, e);
            throw new NotFoundException("Error deleting record with ID " + id, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}
