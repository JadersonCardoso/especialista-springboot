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

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        var cozinha = cozinhaRepository.findById(cozinhaId);

        if(cozinha.isEmpty()) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe cadastro de cozinha com o código %d", cozinhaId));
        }
        restaurante.setCozinha(cozinha.get());
        return restauranteRepository.save(restaurante);
    }


    public Restaurante atualizar(Long restauranteId, Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        var cozinha = cozinhaRepository.findById(cozinhaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("Não existe cadastro de cozinha com o código %d", cozinhaId)
                ));

        var restauranteSalvo = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("Não existe cadastro de restaurante com o código %d", restauranteId)));

        BeanUtils.copyProperties(restaurante, restauranteSalvo, "id", "formasPagamento");
        restauranteSalvo = restauranteRepository.save(restauranteSalvo);
        return restauranteSalvo;
    }



}
