package com.traveler.api.service;

import com.traveler.api.controller.dto.CriarStatusDto;
import com.traveler.api.entity.Status;
import com.traveler.api.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    public Status criarStatus(CriarStatusDto criarStatusDto) {

        Status entity = new Status(
                criarStatusDto.status()
        );

        return statusRepository.save(entity);
    }

    public void deletarStatus(Long statusId) {

        Optional<Status> statusExistente = statusRepository.findById(statusId);

        if (statusExistente.isPresent()) {
            statusRepository.deleteById(statusId);
        }
    }
}
