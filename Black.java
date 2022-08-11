package com.ciclo2udea.bacata;

public class Black extends Bacata{
    private String[] beneficios={"Maleta gratis","Bebida en Avion", "Sala VIP"};
    private boolean[] estadoBeneficios={true,true,true};

    public Black(String ID, String nombreUsuario, String email) {
        super(ID, nombreUsuario, email, 0.02);
    }
    
    public boolean redimirBeneficios(int posicion){
        if(estadoBeneficios[posicion]==true){
            estadoBeneficios[posicion]=false;
        return true;
        }
        return false;
    }

    public String[] getBeneficios() {
        return beneficios;
    }

    public void setBeneficios(String[] beneficios) {
        this.beneficios = beneficios;
    }

    public boolean[] getEstadoBeneficios() {
        return estadoBeneficios;
    }

    public void setEstadoBeneficios(boolean[] estadoBeneficios) {
        this.estadoBeneficios = estadoBeneficios;
    }
 
 
    
}
