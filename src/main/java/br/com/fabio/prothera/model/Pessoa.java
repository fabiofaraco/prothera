package br.com.fabio.prothera.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDate;

/**
 * Created by fabiofaraco on 05/02/2024.
 */
@MappedSuperclass
public class Pessoa
{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	private LocalDate dataNascimento;

	public Long getId()
	{
		return id;
	}

	public void setId(final Long id)
	{
		this.id = id;
	}

	public String getNome()
	{
		return nome;
	}

	public void setNome(final String nome)
	{
		this.nome = nome;
	}

	public LocalDate getDataNascimento()
	{
		return dataNascimento;
	}

	public void setDataNascimento(final LocalDate dataNascimento)
	{
		this.dataNascimento = dataNascimento;
	}
}
