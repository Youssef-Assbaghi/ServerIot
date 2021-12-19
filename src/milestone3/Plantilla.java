package milestone3;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import webserver.BLE_Nordic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@SuppressWarnings("deprecation")
public class Plantilla {

    MongoClient c=null;
    private static  MongoCollection<Document> mini;
    static MongoDatabase db=null;

    public void start() throws Exception {
        MongoClient c =  new MongoClient("localhost",27017);
        db =  c.getDatabase("IOT");
        mini = db.getCollection("DATOS");
    }

    public void stop() throws Exception {
        c.close();
    }

    public List<Integer> historicos_minimos(int id){
        List<Integer> resultados = new ArrayList<Integer>();
        resultados.add(minimas_historicas_temperatura(id));
        resultados.add(minimas_historicas_aforo(id));
        resultados.add(minimas_historicas_humedad(id));
        return resultados;
    }
    public List<Integer> historicos_maximos(int id){
        List<Integer> resultados = new ArrayList<Integer>();
        resultados.add(maximas_historicas_temperatura(id));
        resultados.add(maximas_historicas_aforo(id));
        resultados.add(maximas_historicas_humedad(id));
        return resultados;
    }
    public List<Double> historicos_medios(int id){
        List<Double> resultados = new ArrayList<Double>();
        resultados.add(medias_historicas_temperatura(id));
        resultados.add(medias_historicas_aforo(id));
        resultados.add(medias_historicas_humedad(id));
        return resultados;
    }
    public Integer minimas_historicas_temperatura(int id){
        int valor_minimo=50;
        Document next;
        List<Integer> values = new ArrayList<Integer>();
        BasicDBObject filter = new BasicDBObject();
        filter.put("id", id );

        MongoCursor<Integer> c = db.getCollection("DATOS").distinct("temperatura", filter, Integer.class).iterator();

        int i=0;
        while(c.hasNext())
        {
            values.add(c.next());
            i++;
        }

        i=0;
        while(i<values.size()){
            if(valor_minimo>values.get(i))
                valor_minimo=values.get(i);
            i++;
        }
        return valor_minimo;
    }
    public Integer minimas_historicas_aforo(int id){
        int valor_minimo=1000;
        Document next;
        List<Integer> values = new ArrayList<Integer>();
        BasicDBObject filter = new BasicDBObject();
        filter.put("id", id );

        MongoCursor<Integer> c = db.getCollection("DATOS").distinct("aforo", filter, Integer.class).iterator();

        int i=0;
        while(c.hasNext())
        {
            values.add(c.next());
            i++;
        }

        i=0;
        while(i<values.size()){
            if(valor_minimo>values.get(i))
                valor_minimo=values.get(i);
            i++;
        }
        return valor_minimo;
    }
    public Integer minimas_historicas_humedad(int id){
        int valor_minimo=99;
        Document next;
        List<Integer> values = new ArrayList<Integer>();
        BasicDBObject filter = new BasicDBObject();
        filter.put("id", id );

        MongoCursor<Integer> c = db.getCollection("DATOS").distinct("humedad", filter, Integer.class).iterator();

        int i=0;
        while(c.hasNext())
        {
            values.add(c.next());
            i++;
        }

        i=0;
        while(i<values.size()){
            if(valor_minimo>values.get(i))
                valor_minimo=values.get(i);
            i++;
        }
        return valor_minimo;
    }
    public Integer maximas_historicas_temperatura(int id){
        int valor_maximo=-20;
        Document next;
        List<Integer> values = new ArrayList<Integer>();
        BasicDBObject filter = new BasicDBObject();
        filter.put("id", id );

        MongoCursor<Integer> c = db.getCollection("DATOS").distinct("temperatura", filter, Integer.class).iterator();

        int i=0;
        while(c.hasNext())
        {
            values.add(c.next());
            i++;
        }

        i=0;
        while(i<values.size()){
            if(valor_maximo<values.get(i))
                valor_maximo=values.get(i);
            i++;
        }
        return valor_maximo;
    }
    public Integer maximas_historicas_aforo(int id){
        int valor_maximo=0;
        Document next;
        List<Integer> values = new ArrayList<Integer>();
        BasicDBObject filter = new BasicDBObject();
        filter.put("id", id );

        MongoCursor<Integer> c = db.getCollection("DATOS").distinct("aforo", filter, Integer.class).iterator();

        int i=0;
        while(c.hasNext())
        {
            values.add(c.next());
            i++;
        }

        i=0;
        while(i<values.size()){
            if(valor_maximo<values.get(i))
                valor_maximo=values.get(i);
            i++;
        }
        return valor_maximo;
    }
    public Integer maximas_historicas_humedad(int id){
        int valor_maximo=0;
        Document next;
        List<Integer> values = new ArrayList<Integer>();
        BasicDBObject filter = new BasicDBObject();
        filter.put("id", id );

        MongoCursor<Integer> c = db.getCollection("DATOS").distinct("humedad", filter, Integer.class).iterator();

        int i=0;
        while(c.hasNext())
        {
            values.add(c.next());
            i++;
        }

        i=0;
        while(i<values.size()){
            if(valor_maximo<values.get(i))
                valor_maximo=values.get(i);
            i++;
        }
        return valor_maximo;
    }
    public Double medias_historicas_temperatura(int id){
        Integer acc=0;
        Document next;
        List<Integer> values = new ArrayList<Integer>();
        BasicDBObject filter = new BasicDBObject();
        filter.put("id", id );

        MongoCursor<Integer> c = db.getCollection("DATOS").distinct("temperatura", filter, Integer.class).iterator();

        int i=0;
        while(c.hasNext())
        {
            values.add(c.next());
            i++;
        }

        i=0;
        while(i<values.size()){
            acc+= values.get(i);
            i++;
        }
        Double media= Double.valueOf(acc/values.size());
        return media;
    }
    public Double medias_historicas_aforo(int id){
        Integer acc=0;
        Document next;
        List<Integer> values = new ArrayList<Integer>();
        BasicDBObject filter = new BasicDBObject();
        filter.put("id", id );

        MongoCursor<Integer> c = db.getCollection("DATOS").distinct("aforo", filter, Integer.class).iterator();

        int i=0;
        while(c.hasNext())
        {
            values.add(c.next());
            i++;
        }

        i=0;
        while(i<values.size()){
            acc+= values.get(i);
            i++;
        }
        Double media= Double.valueOf(acc/values.size());
        return media;
    }
    public Double medias_historicas_humedad(int id){
        Integer acc=0;
        Document next;
        List<Integer> values = new ArrayList<Integer>();
        BasicDBObject filter = new BasicDBObject();
        filter.put("id", id );

        MongoCursor<Integer> c = db.getCollection("DATOS").distinct("humedad", filter, Integer.class).iterator();

        int i=0;
        while(c.hasNext())
        {
            values.add(c.next());
            i++;
        }

        i=0;
        while(i<values.size()){
            acc+= values.get(i);
            i++;
        }
        Double media= Double.valueOf(acc/values.size());
        return media;
    }

