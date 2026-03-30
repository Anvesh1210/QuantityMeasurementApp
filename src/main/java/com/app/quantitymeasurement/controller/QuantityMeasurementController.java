package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/quantities")
@Tag(name = "Quantity Measurements", description = "Operations related to quantity calculations")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class QuantityMeasurementController {

    @Autowired
    private IQuantityMeasurementService quantityMeasurementService;

    // COMPARE
    @Operation(summary = "Compare two quantities", description = "Returns true if both quantities are equal")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Comparison successful"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/compare")
    public ResponseEntity<?> performComparison(@Valid @RequestBody QuantityInputDTO input) {
        try {
            return ResponseEntity.ok(
                    quantityMeasurementService.compare(
                            input.getThisQuantityDTO(),
                            input.getThatQuantityDTO()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //CONVERT
    @Operation(summary = "Convert quantity", description = "Converts one quantity into another unit")
    @PostMapping("/convert")
    public ResponseEntity<?> performConversion(@Valid @RequestBody QuantityInputDTO input) {
        try {
            return ResponseEntity.ok(
                    quantityMeasurementService.convert(
                            input.getThisQuantityDTO(),
                            input.getThatQuantityDTO()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ADD
    @Operation(summary = "Add two quantities", description = "Adds two quantities")
    @PostMapping("/add")
    public ResponseEntity<?> performAddition(@Valid @RequestBody QuantityInputDTO input) {
        try {
            return ResponseEntity.ok(
                    quantityMeasurementService.add(
                            input.getThisQuantityDTO(),
                            input.getThatQuantityDTO()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //ADD WITH TARGET 
    @Operation(summary = "Add with target unit", description = "Adds two quantities and converts result to target unit")
    @PostMapping("/add-with-target-unit")
    public ResponseEntity<?> performAdditionWithTargetUnit(@Valid @RequestBody QuantityInputDTO input) {
        try {
            return ResponseEntity.ok(
                    quantityMeasurementService.add(
                            input.getThisQuantityDTO(),
                            input.getThatQuantityDTO(),
                            input.getTargetQuantityDTO()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // SUBTRACT
    @Operation(summary = "Subtract two quantities", description = "Subtracts second quantity from first")
    @PostMapping("/subtract")
    public ResponseEntity<?> performSubtraction(@Valid @RequestBody QuantityInputDTO input) {
        try {
            return ResponseEntity.ok(
                    quantityMeasurementService.subtract(
                            input.getThisQuantityDTO(),
                            input.getThatQuantityDTO()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // SUBTRACT WITH TARGET
    @Operation(summary = "Subtract with target unit", description = "Subtract and convert result to target unit")
    @PostMapping("/subtract-with-target-unit")
    public ResponseEntity<?> performSubtractionWithTargetUnit(@Valid @RequestBody QuantityInputDTO input) {
        try {
            return ResponseEntity.ok(
                    quantityMeasurementService.subtract(
                            input.getThisQuantityDTO(),
                            input.getThatQuantityDTO(),
                            input.getTargetQuantityDTO()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DIVIDE 
    @Operation(summary = "Divide quantities", description = "Divides first quantity by second")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Division successful"),
        @ApiResponse(responseCode = "500", description = "Divide by zero error")
    })
    @PostMapping("/divide")
    public ResponseEntity<?> performDivision(@Valid @RequestBody QuantityInputDTO input) {
        try {
            return ResponseEntity.ok(
                    quantityMeasurementService.divide(
                            input.getThisQuantityDTO(),
                            input.getThatQuantityDTO()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // HISTORY 
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
