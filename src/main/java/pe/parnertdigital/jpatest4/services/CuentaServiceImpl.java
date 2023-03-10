package pe.parnertdigital.jpatest4.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.parnertdigital.jpatest4.models.Banco;
import pe.parnertdigital.jpatest4.models.Cuenta;
import pe.parnertdigital.jpatest4.repositories.BancoRepository;
import pe.parnertdigital.jpatest4.repositories.CuentaRespository;

import java.math.BigDecimal;

@Service
public class CuentaServiceImpl implements CuentaService{



    private CuentaRespository cuentaRespository;
    private BancoRepository bancoRepository;

    @Autowired
    public CuentaServiceImpl(CuentaRespository cuentaRespository, BancoRepository bancoRepository) {
        this.cuentaRespository = cuentaRespository;
        this.bancoRepository = bancoRepository;
    }

    @Override
    public Cuenta buscarPorId(Long id) {
        return cuentaRespository.findById(id).orElseThrow(null);
    }

    @Override
    public int revisarTotalTransferencias(Long bancoId) {
        Banco banco = bancoRepository.findById(bancoId).orElseThrow(null);
        return banco.getTotalTransferencias();
    }

    @Override
    public BigDecimal revisarSaldo(Long cuentaId) {
        Cuenta cuenta = cuentaRespository.findById(cuentaId).orElseThrow(null);
        return cuenta.getSaldo();
    }



    @Override
    public void tranferir(Long numCuentaOrigen, Long numCuentaDestino, BigDecimal monto, Long bancoId) {

        Cuenta cuentaOrigen = cuentaRespository.findById(numCuentaOrigen).orElseThrow(null);
        cuentaOrigen.debito(monto);
        cuentaRespository.save(cuentaOrigen);

        Cuenta cuentaDestino = cuentaRespository.findById(numCuentaDestino).orElseThrow(null);
        cuentaDestino.credito(monto);
        cuentaRespository.save(cuentaDestino);


        //primero actualizar el total de transferencia
        Banco banco = bancoRepository.findById(bancoId).orElseThrow(null);
        int totalTransferencias = banco.getTotalTransferencias();
        banco.setTotalTransferencias(++totalTransferencias);
        bancoRepository.save(banco);

    }

}
