package pe.parnertdigital.jpatest4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.parnertdigital.jpatest4.models.Cuenta;

import java.util.List;

public interface CuentaRespository extends JpaRepository<Cuenta, Long> {

}
