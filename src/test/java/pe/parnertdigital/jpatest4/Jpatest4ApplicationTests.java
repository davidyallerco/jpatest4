package pe.parnertdigital.jpatest4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pe.parnertdigital.jpatest4.exceptions.DineroInsuficienteException;
import pe.parnertdigital.jpatest4.models.Banco;
import pe.parnertdigital.jpatest4.models.Cuenta;
import pe.parnertdigital.jpatest4.repositories.BancoRepository;
import pe.parnertdigital.jpatest4.repositories.CuentaRespository;
import pe.parnertdigital.jpatest4.services.CuentaService;

import java.math.BigDecimal;

import static  org.mockito.Mockito.*;
import static pe.parnertdigital.jpatest4.Datos.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class Jpatest4ApplicationTests {

	@MockBean
	CuentaRespository cuentaRespository;
	@MockBean
	BancoRepository bancoRepository;
	@Autowired
	CuentaService service;

	@BeforeEach
	void setUp() {

	}

	@Test
	void contextLoads() {
		when(cuentaRespository.findById(1L)).thenReturn(crearCuenta001());
		when(cuentaRespository.findById(2L)).thenReturn(crearCuenta002());
		when(bancoRepository.findById(1L)).thenReturn(crearBanco());

		BigDecimal saldoOrigen = service.revisarSaldo(1L);
		BigDecimal saldoDestino = service.revisarSaldo(2L);
		assertEquals("1000", saldoOrigen.toPlainString());
		assertEquals("2000", saldoDestino.toPlainString());

		service.tranferir(1L,2L, new BigDecimal("100"), 1L);

		saldoOrigen = service.revisarSaldo(1L);
		saldoDestino = service.revisarSaldo(2L);

		assertEquals("900", saldoOrigen.toPlainString());
		assertEquals("2100", saldoDestino.toPlainString());

		int total = service.revisarTotalTransferencias(1L);
		assertEquals(1, total);

		verify(cuentaRespository, times(3)).findById(1L);
		verify(cuentaRespository, times(3)).findById(2L);
		verify(cuentaRespository, times(2)).save(any(Cuenta.class));

		verify(bancoRepository,times(2)).findById(1L);
		verify(bancoRepository).save(any(Banco.class));

		verify(cuentaRespository, times(6)).findById(anyLong());
		verify(cuentaRespository, never()).findAll();
	}

	@Test
	void contextLoads2() {
		when(cuentaRespository.findById(1L)).thenReturn(crearCuenta001());
		when(cuentaRespository.findById(2L)).thenReturn(crearCuenta002());
		when(bancoRepository.findById(1L)).thenReturn(crearBanco());

		BigDecimal saldoOrigen = service.revisarSaldo(1L);
		BigDecimal saldoDestino = service.revisarSaldo(2L);

		assertEquals("1000", saldoOrigen.toPlainString());
		assertEquals("2000", saldoDestino.toPlainString());


		assertThrows(DineroInsuficienteException.class,()->{
			service.tranferir(1L,2L, new BigDecimal("1200"), 1L);

		});

		saldoOrigen = service.revisarSaldo(1L);
		saldoDestino = service.revisarSaldo(2L);

		assertEquals("1000", saldoOrigen.toPlainString());
		assertEquals("2000", saldoDestino.toPlainString());

		int total = service.revisarTotalTransferencias(1L);
		assertEquals(0, total);

		verify(cuentaRespository, times(3)).findById(1L);
		verify(cuentaRespository, times(2)).findById(2L);
		verify(cuentaRespository, never()).save(any(Cuenta.class));

		verify(bancoRepository,times(1)).findById(1L);
		verify(bancoRepository, never()).save(any(Banco.class));

		verify(cuentaRespository, times(5)).findById(anyLong());
		verify(cuentaRespository, never()).findAll();


	}


	@Test
	void contextLoads3(){
		when(cuentaRespository.findById(1L)).thenReturn(crearCuenta001());

		Cuenta cuenta1 = service.buscarPorId(1L);
		Cuenta cuenta2 = service.buscarPorId(1L);

		assertSame(cuenta1, cuenta2);
		assertTrue(cuenta1 == cuenta2);
		assertEquals("David", cuenta1.getPersona());
		assertEquals("David", cuenta2.getPersona());
		verify(cuentaRespository, times(2)).findById(1L);



	}


}
