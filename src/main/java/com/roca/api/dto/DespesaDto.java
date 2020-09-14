package com.roca.api.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class DespesaDto {

    private Date data;

    private String descricao;

    private BigDecimal quantidade;

    private BigDecimal valorUnitario;

    private BigDecimal total;
}
