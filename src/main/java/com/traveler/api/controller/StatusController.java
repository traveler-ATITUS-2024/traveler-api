package com.traveler.api.controller;

import com.traveler.api.controller.dto.CriarStatusDto;
import com.traveler.api.entity.Status;
import com.traveler.api.service.StatusService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/status")
public class StatusController {

    @Autowired
    private StatusService statusService;

    @PostMapping
    public ResponseEntity<Status> criarStatus(@RequestBody @Valid CriarStatusDto criarStatusDto) {
        try {
            Status status  = statusService.criarStatus(criarStatusDto);
            return new ResponseEntity<>(status, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{statusId}")
    public ResponseEntity<Void> deletarStatus(@PathVariable("statusId") Long statusId) {
        statusService.deletarStatus(statusId);
        return ResponseEntity.noContent().build();
    }

}
