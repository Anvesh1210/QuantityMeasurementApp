package com.apps.quantitymeasurement.repository;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;

public class QuantityMeasurementCacheRepository implements IQuantityMeasurementRepository {

	public static final String FILE_NAME = "quantity_measurement_repo.ser";

	List<QuantityMeasurementEntity> entities;

	private static QuantityMeasurementCacheRepository instance;

	private QuantityMeasurementCacheRepository() {
		entities = new ArrayList<>();
		loadFromDisk();
	}

	public static QuantityMeasurementCacheRepository getInstance() {
		if (instance == null) {
			instance = new QuantityMeasurementCacheRepository();
		}
		return instance;
	}

	@Override
	public void save(QuantityMeasurementEntity entity) {
		entities.add(entity);
		saveToDisk(entity);
	}

	// SAVE ENTITY TO FILE
	private void saveToDisk(QuantityMeasurementEntity entity) {
		try (FileOutputStream fos = new FileOutputStream(FILE_NAME, true);
				ObjectOutputStream oos = (new File(FILE_NAME).length() == 0) ? new ObjectOutputStream(fos)
						: new AppendableObjectOutputStream(fos)) {
			oos.writeObject(entity);
		} catch (IOException e) {
			System.err.println("Error saving measurement to disk: " + e.getMessage());
		}
	}

	// LOAD DATA WHEN APPLICATION STARTS
	private void loadFromDisk() {
		File file = new File(FILE_NAME);
		if (!file.exists())
			return;
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
			while (true) {
				QuantityMeasurementEntity entity = (QuantityMeasurementEntity) ois.readObject();
				entities.add(entity);
			}
		} catch (EOFException e) {
			// End of file reached
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("Error loading measurements: " + e.getMessage());
		}
	}

	@Override
	public List<QuantityMeasurementEntity> getAllMeasurements() {
		return entities;
	}

}
