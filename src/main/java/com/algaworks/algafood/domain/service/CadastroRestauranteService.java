package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroRestauranteService {

    public static final String MSG_RESTAURANTE_NAO_ENCONTRADO = "Não existe cadastro de restaurante com o código %d";
    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        var cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
        restaurante.setCozinha(cozinha);
        return restauranteRepository.save(restaurante);
    }


    public Restaurante atualizar(Long restauranteId, Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        var cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
        var restauranteSalvo = buscarOuFalar(restauranteId);

        BeanUtils.copyProperties(restaurante, restauranteSalvo,
                "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
        restauranteSalvo = restauranteRepository.save(restauranteSalvo);
        return restauranteSalvo;
    }


    public Restaurante buscarOuFalar(Long restauranteId){
        return restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, restauranteId)));
    }



}
