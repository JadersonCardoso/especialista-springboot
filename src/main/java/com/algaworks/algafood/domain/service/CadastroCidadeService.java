package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRespository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroCidadeService {

    public static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser remivida, pois está em uso";

    @Autowired
    private CidadeRespository cidadeRespository;


    @Autowired
    private CadastroEstadoService cadastroEstado;

    public Cidade salvar(Cidade cidade){
        Long estadoId = cidade.getEstado().getId();
        var estado = cadastroEstado.buscarOuFalhar(estadoId);
        cidade.setEstado(estado);
        return cidadeRespository.save(cidade);
    }

    public Cidade atualizar(Long cidadeId, Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
        var estado = cadastroEstado.buscarOuFalhar(estadoId);
        var cidadeSalva = buscarOuFalhar(cidadeId);

        BeanUtils.copyProperties(cidade, cidadeSalva, "id");
        cidadeSalva = cidadeRespository.save(cidadeSalva);
        return cidadeSalva;
    }

    public void excluir(Long cidadeId) {
        try {
            cidadeRespository.deleteById(cidadeId);
        } catch (EmptyResultDataAccessException e) {
            throw new CidadeNaoEncontradaException(cidadeId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_CIDADE_EM_USO, cidadeId));
        }
    }


    public Cidade buscarOuFalhar(Long cidadeId) {
        return cidadeRespository.findById(cidadeId)
                .orElseThrow(() -> new CidadeNaoEncontradaException(cidadeId));
    }


}
