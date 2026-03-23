package com.app.quantitymeasurement.repository;

import java.util.List;

import com.app.quantitymeasurement.model.QuantityMeasurementEntity;

public interface IQuantityMeasurementRepository {
	
	//Saves a QuantityMeasurementEntity to the repository
	void save(QuantityMeasurementEntity entity);
	
	//return a list of all QuantityMeasurementEntity instances form repository
	List<QuantityMeasurementEntity> getAllMeasurements();
	
	//main method for testing purpose
	public static void main(String []args) {
		System.out.println("Testing IQuantityMeasurementRepository interface");
	}

}
