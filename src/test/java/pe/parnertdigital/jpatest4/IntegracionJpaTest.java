package pe.parnertdigital.jpatest4;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pe.parnertdigital.jpatest4.models.Cuenta;
import pe.parnertdigital.jpatest4.repositories.CuentaRespository;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class IntegracionJpaTest {
    @Autowired
    CuentaRespository cuentaRespository;

    @Test
    void testFindById(){
        Optional<Cuenta> cuenta = cuentaRespository.findById(1L);
        assertTrue(cuenta.isPresent());
        assertEquals("David", cuenta.orElseThrow(null).getPersona());
    }
}
