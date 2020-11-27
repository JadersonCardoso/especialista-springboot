package com.algaworks.algafood.integracao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PimpoDTO {

    private Long idPimpo;
    private String nome;
    private String sobrenome;
    private LocalDate dataNascimento;
    private LocalTime horaNascimento;
    private String localNascimento;
    private String cidadeNascimento;
    private Integer idade;
    private TipoSanguineoEnum tipoSanguineo;
    private String numeroSus;
    private GeneroEnum genero;
    private String urlFoto;
    private Double peso;
    private Double altura;
    private Double perimetroCefalico;
    private String cpf;
    private boolean testePezinho;
    private boolean testeCoracao;
    private boolean testeAuditivo;
    private boolean testeOlinho;


}