    public List<Integer> dia_minimos(int id, int dia, int mes, int ano){
        List<Integer> resultados = new ArrayList<Integer>();
        resultados.add(minimas_dia_temperatura(id,dia,mes,ano));
        resultados.add(minimas_dia_aforo(id,dia,mes,ano));
        resultados.add(minimas_dia_humedad(id,dia,mes,ano));
        return resultados;
    }
    public List<Integer> dia_maximos(int id, int dia, int mes, int ano){
        List<Integer> resultados = new ArrayList<Integer>();
        resultados.add(maximas_dia_temperatura(id,dia,mes,ano));
        resultados.add(maximas_dia_aforo(id,dia,mes,ano));
        resultados.add(maximas_dia_humedad(id,dia,mes,ano));
        return resultados;
    }
    public List<Double> dia_medios(int id, int dia, int mes, int ano){
        List<Double> resultados = new ArrayList<Double>();
        resultados.add(medias_dia_temperatura(id,dia,mes,ano));
        resultados.add(medias_dia_aforo(id,dia,mes,ano));
        resultados.add(medias_dia_humedad(id,dia,mes,ano));
        return resultados;
    }
    public Integer minimas_dia_temperatura(int id, int dia, int mes, int ano){
        int valor_minimo=50;
        Document next;
        List<Integer> values = new ArrayList<Integer>();
        BasicDBObject filter = new BasicDBObject();
        filter.put("id", id );
        filter.put("dia", dia);
        filter.put("mes", mes);
        filter.put("ano", ano);
        MongoCursor<Integer> c = db.getCollection("DATOS").distinct("temperatura", filter, Integer.class).iterator();
        int i=0;
        while(c.hasNext())
        {
            values.add(c.next());
            i++;
        }
        if (!comprovar0(i)){
            return 0;
        }
        i=0;
        while(i<values.size()){
            if(valor_minimo>values.get(i))
                valor_minimo=values.get(i);
            i++;
        }
        return valor_minimo;
    }
    public Integer minimas_dia_aforo(int id, int dia, int mes, int ano){
        int valor_minimo=1000;
        Document next;
        List<Integer> values = new ArrayList<Integer>();
        BasicDBObject filter = new BasicDBObject();
        filter.put("id", id );
        filter.put("dia", dia);
        filter.put("mes", mes);
        filter.put("ano", ano);
        MongoCursor<Integer> c = db.getCollection("DATOS").distinct("aforo", filter, Integer.class).iterator();
        int i=0;
        while(c.hasNext())
        {
            values.add(c.next());
            i++;
        }
        if (!comprovar0(i)){
            return 0;
        }
        i=0;
        while(i<values.size()){
            if(valor_minimo>values.get(i))
                valor_minimo=values.get(i);
            i++;
        }
        return valor_minimo;
    }
    public Integer minimas_dia_humedad(int id, int dia, int mes, int ano){
        int valor_minimo=99;
        Document next;
        List<Integer> values = new ArrayList<Integer>();
        BasicDBObject filter = new BasicDBObject();
        filter.put("id", id );
        filter.put("dia", dia);
        filter.put("mes", mes);
        filter.put("ano", ano);
        MongoCursor<Integer> c = db.getCollection("DATOS").distinct("humedad", filter, Integer.class).iterator();
        int i=0;
        while(c.hasNext())
        {
            values.add(c.next());
            i++;
        }
        if (!comprovar0(i)){
            return 0;
        }
        i=0;
        while(i<values.size()){
            if(valor_minimo>values.get(i))
                valor_minimo=values.get(i);
            i++;
        }
        return valor_minimo;
    }
    public Integer maximas_dia_temperatura(int id, int dia, int mes, int ano){
        int valor_maximo=-20;
        Document next;
        List<Integer> values = new ArrayList<Integer>();
        BasicDBObject filter = new BasicDBObject();
        filter.put("id", id );
        filter.put("dia", dia);
        filter.put("mes", mes);
        filter.put("ano", ano);
        MongoCursor<Integer> c = db.getCollection("DATOS").distinct("temperatura", filter, Integer.class).iterator();

        int i=0;
        while(c.hasNext())
        {
            values.add(c.next());
            i++;
        }
        if (!comprovar0(i)){
            return 0;
        }
        i=0;
        while(i<values.size()){
            if(valor_maximo<values.get(i))
                valor_maximo=values.get(i);
            i++;
        }
        return valor_maximo;
    }
    public Integer maximas_dia_aforo(int id, int dia, int mes, int ano){
        int valor_maximo=0;
        Document next;
        List<Integer> values = new ArrayList<Integer>();
        BasicDBObject filter = new BasicDBObject();
        filter.put("id", id );
        filter.put("dia", dia);
        filter.put("mes", mes);
        filter.put("ano", ano);
        MongoCursor<Integer> c = db.getCollection("DATOS").distinct("aforo", filter, Integer.class).iterator();

        int i=0;
        while(c.hasNext())
        {
            values.add(c.next());
            i++;
        }
        if (!comprovar0(i)){
            return 0;
        }
        i=0;
        while(i<values.size()){
            if(valor_maximo<values.get(i))
                valor_maximo=values.get(i);
            i++;
        }
        return valor_maximo;
    }
    public Integer maximas_dia_humedad(int id, int dia, int mes, int ano){
        int valor_maximo=0;
        Document next;
        List<Integer> values = new ArrayList<Integer>();
        BasicDBObject filter = new BasicDBObject();
        filter.put("id", id );
        filter.put("dia", dia);
        filter.put("mes", mes);
        filter.put("ano", ano);
        MongoCursor<Integer> c = db.getCollection("DATOS").distinct("humedad", filter, Integer.class).iterator();

        int i=0;
        while(c.hasNext())
        {
            values.add(c.next());
            i++;
        }
        if (!comprovar0(i)){
            return 0;
        }
        i=0;
        while(i<values.size()){
            if(valor_maximo<values.get(i))
                valor_maximo=values.get(i);
            i++;
        }
        return valor_maximo;
    }
    public Double medias_dia_temperatura(int id, int dia, int mes, int ano){
        Integer acc=0;
        Document next;
        List<Integer> values = new ArrayList<Integer>();
        BasicDBObject filter = new BasicDBObject();
        filter.put("id", id );
        filter.put("dia", dia);
        filter.put("mes", mes);
        filter.put("ano", ano);
        MongoCursor<Integer> c = db.getCollection("DATOS").distinct("temperatura", filter, Integer.class).iterator();

        int i=0;
        while(c.hasNext())
        {
            values.add(c.next());
            i++;
        }
        if (!comprovar0(i)){
            return 0.0;
        }
        i=0;
        while(i<values.size()){
            acc+= values.get(i);
            i++;
        }
        Double media= Double.valueOf(acc/values.size());
        return media;
    }
    public Double medias_dia_aforo(int id, int dia, int mes, int ano){
        Integer acc=0;
        Document next;
        List<Integer> values = new ArrayList<Integer>();
        BasicDBObject filter = new BasicDBObject();
        filter.put("id", id );
        filter.put("dia", dia);
        filter.put("mes", mes);
        filter.put("ano", ano);
        MongoCursor<Integer> c = db.getCollection("DATOS").distinct("aforo", filter, Integer.class).iterator();

        int i=0;
        while(c.hasNext())
        {
            values.add(c.next());
            i++;
        }
        if (!comprovar0(i)){
            return 0.0;
        }
        i=0;
        while(i<values.size()){
            acc+= values.get(i);
            i++;
        }
        Double media= Double.valueOf(acc/values.size());
        return media;
    }
    public Double medias_dia_humedad(int id, int dia, int mes, int ano){
        Integer acc=0;
        Document next;
        List<Integer> values = new ArrayList<Integer>();
        BasicDBObject filter = new BasicDBObject();
        filter.put("id", id );
        filter.put("dia", dia);
        filter.put("mes", mes);
        filter.put("ano", ano);
        MongoCursor<Integer> c = db.getCollection("DATOS").distinct("humedad", filter, Integer.class).iterator();

        int i=0;
        while(c.hasNext())
        {
            values.add(c.next());
            i++;
        }
        if (!comprovar0(i)){
            return 0.0;
        }
        i=0;
        while(i<values.size()){
            acc+= values.get(i);
            i++;
        }
        Double media= Double.valueOf(acc/values.size());
        return media;
    }
    public boolean comprovar0 (int i){
        return i!=0;
    }


