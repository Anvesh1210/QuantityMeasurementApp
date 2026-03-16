package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.core.Quantity;
import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.entity.QuantityModel;
import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.units.IMeasurable;
import com.app.quantitymeasurement.units.LengthUnit;
import com.app.quantitymeasurement.units.TemperatureUnit;
import com.app.quantitymeasurement.units.VolumeUnit;
import com.app.quantitymeasurement.units.WeightUnit;

public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

	public IQuantityMeasurementRepository repository;

	public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
		this.repository = repository;
	}

	// -------------------- COMPARISON --------------------

	@Override
	public boolean compare(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {

		QuantityModel<IMeasurable> m1 = getQuantityModel(thisQuantityDTO);
		QuantityModel<IMeasurable> m2 = getQuantityModel(thatQuantityDTO);

		Quantity<IMeasurable> q1 = new Quantity<>(m1.getValue(), m1.getUnit());
		Quantity<IMeasurable> q2 = new Quantity<>(m2.getValue(), m2.getUnit());

		try {
			boolean result = q1.equals(q2);
			repository.save(new QuantityMeasurementEntity(m1, m2, "COMPARE", result));
			return result;
		} catch (Exception e) {
			throw new QuantityMeasurementException("Comparison failed", e);
		}
	}

	// -------------------- CONVERSION --------------------

	@Override
	public QuantityDTO convert(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {

		QuantityModel<IMeasurable> model = getQuantityModel(thisQuantityDTO);

		Quantity<IMeasurable> quantity = new Quantity<>(model.getValue(), model.getUnit());

		IMeasurable targetUnit = getUnitInstance(thatQuantityDTO.getUnit(), thatQuantityDTO.getMeasurementType());

		Quantity<IMeasurable> converted;
		try {
			converted = quantity.convertTo(targetUnit);
		} catch (Exception e) {
			throw new QuantityMeasurementException("Conversion failed", e);
		}

		QuantityDTO result = new QuantityDTO(converted.getValue(), converted.getUnit().getUnitName(),
				converted.getUnit().getMeasurementType());

		repository.save(new QuantityMeasurementEntity(model, getQuantityModel(thatQuantityDTO), "CONVERT",
				new QuantityModel<>(converted.getValue(), converted.getUnit())));

		return result;
	}

	// -------------------- ADDITION --------------------

	@Override
	public QuantityDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {

		QuantityModel<IMeasurable> m1 = getQuantityModel(thisQuantityDTO);
		QuantityModel<IMeasurable> m2 = getQuantityModel(thatQuantityDTO);

		Quantity<IMeasurable> q1 = new Quantity<>(m1.getValue(), m1.getUnit());
		Quantity<IMeasurable> q2 = new Quantity<>(m2.getValue(), m2.getUnit());

		Quantity<IMeasurable> resultQuantity = q1.add(q2);

		QuantityDTO result = new QuantityDTO(resultQuantity.getValue(), resultQuantity.getUnit().getUnitName(),
				resultQuantity.getUnit().getMeasurementType());

		repository.save(new QuantityMeasurementEntity(m1, m2, "ADD",
				new QuantityModel<>(resultQuantity.getValue(), resultQuantity.getUnit())));

		return result;
	}

	@Override
	public QuantityDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO) {

		QuantityModel<IMeasurable> m1 = getQuantityModel(thisQuantityDTO);
		QuantityModel<IMeasurable> m2 = getQuantityModel(thatQuantityDTO);

		Quantity<IMeasurable> q1 = new Quantity<>(m1.getValue(), m1.getUnit());
		Quantity<IMeasurable> q2 = new Quantity<>(m2.getValue(), m2.getUnit());

		IMeasurable targetUnit = getUnitInstance(targetUnitDTO.getUnit(), targetUnitDTO.getMeasurementType());

		Quantity<IMeasurable> resultQuantity = q1.add(q2, targetUnit);

		QuantityDTO result = new QuantityDTO(resultQuantity.getValue(), resultQuantity.getUnit().getUnitName(),
				resultQuantity.getUnit().getMeasurementType());

		repository.save(new QuantityMeasurementEntity(m1, m2, "ADD",
				new QuantityModel<>(resultQuantity.getValue(), resultQuantity.getUnit())));

		return result;
	}

	// -------------------- SUBTRACTION --------------------

	@Override
	public QuantityDTO subtarct(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {

		QuantityModel<IMeasurable> m1 = getQuantityModel(thisQuantityDTO);
		QuantityModel<IMeasurable> m2 = getQuantityModel(thatQuantityDTO);

		Quantity<IMeasurable> q1 = new Quantity<>(m1.getValue(), m1.getUnit());
		Quantity<IMeasurable> q2 = new Quantity<>(m2.getValue(), m2.getUnit());

		Quantity<IMeasurable> resultQuantity = q1.subtract(q2);

		QuantityDTO result = new QuantityDTO(resultQuantity.getValue(), resultQuantity.getUnit().getUnitName(),
				resultQuantity.getUnit().getMeasurementType());

		repository.save(new QuantityMeasurementEntity(m1, m2, "SUBTRACT",
				new QuantityModel<>(resultQuantity.getValue(), resultQuantity.getUnit())));

		return result;
	}

	@Override
	public QuantityDTO subtarct(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO) {

		QuantityModel<IMeasurable> m1 = getQuantityModel(thisQuantityDTO);
		QuantityModel<IMeasurable> m2 = getQuantityModel(thatQuantityDTO);

		Quantity<IMeasurable> q1 = new Quantity<>(m1.getValue(), m1.getUnit());
		Quantity<IMeasurable> q2 = new Quantity<>(m2.getValue(), m2.getUnit());
		IMeasurable targetUnit = getUnitInstance(targetUnitDTO.getUnit(), targetUnitDTO.getMeasurementType());
		Quantity<IMeasurable> resultQuantity = q1.subtract(q2, targetUnit);

		QuantityDTO result = new QuantityDTO(resultQuantity.getValue(), resultQuantity.getUnit().getUnitName(),
				resultQuantity.getUnit().getMeasurementType());

		repository.save(new QuantityMeasurementEntity(m1, m2, "SUBTRACT",
				new QuantityModel<>(resultQuantity.getValue(), resultQuantity.getUnit())));

		return result;
	}

	// -------------------- DIVISION --------------------

	@Override
	public QuantityDTO division(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {

		QuantityModel<IMeasurable> m1 = getQuantityModel(thisQuantityDTO);
		QuantityModel<IMeasurable> m2 = getQuantityModel(thatQuantityDTO);

		Quantity<IMeasurable> q1 = new Quantity<>(m1.getValue(), m1.getUnit());
		Quantity<IMeasurable> q2 = new Quantity<>(m2.getValue(), m2.getUnit());

		if (m2.getValue() == 0) {
			throw new QuantityMeasurementException("Division by zero not allowed");
		}

		double resultValue = q1.divide(q2);

		QuantityDTO result = new QuantityDTO(resultValue, thisQuantityDTO.getUnit(),
				thisQuantityDTO.getMeasurementType());

		repository.save(new QuantityMeasurementEntity(m1, m2, "DIVIDE", String.valueOf(resultValue)));

		return result;
	}

	// -------------------- DTO → MODEL --------------------

	private QuantityModel<IMeasurable> getQuantityModel(QuantityDTO dto) {

		IMeasurable unit = getUnitInstance(dto.getUnit(), dto.getMeasurementType());

		return new QuantityModel<>(dto.getValue(), unit);
	}

	// -------------------- UNIT MAPPING --------------------

	private IMeasurable getUnitInstance(String unitName, String measurementType) {

		switch (measurementType) {

		case "LENGTH":
			return LengthUnit.valueOf(unitName);

		case "WEIGHT":
			return WeightUnit.valueOf(unitName);

		case "VOLUME":
			return VolumeUnit.valueOf(unitName);

		case "TEMPERATURE":
			return TemperatureUnit.valueOf(unitName);

		default:
			throw new IllegalArgumentException("Invalid Measurement Type");
		}
	}
}