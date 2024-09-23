package com.traveler.api.service;

import com.traveler.api.controller.dto.ViagemInputDto;
import com.traveler.api.entity.Usuario;
import com.traveler.api.entity.Viagem;
import com.traveler.api.repository.ViagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class ViagemService {

    @Autowired
    private ViagemRepository viagemRepository;


    public Viagem criarViagem(ViagemInputDto viagemInputDto, Usuario usuario) {

        var entity = new Viagem(
                viagemInputDto.nome(),
                viagemInputDto.status(),
                new Timestamp(viagemInputDto.dataIda().getTime()),
                new Timestamp(viagemInputDto.dataVolta().getTime()),
                viagemInputDto.valorPrv(),
                viagemInputDto.valorReal(),
                viagemInputDto.latitude(),
                viagemInputDto.longitude()
        );

        entity.setUsuario(usuario);

//        entity.setUsuario(new Usuario("xande", Integer.toUnsignedLong(1)));
        return viagemRepository.save(entity);
    }

    public Optional<Viagem> buscarViagemPorId(String viagemId) {
        return viagemRepository.findById(Long.parseLong(viagemId));
    }

    public List<Viagem> buscarViagens() {
        return viagemRepository.findAll();
    }

    public Viagem alterarViagem(String id, ViagemInputDto viagemInputDto, Usuario usuario) throws Exception {
        Long idViagem = Long.parseLong(id);

        Optional<Viagem> viagem = viagemRepository.findById(idViagem);

        if (viagem.isPresent()) {
            return viagemRepository.save(viagemInputDto.toViagemWithId(idViagem, usuario));

        }

        throw new Exception("Algum erro foi encontrado");

    }
}
