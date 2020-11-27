package com.algaworks.algafood.integracao;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/integracao")
public class ControllerIntegracao {


    private String uri = "http://localhost:5000/v1/integracoes/pimpos/";

    @GetMapping("/{cpf}")
    public ResponseEntity<?> buscaIntegracao(@PathVariable(value = "cpf") String cpfResponsavel){
        List<PimpoDTO> pimpos = new ArrayList<>();
        String uri = this.uri+cpfResponsavel;
        RestTemplate restTemplate = new RestTemplate();
        try{
            restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("pimpoIntegracao", "pimpoIntegration"));
            ResponseEntity<List<PimpoDTO>> pimpoDTOS =restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<PimpoDTO>>() {});
            if(pimpoDTOS != null && pimpoDTOS.hasBody()){
                pimpos = pimpoDTOS.getBody();
            }
        } catch (RestClientException e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(pimpos);
    }

}