    public void insertTrampa(){
        int id=3;
        int dia=12;
        int mes=12;
        int ano=2021;
        int aforo=0;
        for(int i=0;i<5;i++){
            Document doc = new Document();
            doc.append("id", id);
            doc.append("dia", dia);
            doc.append("mes", mes);
            doc.append("ano", ano);
            doc.append("temperatura", (int)(Math.random() * (50 - (-20) + 1) + (-20) ));
            aforo+=(int)(Math.random() * (18 - (-9) + 1) + (-9));
            if(aforo<0)
                aforo=0;
            doc.append("aforo", aforo);
            doc.append("humedad", (int)(Math.random() * (99 - 1 + 1) + 1 ));
            mini.insertOne(doc);
        }
    }

    public void insertDatos(){
        int id=1;
        int dia=1;
        int mes=1;
        int ano=2020;

        //INSERTS DE TODO 2020
        for (id=0;id<10;id++){
            for(ano=2020;ano<2021;ano++){
                for (mes=1;mes<13;mes++){
                    for(dia=1;dia<32;dia++){
                        int aforo=0;
                        for(int i=0;i<25;i++){
                            Document doc = new Document();
                            doc.append("id", id);
                            doc.append("dia", dia);
                            doc.append("mes", mes);
                            doc.append("ano", ano);
                            doc.append("temperatura", (int)(Math.random() * (50 - (-20) + 1) + (-20) ));
                            aforo+=(int)(Math.random() * (18 - (-9) + 1) + (-9));
                            if(aforo<0)
                                aforo=0;
                            doc.append("aforo", aforo);
                            doc.append("humedad", (int)(Math.random() * (99 - 1 + 1) + 1 ));
                            mini.insertOne(doc);
                        }
                    }
                }
            }
        }
        //INSERTS DE TODO 2021 MENOS DICIEMBRE
        for (id=1;id<11;id++){
            for(ano=2021;ano<2022;ano++){
                for (mes=1;mes<12;mes++){
                    for(dia=1;dia<32;dia++){
                        int aforo=0;
                        for(int i=0;i<25;i++){
                            Document doc = new Document();
                            doc.append("id", id);
                            doc.append("dia", dia);
                            doc.append("mes", mes);
                            doc.append("ano", ano);
                            doc.append("temperatura", (int)(Math.random() * (50 - (-20) + 1) + (-20) ));
                            aforo+=(int)(Math.random() * (18 - (-9) + 1) + (-9));
                            if(aforo<0)
                                aforo=0;
                            doc.append("aforo", aforo);
                            doc.append("humedad", (int)(Math.random() * (99 - 1 + 1) + 1 ));
                            mini.insertOne(doc);
                        }
                    }
                }
            }
        }
        //INSERTS DE LO QUE LLEVAMOS DE AÑO
        ano=2021;
        mes=12;
        for (id=1;id<11;id++){
            for(dia=1;dia<12;dia++){
                int aforo=0;
                for(int i=0;i<25;i++){
                    Document doc = new Document();
                    doc.append("id", id);
                    doc.append("dia", dia);
                    doc.append("mes", mes);
                    doc.append("ano", ano);
                    doc.append("temperatura", (int)(Math.random() * (50 - (-20) + 1) + (-20) ));
                    aforo+=(int)(Math.random() * (18 - (-9) + 1) + (-9));
                    if(aforo<0)
                        aforo=0;
                    doc.append("aforo", aforo);
                    doc.append("humedad", (int)(Math.random() * (99 - 1 + 1) + 1 ));
                    mini.insertOne(doc);
                }
            }
        }
    }

    public void insertIndividual(BLE_Nordic dato) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        int dia = Integer.parseInt(formatter.format(date).substring(0,2));
        int mes = Integer.parseInt(formatter.format(date).substring(3,5));
        int ano = Integer.parseInt(formatter.format(date).substring(6,10));

        Document doc = new Document();
        doc.append("id", dato.getId());
        doc.append("dia", dia+1);
        doc.append("mes", mes);
        doc.append("ano", ano);
        doc.append("temperatura", dato.getTemperatura());
        doc.append("aforo", dato.getAforo_actual());
        doc.append("humedad",dato.getHumedad() );
        mini.insertOne(doc);

    }
}