package com.traveler.api.service;

import com.traveler.api.controller.dto.DespesaInputDto;
import com.traveler.api.entity.Categoria;
import com.traveler.api.entity.Despesa;
import com.traveler.api.entity.Usuario;
import com.traveler.api.entity.Viagem;
import com.traveler.api.repository.CategoriaRepository;
import com.traveler.api.repository.DespesaRepository;
import com.traveler.api.repository.ViagemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class DespesaService {

    @Autowired
    private DespesaRepository despesaRepository;
    @Autowired
    private ViagemRepository viagemRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional
    public Despesa criarDespesa(DespesaInputDto despesaInputDto)  throws Exception{

        Categoria categoria = categoriaRepository.findById(despesaInputDto.categoriaId())
                .orElseThrow(() -> new Exception("Não foi encontrada nenhuma categoria"));

        Viagem viagem = viagemRepository.findById(despesaInputDto.viagemId())
                .orElseThrow(() -> new Exception("Não foi encontrada nenhuma viagem"));

        Despesa despesa = new Despesa();
        despesa.setCategoriaId(categoria.getId());
        despesa.setViagemId(viagem.getId());
        despesa.setNome(despesaInputDto.nome());
        despesa.setDecricao(despesaInputDto.descricao());
        despesa.setData(new Timestamp(despesaInputDto.data().getTime()));
        despesa.setValor(despesaInputDto.valor());

        Despesa saved = despesaRepository.save(despesa);
        viagem.setValorReal(viagem.getValorReal().add(despesa.getValor()));
        viagemRepository.save(viagem);
        return saved;
    }

    public List<Despesa> buscarDespesas() {return despesaRepository.findAll();}

    public Optional<Despesa> buscarDespesaPorId(String despesaId) {
        return despesaRepository.findById(Long.parseLong(despesaId));
    }

    public List<Despesa> buscarDespesasPorViagem(Long viagemId) {
        return despesaRepository.findByViagemId(viagemId);
    }

    public Despesa alterarDespesa(Long id, DespesaInputDto despesaInputDto) throws Exception{
        Optional<Despesa> despesa = despesaRepository.findById(id);

        Viagem viagem = viagemRepository.findById(despesaInputDto.viagemId())
                .orElseThrow(() -> new Exception("Não foi encontrada nenhuma viagem"));

        if(despesa.isPresent()) {
            viagem.setValorReal(viagem.getValorReal().subtract(despesa.get().getValor()));
            viagem.setValorReal(viagem.getValorReal().add(despesaInputDto.valor()));
            return despesaRepository.save(
                    despesaInputDto.toDespesaWithId(id)
            );
        }

        throw new Exception("Despesa com o id " + id + " não foi encontrada.");
    }

    @Transactional
    public void deletarDespesa(Long id) throws Exception{
        if (!despesaRepository.existsById(id)) {
            throw new Exception("Viagem com id " + id + " não existe.");
        }

        Despesa despesa = despesaRepository.findById(id)
                .orElseThrow(() -> new Exception("Despesa com id " + id + " não encontrada."));

        Long viagemId = despesa.getViagemId();

        Viagem viagem = viagemRepository.findById(viagemId)
                .orElseThrow(() -> new Exception("Viagem com id " + viagemId + " não encontrada."));

        viagem.setValorReal(viagem.getValorReal().subtract(despesa.getValor()));
        despesaRepository.deleteById(id);


        viagemRepository.save(viagem);

    }
}



