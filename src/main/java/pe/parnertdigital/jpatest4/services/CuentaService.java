package pe.parnertdigital.jpatest4.services;

import pe.parnertdigital.jpatest4.models.Cuenta;

import java.math.BigDecimal;

public interface CuentaService {


    Cuenta buscarPorId(Long id);
    int revisarTotalTransferencias(Long bancoId);
    BigDecimal revisarSaldo(Long cuentaId);
    void tranferir(Long numCuentaOrigen, Long numCuentaDestino, BigDecimal monto, Long bancoId);

}
