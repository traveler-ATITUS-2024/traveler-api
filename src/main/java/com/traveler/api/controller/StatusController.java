package com.traveler.api.controller;

import com.traveler.api.controller.dto.CriarStatusDto;
import com.traveler.api.entity.Status;
import com.traveler.api.service.StatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/status")
@Tag(name = "Status")
public class StatusController {

    @Autowired
    private StatusService statusService;

    @Operation(summary = "Criar um status", description = "Criar um novo status")
    @PostMapping
    public ResponseEntity<Status> criarStatus(@RequestBody @Valid CriarStatusDto criarStatusDto) {
        try {
            Status status  = statusService.criarStatus(criarStatusDto);
            return new ResponseEntity<>(status, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Deletar um status", description = "Deletar um status do banco de dados")
    @DeleteMapping("/{statusId}")
    public ResponseEntity<?> deletarStatus(@PathVariable("statusId") Long statusId) {
        try {

        statusService.deletarStatus(statusId);
        return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
