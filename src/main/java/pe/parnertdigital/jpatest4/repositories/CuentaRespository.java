package pe.parnertdigital.jpatest4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.parnertdigital.jpatest4.models.Cuenta;

import java.util.List;
import java.util.Optional;

public interface CuentaRespository extends JpaRepository<Cuenta, Long> {

    //primera forma
    //Cuenta findByPersona(String persona);

    //segunda forma con query, el nombre puede ser cualquiera
//    @Query("select c from Cuenta c where c.persona=?1")
//    Cuenta findByPersona(String persona);

    //tercera forma mejorada de la segunda
    //mejorando la consulta con Optional para manejo de errores como nullPointerException
    //ojo es si deseas aplicas optional
    @Query("select c from Cuenta c where c.persona=?1")
    Optional<Cuenta>  findByPersona(String persona);

}
