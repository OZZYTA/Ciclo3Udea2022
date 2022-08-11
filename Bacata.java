package com.ciclo2udea.bacata;

public class Bacata {
    private String ID;
    private String nombreUsuario;
    private String email;
    private double porcentajeMillas;

    public Bacata(String ID, String nombreUsuario, String email, double porcentajeMillas) {
        this.ID = ID;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.porcentajeMillas = porcentajeMillas;
    }
	
	//Modificar para probar git
    
    public double[] calcularMillas(double porMillas, double valor){
        double millasAcum=0;
        double bono=0;
        double[] respuesta = new double[2];
        if (porMillas==0.01){  //platinum
            millasAcum=valor*porMillas;
            bono=millasAcum*70;
        }
        else if (porMillas==0.02){  //black
            millasAcum=valor*porMillas;
            bono=millasAcum*90;
        }
        respuesta[0]=millasAcum;
        respuesta[1]=bono;
        return respuesta;
    }
    

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getPorcentajeMillas() {
        return porcentajeMillas;
    }

    public void setPorcentajeMillas(double porcentajeMillas) {
        this.porcentajeMillas = porcentajeMillas;
    }
    
    
    
    

    public static void main(String[] args) {  //Ejemplo
        Platinum TPL=new Platinum("001","Claudia Torres","Claudia@torres.com"); //Cree la tarjeta
        System.out.println(TPL.getCantidadUsos());  //Cantidad de usos con tarjeta nueva
        System.out.println(TPL.isDerechoVIP()); //Derecho al VIP con tarjeta nueva
        TPL.usar(2000000); //usar tarjeta
        TPL.usar(2000000); //usar tarjeta
        TPL.usar(2000000); //usar tarjeta
        TPL.usar(2000000); //usar tarjeta
        System.out.println("Use mi tarjeta de credito y las millas que gané "+TPL.usar(2000000)[0]); //usar tarjeta
        System.out.println(TPL.getCantidadUsos());  //Cantidad de usos ahora
        System.out.println(TPL.isDerechoVIP());
        
        System.out.println("");
        
        Black TBL= new Black("002","Lola López","lolita@lopez.com");
        System.out.println("Tengo derecho a mi beneficio 2?");
        System.out.println(TBL.getEstadoBeneficios()[1]);
        System.out.println("Redimi mi beneficio 2, tengo derecho a volverlo a usar?");
        TBL.redimirBeneficios(1); //Redimi el beneficio
        System.out.println(TBL.getEstadoBeneficios()[1]);        
        
    }
}
