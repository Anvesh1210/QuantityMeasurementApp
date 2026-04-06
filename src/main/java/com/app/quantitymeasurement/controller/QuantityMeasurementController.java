package com.app.quantitymeasurement.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/quantities")
@Tag(name = "Quantity Measurements", description = "Operations related to quantity calculations")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class QuantityMeasurementController {

	private final IQuantityMeasurementService quantityMeasurementService;

	public QuantityMeasurementController(IQuantityMeasurementService quantityMeasurementService) {
		this.quantityMeasurementService = quantityMeasurementService;
	}

	@Operation(summary = "Compare two quantities", description = "Returns true if both quantities are equal")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Comparison successful"),
			@ApiResponse(responseCode = "400", description = "Invalid input") })
	@PostMapping("/compare")
	public ResponseEntity<QuantityMeasurementDTO> performComparison(@Valid @RequestBody QuantityInputDTO input) {
		return ResponseEntity.ok(quantityMeasurementService.compare(input.getThisQuantityDTO(), input.getThatQuantityDTO()));
	}

	@Operation(summary = "Convert quantity", description = "Converts one quantity into another unit")
	@PostMapping("/convert")
	public ResponseEntity<QuantityMeasurementDTO> performConversion(@Valid @RequestBody QuantityInputDTO input) {
		return ResponseEntity.ok(quantityMeasurementService.convert(input.getThisQuantityDTO(), input.getThatQuantityDTO()));
	}

	@Operation(summary = "Add two quantities", description = "Adds two quantities")
	@PostMapping("/add")
	public ResponseEntity<QuantityMeasurementDTO> performAddition(@Valid @RequestBody QuantityInputDTO input) {
		return ResponseEntity.ok(quantityMeasurementService.add(input.getThisQuantityDTO(), input.getThatQuantityDTO()));
	}

	@Operation(summary = "Add with target unit", description = "Adds two quantities and converts result to target unit")
	@PostMapping("/add-with-target-unit")
	public ResponseEntity<QuantityMeasurementDTO> performAdditionWithTargetUnit(
			@Valid @RequestBody QuantityInputDTO input) {
		return ResponseEntity
				.ok(quantityMeasurementService.add(input.getThisQuantityDTO(), input.getThatQuantityDTO(),
						input.getTargetQuantityDTO()));
	}

	@Operation(summary = "Subtract two quantities", description = "Subtracts second quantity from first")
	@PostMapping("/subtract")
	public ResponseEntity<QuantityMeasurementDTO> performSubtraction(@Valid @RequestBody QuantityInputDTO input) {
		return ResponseEntity.ok(quantityMeasurementService.subtract(input.getThisQuantityDTO(), input.getThatQuantityDTO()));
	}

	@Operation(summary = "Subtract with target unit", description = "Subtract and convert result to target unit")
	@PostMapping("/subtract-with-target-unit")
	public ResponseEntity<QuantityMeasurementDTO> performSubtractionWithTargetUnit(
			@Valid @RequestBody QuantityInputDTO input) {
		return ResponseEntity
				.ok(quantityMeasurementService.subtract(input.getThisQuantityDTO(), input.getThatQuantityDTO(),
						input.getTargetQuantityDTO()));
	}

	@Operation(summary = "Divide quantities", description = "Divides first quantity by second")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Division successful"),
			@ApiResponse(responseCode = "400", description = "Invalid input") })
	@PostMapping("/divide")
	public ResponseEntity<QuantityMeasurementDTO> performDivision(@Valid @RequestBody QuantityInputDTO input) {
		return ResponseEntity.ok(quantityMeasurementService.divide(input.getThisQuantityDTO(), input.getThatQuantityDTO()));
	}

	@Operation(summary = "Get operation history")
	@GetMapping("/history/operation/{operation}")
	public ResponseEntity<List<QuantityMeasurementDTO>> getOperationHistory(@PathVariable String operation) {
		return ResponseEntity.ok(quantityMeasurementService.getOperationHistory(operation));
	}

	@Operation(summary = "Get history by type")
	@GetMapping("/history/type/{type}")
	public ResponseEntity<List<QuantityMeasurementDTO>> getHistoryByType(@PathVariable String type) {
		return ResponseEntity.ok(quantityMeasurementService.getMeasurementsByType(type));
	}

	@Operation(summary = "Get operation count")
	@GetMapping("/count/{operation}")
	public ResponseEntity<Long> getOperationCount(@PathVariable String operation) {
		return ResponseEntity.ok(quantityMeasurementService.getOperationCount(operation));
	}

	@Operation(summary = "Get errored operations")
	@GetMapping("/history/errored")
	public ResponseEntity<List<QuantityMeasurementDTO>> getErroredOperations() {
		return ResponseEntity.ok(quantityMeasurementService.getErrorHistory());
	}
}
