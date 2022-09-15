package com.UdeA.Ciclo3.repo;

import com.UdeA.Ciclo3.modelos.MovimientoDinero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface MovimientosRepository extends JpaRepository<MovimientoDinero, Integer> {
    //Metodo para filtrar movimientos por empleado
    @Query(value ="select * from movimientos where empleado_id= ?1", nativeQuery = true)
    public abstract ArrayList<MovimientoDinero> findByEmpleado(Integer id);

    //Metodo para filtrar movimientos por empresa
    @Query(value="select * from movimientos where empleado_id in (select id from empleado where empresa_id= ?1)", nativeQuery = true)
    public abstract ArrayList<MovimientoDinero> findByEmpresa(Integer id);

    //Metodo para ver la suma de TODOS LOS MOVIMIENTOS
    @Query(value="SELECT SUM(monto) from movimientos", nativeQuery = true)
    public abstract Long SumarMonto();

    //Metodo para ver la suma de los montos por empleado
    @Query(value="SELECT SUM(monto) from movimientos where empleado_id=?1", nativeQuery = true)
    public abstract Long MontosPorEmpleado(Integer id); //id del empleado

    //Metodo para ver la suma de los movimientos por empresa
    @Query(value="select sum(monto) from movimientos where empleado_id in (select id from empleado where empresa_id= ?1)", nativeQuery = true)
    public abstract Long MontosPorEmpresa(Integer id); //Id de la empresa

    //Metodo que me trae el id de un usuario cuando tengo su correo
    @Query(value="select id from empleado where correo=?1", nativeQuery = true)
    public abstract Integer IdPorCorreo(String correo);
}
