package com.UdeA.Ciclo3.modelos;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Movimientos")
public class MovimientoDinero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private long monto;
    private String concepto;
    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado usuario;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date fecha;

    public MovimientoDinero() {
    }

    public MovimientoDinero(long monto, String concepto, Empleado empleado, Date fecha) {
        this.monto = monto;
        this.concepto = concepto;
        this.usuario = empleado;
        this.fecha=fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getMonto() {
        return monto;
    }

    public void setMonto(long monto) {
        this.monto = monto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Empleado getUsuario() {
        return usuario;
    }

    public void setUsuario(Empleado empleado) {
        this.usuario = empleado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
