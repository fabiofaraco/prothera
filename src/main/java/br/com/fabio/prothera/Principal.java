package br.com.fabio.prothera;

import br.com.fabio.prothera.model.Funcionario;
import br.com.fabio.prothera.service.FuncionarioService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.util.*;

/**
 * Created by fabiofaraco on 05/02/2024.
 */

@Component
public class Principal
{
	@Autowired
	private FuncionarioService funcionarioService;
	@PostConstruct
	public void init() {
		NumberFormat.getInstance(new Locale("pt-br"));

		System.out.println("-------------------------------------");

		System.out.println("3.1 – Inserir todos os funcionários, na mesma ordem e informações da tabela acima");
		System.out.println("Funcionários inseridos via migrations\n");
		List<Funcionario> funcionarios = funcionarioService.listaTodos();
		funcionarioService.imprimeFuncionarios(funcionarios);

		System.out.println("\nItem 3.2 - Remover o funcionário “João” da lista\n");
		final Funcionario joao = funcionarioService.buscaPorNome("João");
		funcionarioService.removePorId(joao.getId());
		funcionarios = funcionarioService.listaTodos();
		funcionarioService.imprimeFuncionarios(funcionarios);

		System.out.println("\nItem 3.3 - Imprimir todos os funcionários com todas suas informações, sendo que:\n" +
				"• informação de data deve ser exibido no formato dd/mm/aaaa;\n" +
				"• informação de valor numérico deve ser exibida no formatado com separador de milhar como ponto e decimal como vírgula.\n");
		funcionarioService.imprimeFuncionarios(funcionarios);

		System.out.println("\nItem 3.4 – Os funcionários receberam 10% de aumento de salário, atualizar a lista de funcionários com novo valor\n");
		funcionarios = funcionarioService.atualizaSalarios(10);
		funcionarioService.imprimeFuncionarios(funcionarios);

		System.out.println("\nItem 3.5 - Agrupar os funcionários por função em um MAP, sendo a chave a “função” e o valor a “lista de funcionários”");
		System.out.println("Item 3.6 -Imprimir os funcionários, agrupados por função.");

		final Map<String, List<Funcionario>> funcionariosAgrupados = funcionarioService.agrupaPorFuncao();

		funcionariosAgrupados.forEach((k, values) -> {
			System.out.printf("%n%s%n", k.toUpperCase());
			funcionarioService.imprimeFuncionarios(values);
		});

		System.out.println("\nItem 3.8 - Imprimir os funcionários que fazem aniversário no mês 10 e 12. \n");
		funcionarioService.imprimeFuncionarios(funcionarioService.listaPorMesNascimento(List.of(10, 12)));

		System.out.println("\nItem 3.9 - Imprimir o funcionário com a maior idade, exibir os atributos: nome e idade\n");
		funcionarioService.imprimeNomeIdadeFuncionarios(List.of(funcionarioService.buscaPorMaiorIdade()));

		System.out.println("\nItem 3.10 - Imprimir a lista de funcionários por ordem alfabética.\n");
		funcionarios = funcionarioService.listaTodos(Sort.by("nome"));
		funcionarioService.imprimeFuncionarios(funcionarios);

		System.out.println("\nItem 3.11 - Imprimir o total dos salários dos funcionários.\n");
		System.out.println("Total salário: " + funcionarioService.buscaTotalSalarios());

		System.out.println("\nItem 3.12 - Imprimir quantos salários mínimos ganha cada funcionário, considerando que o salário mínimo é R$1212.00.\n");
		funcionarioService.imprimeTotalSalarioMinimo(1212);

		System.out.println("-------------------------------------");


	}
}
