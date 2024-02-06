package br.com.fabio.prothera.repository;

import br.com.fabio.prothera.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by fabiofaraco on 05/02/2024.
 */

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>
{
	Funcionario findByNome(final String nome);

	@Query(value = "SELECT * FROM funcionario WHERE EXTRACT (month FROM data_nascimento) IN (:meses) ORDER BY data_nascimento", nativeQuery = true)
	List<Funcionario> listByMonths(@Param("meses") final List<Integer> meses);
}


