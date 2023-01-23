package pe.parnertdigital.jpatest4;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pe.parnertdigital.jpatest4.models.Cuenta;
import pe.parnertdigital.jpatest4.repositories.CuentaRespository;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
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

    @Test
    void testFindByPersona(){
        Optional<Cuenta> cuenta = cuentaRespository.findByPersona("David");
        assertTrue(cuenta.isPresent());
        assertEquals("David", cuenta.orElseThrow(null).getPersona());
        assertEquals("1000.00", cuenta.orElseThrow(null).getSaldo().toPlainString());
    }

    @Test
    void testFindByPersonaThrowException(){
        Optional<Cuenta> cuenta = cuentaRespository.findByPersona("Davidxxxxxxx");
        //primera forma
        /*
        assertThrows(NoSuchElementException.class, ()->{
            cuenta.orElseThrow(null);
        });*/
       // assertThrows(NoSuchElementException.class,  cuenta::orElseThrow); //segunda forma mas reducida
        assertFalse(cuenta.isPresent());
    }

    @Test
    void testFindAll() {
        List<Cuenta> cuentas = cuentaRespository.findAll();
        assertFalse(cuentas.isEmpty());
        assertEquals(2,cuentas.size());

    }

    @Test
    void testSave() {
        //Given
        Cuenta cuentaPepe = new Cuenta(null, "Pepe", new BigDecimal("3000"));
        cuentaRespository.save(cuentaPepe);

        //When
        Cuenta cuenta = cuentaRespository.findByPersona("Pepe").orElseThrow(null);

        //Then
        assertEquals("Pepe", cuenta.getPersona());
        assertEquals("3000", cuenta.getSaldo().toPlainString());
        //assertEquals(3, cuenta.getId());//el id puede variar y puede salir resultos diferentes, no se recomienda este metodo
    }
}
