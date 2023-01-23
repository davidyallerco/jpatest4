package pe.parnertdigital.jpatest4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.parnertdigital.jpatest4.models.Banco;

import java.util.List;

public interface BancoRepository extends JpaRepository<Banco, Long> {

}
