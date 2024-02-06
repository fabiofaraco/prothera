package br.com.fabio.prothera.model;

import jakarta.persistence.Entity;

import java.math.BigDecimal;

/**
 * Created by fabiofaraco on 05/02/2024.
 */

@Entity
public class Funcionario extends Pessoa
{
	private BigDecimal salario;

	private String funcao;

	public BigDecimal getSalario()
	{
		return salario;
	}

	public void setSalario(final BigDecimal salario)
	{
		this.salario = salario;
	}

	public String getFuncao()
	{
		return funcao;
	}

	public void setFuncao(final String funcao)
	{
		this.funcao = funcao;
	}
}
