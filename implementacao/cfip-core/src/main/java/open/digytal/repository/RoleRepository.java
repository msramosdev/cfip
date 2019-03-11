package open.digytal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import open.digytal.model.entity.EntidadeRole;

public interface RoleRepository  extends JpaRepository<EntidadeRole, String> {
	EntidadeRole findByNome(String nome);
}
