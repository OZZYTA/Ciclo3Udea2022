package com.UdeA.Ciclo3.service;

import com.UdeA.Ciclo3.modelos.Empleado;
import com.UdeA.Ciclo3.modelos.MovimientoDinero;
import com.UdeA.Ciclo3.repo.MovimientosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovimientosService {
    @Autowired
    MovimientosRepository movimientosRepository;

    public List<MovimientoDinero> getAllMovimientos(){ //Metodo que me muestra todos los movimientos sin ningn filtro
        List<MovimientoDinero> movimientosList = new ArrayList<>();
        movimientosRepository.findAll().forEach(movimiento -> movimientosList.add(movimiento));  //Recorremos el iterable que regresa el metodo findAll del Jpa y lo guardamos en la lista creada
        return movimientosList;
    }

    public MovimientoDinero getMovimientoById(Integer id){ //Ver movimientos por id
        return movimientosRepository.findById(id).get();
    }

    public boolean saveOrUpdateMovimiento(MovimientoDinero movimientoDinero){ //Guardar o actualizar elementos
        MovimientoDinero mov=movimientosRepository.save(movimientoDinero);
        if (movimientosRepository.findById(mov.getId())!=null){
            return true;
        }
        return false;
    }

    public boolean deleteMovimiento(Integer id){ //Eliminar movimiento por id
        movimientosRepository.deleteById(id); //Eliminar usando el metodo que nos ofrece el repositorio
        if(this.movimientosRepository.findById(id).isPresent()){ //Si al buscar el movimiento lo encontramos, no se eliminó (false)
            return false;
        }
        return true; //Si al buscar el movimiento no lo encontramos, si lo eliminò (true)
    }

    public ArrayList<MovimientoDinero> obtenerPorEmpleado(Integer id) { //Obterner teniendo en cuenta el id del empleado
        return movimientosRepository.findByEmpleado(id);
    }

    public ArrayList<MovimientoDinero> obtenerPorEmpresa(Integer id) { //Obtener movimientos teniendo en cuenta el id de la empresa a la que pertencen los empleados que la registraron
        return movimientosRepository.findByEmpresa(id);
    }

    //Servicio para ver la suma de todos los montos
    public Long obtenerSumaMontos(){
        return movimientosRepository.SumarMonto();
    }

    //Servicio para ver la suma de los montos por empleado
    public Long MontosPorEmpleado(Integer id){
        return movimientosRepository.MontosPorEmpleado(id);
    }

    //Servicio para ver la suma de los montos por empresa
    public Long MontosPorEmpresa(Integer id){
        return movimientosRepository.MontosPorEmpresa(id);
    }

    //servicio que nos deja conseguir el id de un empleado si tenemos su correo
    public Integer IdPorCorreo(String Correo){
        return movimientosRepository.IdPorCorreo(Correo);
    }
}
