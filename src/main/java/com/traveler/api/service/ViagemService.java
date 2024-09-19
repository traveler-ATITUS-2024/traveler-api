package com.traveler.api.service;

import com.traveler.api.controller.dto.CriarViagemDto;
import com.traveler.api.entity.Usuario;
import com.traveler.api.entity.Viagem;
import com.traveler.api.repository.ViagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class ViagemService {

    @Autowired
    private ViagemRepository viagemRepository;


    public Viagem criarViagem(CriarViagemDto criarViagemDto, Usuario usuario) {

        var entity = new Viagem(
                criarViagemDto.nome(),
                criarViagemDto.status(),
                new Timestamp(criarViagemDto.dataIda().getTime()),
                new Timestamp(criarViagemDto.dataVolta().getTime()),
                criarViagemDto.valorPrv(),
                criarViagemDto.valorReal(),
                criarViagemDto.latitude(),
                criarViagemDto.longitude()
        );

        entity.setUsuario(usuario);

//        entity.setUsuario(new Usuario("xande", Integer.toUnsignedLong(1)));
        return viagemRepository.save(entity);
    }
}
