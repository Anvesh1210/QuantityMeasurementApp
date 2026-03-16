package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.exception.DatabaseException;
import com.app.quantitymeasurement.util.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class QuantityMeasurementDatabaseRepository implements IQuantityMeasurementRepository {

	private static final Logger logger = Logger.getLogger(QuantityMeasurementDatabaseRepository.class.getName());

	private static QuantityMeasurementDatabaseRepository instance;

	private ConnectionPool connectionPool;

	private static final String INSERT_QUERY = "INSERT INTO quantity_measurement_entity "
			+ "(this_value,this_unit,this_measurement_type,that_value,that_unit,that_measurement_type,"
			+ "operation,result_value,result_unit,result_measurement_type,result_string,is_error,error_message,"
			+ "created_at,updated_at) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),NOW())";

	private QuantityMeasurementDatabaseRepository() {
		try {
			connectionPool = ConnectionPool.getInstance();
		} catch (SQLException e) {
			throw DatabaseException.connectionFailed("Initializing pool", e);
		}
		initializeDatabase();
	}

	public static synchronized QuantityMeasurementDatabaseRepository getInstance() {
		if (instance == null) {
			instance = new QuantityMeasurementDatabaseRepository();
		}
		return instance;
	}

	private void initializeDatabase() {
		String createTable = "CREATE TABLE IF NOT EXISTS quantity_measurement_entity ("
				+ "id BIGINT AUTO_INCREMENT PRIMARY KEY," + "this_value DOUBLE," + "this_unit VARCHAR(50),"
				+ "this_measurement_type VARCHAR(50)," + "that_value DOUBLE," + "that_unit VARCHAR(50),"
				+ "that_measurement_type VARCHAR(50)," + "operation VARCHAR(50)," + "result_value DOUBLE,"
				+ "result_unit VARCHAR(50)," + "result_measurement_type VARCHAR(50)," + "result_string VARCHAR(255),"
				+ "is_error BOOLEAN," + "error_message VARCHAR(255)," + "created_at TIMESTAMP,"
				+ "updated_at TIMESTAMP)";

		try (Connection conn = connectionPool.getConnection(); Statement stmt = conn.createStatement()) {
			stmt.execute(createTable);
		} catch (Exception e) {
			throw DatabaseException.queryFailed("Create Table", e);
		}
	}

	@Override
	public void save(QuantityMeasurementEntity entity) {
		Connection conn = null;
		try {
			conn = connectionPool.getConnection();
			PreparedStatement ps = conn.prepareStatement(INSERT_QUERY);
			ps.setDouble(1, entity.thisValue);
			ps.setString(2, entity.thisUnit);
			ps.setString(3, entity.thisMeasurementType);
			ps.setDouble(4, entity.thatValue);
			ps.setString(5, entity.thatUnit);
			ps.setString(6, entity.thatMeasurementType);
			ps.setString(7, entity.operation);
			ps.setDouble(8, entity.resultValue);
			ps.setString(9, entity.resultUnit);
			ps.setString(10, entity.resultMeasurementType);
			ps.setString(11, entity.resultString);
			ps.setBoolean(12, entity.isError);
			ps.setString(13, entity.errorMessage);
			ps.executeUpdate();
		} catch (Exception e) {
			throw DatabaseException.queryFailed("Insert Query", e);
		} finally {
			if (conn != null) {
				connectionPool.releaseConnection(conn);
			}
		}
	}

	@Override
	public List<QuantityMeasurementEntity> getAllMeasurements() {
		List<QuantityMeasurementEntity> list = new ArrayList<>();
		String query = "SELECT * FROM quantity_measurement_entity ORDER BY created_at DESC";
		Connection conn = null;
		try {
			conn = connectionPool.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				QuantityMeasurementEntity entity = mapResultSetToEntity(rs);
				list.add(entity);
			}
		} catch (Exception e) {
			throw DatabaseException.queryFailed(query, e);
		} finally {
			if (conn != null) {
				connectionPool.releaseConnection(conn);
			}
		}
		return list;
	}

	private QuantityMeasurementEntity mapResultSetToEntity(ResultSet rs) throws SQLException {
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
		entity.thisValue = rs.getDouble("this_value");
		entity.thisUnit = rs.getString("this_unit");
		entity.thisMeasurementType = rs.getString("this_measurement_type");
		entity.thatValue = rs.getDouble("that_value");
		entity.thatUnit = rs.getString("that_unit");
		entity.thatMeasurementType = rs.getString("that_measurement_type");
		entity.operation = rs.getString("operation");
		entity.resultValue = rs.getDouble("result_value");
		entity.resultUnit = rs.getString("result_unit");
		entity.resultMeasurementType = rs.getString("result_measurement_type");
		entity.resultString = rs.getString("result_string");
		entity.isError = rs.getBoolean("is_error");
		entity.errorMessage = rs.getString("error_message");
		return entity;
	}

	public int getTotalCount() {
		String query = "SELECT COUNT(*) FROM quantity_measurement_entity";
		try (Connection conn = connectionPool.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			throw DatabaseException.queryFailed(query, e);
		}
		return 0;
	}

	public void deleteAll() {
		String query = "DELETE FROM quantity_measurement_entity";
		try (Connection conn = connectionPool.getConnection(); Statement stmt = conn.createStatement()) {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			throw DatabaseException.queryFailed(query, e);
		}
	}
}