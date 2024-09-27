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
}



