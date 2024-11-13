package com.traveler.api.service;

import com.traveler.api.controller.dto.ViagemInputDto;
import com.traveler.api.entity.Despesa;
import com.traveler.api.entity.Usuario;
import com.traveler.api.entity.Viagem;
import com.traveler.api.repository.DespesaRepository;
import com.traveler.api.repository.ViagemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class ViagemService {

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private ViagemRepository viagemRepository;


    public Viagem criarViagem(ViagemInputDto viagemInputDto, Usuario usuario) {

        viagemInputDto.validarDatas();

        var entity = new Viagem(
                viagemInputDto.nome(),
                viagemInputDto.statusId(),
                new Timestamp(viagemInputDto.dataIda().getTime()),
                new Timestamp(viagemInputDto.dataVolta().getTime()),
                viagemInputDto.valorPrv(),
                viagemInputDto.valorReal(),
                viagemInputDto.latitude(),
                viagemInputDto.longitude()
        );

        entity.setUsuario(usuario);

        return viagemRepository.save(entity);
    }

    public Optional<Viagem> buscarViagemPorId(String viagemId) {
        Optional<Viagem> viagemOpt = viagemRepository.findById(Long.parseLong(viagemId));

        if (viagemOpt.isPresent()) {
            Viagem viagem = viagemOpt.get();

            List<Despesa> despesas = despesaRepository.findByViagemId(viagem.getId());

            double valorTotalDespesas = despesas.stream()
                    .mapToDouble(despesa -> despesa.getValor().doubleValue())
                    .sum();


            viagem.setValorTotalDespesas(valorTotalDespesas);

            return Optional.of(viagem);
        } else {
            return Optional.empty();
        }
    }


    public List<Viagem> buscarViagens() {
        return viagemRepository.findAll();
    }

    public List<Viagem> buscarViagensPorUsuario(Long usuarioId) {
        return viagemRepository.findByUsuarioId(usuarioId);
    }

    public Viagem alterarViagem(String id, ViagemInputDto viagemInputDto, Usuario usuario) throws Exception {
        Long idViagem = Long.parseLong(id);

        Optional<Viagem> viagem = viagemRepository.findById(idViagem);

        if (viagem.isPresent()) {
            return viagemRepository.save(viagemInputDto.toViagemWithId(idViagem, usuario));

        }

        throw new Exception("Viagem com id " + id + " não foi encontrada.");

    }

    public void deletarViagem(String viagemId) throws Exception {

        Long id = Long.parseLong(viagemId);

        if (!viagemRepository.existsById(id)) {
            throw new Exception("Viagem com id " + id + " não existe.");
        }

        viagemRepository.deleteById(id);


    }
}
