package br.com.fabio.prothera.service;

import br.com.fabio.prothera.model.Funcionario;
import br.com.fabio.prothera.repository.FuncionarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by fabiofaraco on 05/02/2024.
 */

@Service
public class FuncionarioService
{
	@Autowired
	private FuncionarioRepository funcionarioRepository;

	public List<Funcionario> listaTodos()
	{
		return funcionarioRepository.findAll();
	}
	public List<Funcionario> listaTodos(final Sort sort)
	{
		return funcionarioRepository.findAll(sort);
	}

	public List<Funcionario> listaPorMesNascimento(final List<Integer> meses)
	{
		return funcionarioRepository.listByMonths(meses);
	}

	public Funcionario buscaPorNome(final String nome)
	{
		return funcionarioRepository.findByNome(nome);
	}

	public Funcionario buscaPorMaiorIdade()
	{
		return listaTodos().stream()
				.min(Comparator.comparing(Funcionario::getDataNascimento))
				.orElseThrow(NoSuchElementException::new);
	}

	public String buscaTotalSalarios()
	{
		final BigDecimal sum = listaTodos().stream()
				.map(Funcionario::getSalario)
				.reduce(new BigDecimal(0), BigDecimal::add);

		return NumberFormat.getCurrencyInstance().format(sum);
	}

	@Transactional
	public void removePorId(final Long id)
	{
		funcionarioRepository.deleteById(id);
	}

	@Transactional
	public List<Funcionario> atualizaSalarios(final double percentualAumento)
	{
		final List<Funcionario> funcionarios = this.listaTodos();
		funcionarios.forEach(f -> atualizaSalario(f, percentualAumento));
		return funcionarios;
	}

	private void atualizaSalario(final Funcionario funcionario, final double percentualAumento)
	{
		final BigDecimal novoSalario = BigDecimal.valueOf(funcionario.getSalario().doubleValue() * (1 + (percentualAumento / 100)));
		funcionario.setSalario(novoSalario);
	}

	public Map<String, List<Funcionario>> agrupaPorFuncao()
	{
		return listaTodos()
				.stream()
				.collect(Collectors.groupingBy(Funcionario::getFuncao));
	}

	public void imprimeFuncionarios(final List<Funcionario> funcionarios)
	{
		funcionarios.forEach(f -> {
			final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			final String dataNascimento = f.getDataNascimento().format(formatter);

			final String salario = NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(f.getSalario());

			System.out.printf("Nome: %s - Data de Nascimento: %s - Idade: %s - Função: %s - Salário: %s%n",
					f.getNome(), dataNascimento, Period.between(f.getDataNascimento(), LocalDate.now()).getYears(), f.getFuncao(), salario);
		});
	}

	public void imprimeNomeIdadeFuncionarios(final List<Funcionario> funcionarios)
	{
		funcionarios.forEach(f -> {
			final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			final String dataNascimento = f.getDataNascimento().format(formatter);

			System.out.printf("Nome: %s - Idade: %s%n",
					f.getNome(), Period.between(f.getDataNascimento(), LocalDate.now()).getYears());
		});
	}

	public void imprimeTotalSalarioMinimo(final double salarioMinimo)
	{
		final List<Funcionario> funcionarios = listaTodos(Sort.by("nome"));

		funcionarios.forEach(f -> {
			final BigDecimal totalSalarioMinimo = BigDecimal.valueOf(f.getSalario().doubleValue() / salarioMinimo);
			System.out.printf("Nome: %s - %s salários mínimos%n", f.getNome(), totalSalarioMinimo.setScale(2, RoundingMode.CEILING));
		});
	}
}
