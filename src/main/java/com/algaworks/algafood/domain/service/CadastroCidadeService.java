package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRespository;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroCidadeService {

    @Autowired
    private CidadeRespository cidadeRespository;

    @Autowired
    private EstadoRepository estadoRepository;

    public Cidade salvar(Cidade cidade){
        Long estadoId = cidade.getEstado().getId();
        var estado = estadoRepository.findById(estadoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não existe cadastro de estado com o código %d", estadoId)));
        cidade.setEstado(estado);
        return cidadeRespository.save(cidade);
    }

    public Cidade atualizar(Long cidadeId, Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
        var estado = estadoRepository.findById(estadoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não existe cadastro de estado com o código %d", estadoId)));
        var cidadeSalva = cidadeRespository.findById(cidadeId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não existe cadastro de cidade com o código %d", cidadeId)));

        BeanUtils.copyProperties(cidade, cidadeSalva, "id");
        cidadeSalva = cidadeRespository.save(cidadeSalva);
        return cidadeSalva;
    }

    public void excluir(Long cidadeId) {
        try {
            cidadeRespository.deleteById(cidadeId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe um cadastro de cidade com o código %d", cidadeId));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Cidade de código %d não pode ser remivida, pois está em uso", cidadeId));
        }
    }


}
