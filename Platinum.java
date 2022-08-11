package com.ciclo2udea.bacata;

public class Platinum extends Bacata{
    private int cantidadUsos=0;
    private boolean derechoVIP=false;

    public Platinum(String ID, String nombreUsuario, String email) {
        super(ID, nombreUsuario, email, 0.01);
    }
    
    public double[] usar(double valor){
        cantidadUsos=cantidadUsos+1; //cantidadUsos++;
        if (cantidadUsos==5){
            derechoVIP=true;
        }
        double porMillas=this.getPorcentajeMillas();
    
    return super.calcularMillas(porMillas,valor);
    }

    public int getCantidadUsos() {
        return cantidadUsos;
    }

    public void setCantidadUsos(int cantidadUsos) {
        this.cantidadUsos = cantidadUsos;
    }

    public boolean isDerechoVIP() {
        return derechoVIP;
    }

    public void setDerechoVIP(boolean derechoVIP) {
        this.derechoVIP = derechoVIP;
    }
    
    
}
