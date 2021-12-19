package webserver;

import milestone3.Generator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BLE_Nordic {
    private int id;

    //DATOS QUE RECIBIMOS DEL EDGE
    private int temperatura;
    private int personas;
    private int humedad;
    private String texto_sensacion;
    private int sensacion;

    //MEDIAS QUE DEVOLVEREMOS A LA APP
    private double media_temp;
    private int aforo_maximo=0;
    private boolean aforo_superado;
    private double media_hum;

    private int acc_temp=0;
    private int acc_hum=0;
    private int aforo_actual=0;

    private int contador;

    public BLE_Nordic() {
        this.contador=1;
        this.id = Generator.getId();
    }

    public BLE_Nordic(int temperatura, int personas, int humedad) {
        this.temperatura=temperatura;
        this.personas=personas;
        this.humedad=humedad;
        this.contador=1;
        this.id = Generator.getId();
        this.texto_sensacion="";
    }

    public void asignacion(int temperatura, int personas, int humedad) {
        this.temperatura=temperatura;
        this.personas=personas;
        this.humedad=humedad;
        this.contador+=1;
        this.texto_sensacion="";
    }

    public void setHumedad(int humedad) {this.humedad = humedad;}

    public void setMedia_hum(double media_hum) {this.media_hum = media_hum;}
    public double getMedia_hum() {return media_hum;}

    public String getTexto_sensacion() {
        return texto_sensacion;
    }

    public void setPersonas(int personas) {this.personas = personas;}
    public void setAforo_actual(int aforo_actual) {this.aforo_actual = aforo_actual;}
    public double getAforo_actual() {return aforo_actual;}

    public int getTemperatura() {
        return temperatura;
    }

    public int getHumedad() {
        return humedad;
    }

    public void setTemperatura(int temperatura) {this.temperatura = temperatura;}
    public void setMedia_temp(double media_temp) {this.media_temp = media_temp;}
    public double getMedia_temp() {return media_temp;}

    public void setAforo_maximo(int aforo_maximo) {this.aforo_maximo = aforo_maximo;}
    public double getAforo_maximo() {return aforo_maximo;}
    public boolean isAforo_superado() {return aforo_superado;}

    public int getSensacion() {
        return sensacion;
    }

    public int getId() {return id;}


    public void calcular_medias(){
        this.acc_temp+=this.temperatura;
        this.acc_hum+=this.humedad;
        this.aforo_actual+=this.personas;
        if(this.aforo_actual<0)
            this.aforo_actual=0;

        this.setMedia_temp(this.acc_temp/this.contador);
        this.setMedia_hum(this.acc_hum/this.contador);
        mirar_aforo();
    }

    public void mirar_aforo(){
        this.aforo_superado=this.aforo_maximo<this.aforo_actual;
    }

    public void predecir_modelo() throws IOException {
        String temp= Integer.toString(this.temperatura);
        String pers= Integer.toString(this.personas);
        System.out.println(temp + " " + pers);
        ProcessBuilder builder = new ProcessBuilder("python", "Model.py", temp , pers);
        builder.redirectErrorStream(true);
        Process p = builder.start();
        //read output
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        StringBuilder sb = new StringBuilder();
        String ultimo="";
        while ((line = br.readLine()) != null) {
            sb.append(line);
            ultimo=line;

            //System.out.println("Texto= " + line);
        }
        System.out.println(ultimo.charAt(1));
        this.sensacion=Character.getNumericValue(ultimo.charAt(1));
        if (this.sensacion==1){
            this.texto_sensacion="Calor";
        }else{
            this.texto_sensacion="Frio";
        }


    }




}