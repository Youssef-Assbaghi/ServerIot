package webserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import milestone3.Plantilla;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


// Based on
// https://www.ssaurel.com/blog/create-a-simple-http-web-server-in-java
// http://www.jcgonzalez.com/java-socket-mini-server-http-example

public class WebServer {
    private static final int PORT = 8080; // port to listen to
    private ArrayList<BLE_Nordic> datos=new ArrayList<BLE_Nordic>();
    private Plantilla bd;

    private int max_tem;
    private int max_aforo;
    private int max_hum;
    private int min_tem;
    private int min_aforo;
    private int min_hum;
    private int med_tem;
    private int med_aforo;
    private int med_hum;

    public WebServer() throws IOException {
        bd=new Plantilla();
        try {
            bd.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ServerSocket serverConnect = new ServerSocket(PORT);
            System.out.println("Server started.\nListening for connections on port : " + PORT + " ...\n");
            // we listen until user halts server execution
            while (true) {
                // each client connection will be managed in a dedicated Thread
                new SocketThread(serverConnect.accept());
                // create dedicated thread to manage the client connection
            }
        } catch (IOException e) {
            System.err.println("Server Connection error : " + e.getMessage());
        }
    }

    /*private Plano findActivityById(int id) {
        return root.findActivityById(id);
    }*/

    private class SocketThread extends Thread {
        // SocketThread sees WebServer attributes
        private final Socket insocked;
        // Client Connection via Socket Class

        SocketThread(Socket insocket) {
            this.insocked = insocket;
            this.start();
        }

        @Override
        public void run() {
            // we manage our particular client connection
            BufferedReader in;
            PrintWriter out;
            String resource;

            try {
                // we read characters from the client via input stream on the socket
                in = new BufferedReader(new InputStreamReader(insocked.getInputStream()));
                // we get character output stream to client
                out = new PrintWriter(insocked.getOutputStream());
                // get first line of the request from the client
                String input = in.readLine();
                // we parse the request with a string tokenizer

                System.out.println("sockedthread : " + input);

                StringTokenizer parse = new StringTokenizer(input);
                String method = parse.nextToken().toUpperCase();
                // we get the HTTP method of the client
                if (!method.equals("GET")) {
                    System.out.println("501 Not Implemented : " + method + " method.");
                } else {
                    // what comes after "localhost:8080"
                    resource = parse.nextToken();
                    System.out.println("input " + input);
                    System.out.println("method " + method);
                    System.out.println("resource " + resource);

                    parse = new StringTokenizer(resource, "/[?]=&");
                    int i = 0;
                    String[] tokens = new String[20];
                    // more than the actual number of parameters
                    while (parse.hasMoreTokens()) {
                        tokens[i] = parse.nextToken();
                        System.out.println("token " + i + "=" + tokens[i]);
                        i++;
                    }

                    // Make the answer as a JSON string, to be sent to the Javascript client
                    String answer = makeHeaderAnswer() + makeBodyAnswer(tokens);
                    System.out.println("answer\n" + answer);
                    // Here we send the response to the client
                    out.println(answer);
                    out.flush(); // flush character output stream buffer
                }

                in.close();
                out.close();
                insocked.close(); // we close socket connection
            } catch (Exception e) {
                System.err.println("Exception : " + e);
            }
        }


        private String makeBodyAnswer(String[] tokens) throws IOException {
            String body = "";
            switch (tokens[0]) {
                case "get_tree" : {
                    int id = Integer.parseInt(tokens[1]);

                    break;
                }
                case "start": {
                    BLE_Nordic nonulo=new BLE_Nordic(3,2,1);
                    datos.add(nonulo);

                    //datos.get(0).predecir_modelo();
                    body = "{jajas}";
                    ObjectMapper mapper = new ObjectMapper();

                    String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(nonulo);
                    body=jsonStr;
                    Gson objGson = new Gson();
                    System.out.println(objGson.toJson(datos));



                    try (FileWriter file = new FileWriter("datos.json")) {

                        file.write(objGson.toJson(datos));
                        file.flush();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "stop": {
                    int id = Integer.parseInt(tokens[1]);

                    body = "{SA}";
                    break;
                }
                case "get_ble":{
                    System.out.println(tokens[1] +" "+tokens[2]+" "+tokens[3]+" "+tokens[4]);
                    if (datos==null){
                        datos.add(new BLE_Nordic(3,2,1));
                    }
                    int id=0;
                    for (BLE_Nordic cadena: datos) {
                        id=cadena.getId();
                        if (id==Integer.parseInt(tokens[4])){
                            datos.get(id).asignacion(Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2]),Integer.parseInt(tokens[3]));
                            datos.get(id).calcular_medias();
                            datos.get(id).predecir_modelo();
                            insertar(datos.get(id));
                            break;
                        }
                    }



                    ObjectMapper mapper = new ObjectMapper();

                    String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(datos.get(id));
                    body=jsonStr;
                    Gson objGson = new Gson();
                    System.out.println(objGson.toJson(datos.get(id)));

                    try (FileWriter file = new FileWriter("datos.json")) {

                        file.write(objGson.toJson(datos));
                        file.flush();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //body = activity.toJson(1).toString();
                    break;
                }
                case "actualizar_aforo_maximo": {
                    System.out.println(tokens[1]+" "+tokens[2]);

                    int id=0;
                    for (BLE_Nordic cadena: datos) {
                        id=cadena.getId();
                        if (id==Integer.parseInt(tokens[2])){
                            datos.get(id).setAforo_maximo(Integer.parseInt(tokens[1]));
                            datos.get(id).mirar_aforo();
                            break;
                        }
                    }

                    //body = activity.toJson(1).toString();
                    ObjectMapper mapper = new ObjectMapper();

                    String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(datos.get(id));
                    body=jsonStr;
                    Gson objGson = new Gson();
                    System.out.println(objGson.toJson(datos.get(id)));

                    try (FileWriter file = new FileWriter("datos.json",true)) {

                        file.write(objGson.toJson(datos));
                        file.flush();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
                }
                case "insert":{
                    bd.insertDatos();
                    body="DATOS INSERTADOS";
                    break;
                }
                case "insert_trampa":{
                    bd.insertTrampa();
                    body="DATOS INSERTADOS";
                    break;
                }

                case "historicos":{
                    int id= Integer.parseInt(tokens[1]);
                    List<Integer> res1 = new ArrayList<Integer>();
                    res1=bd.historicos_minimos(id);
                    List<Integer> res2 = new ArrayList<Integer>();
                    res2=bd.historicos_maximos(id);
                    List<Double> res3 = new ArrayList<Double>();
                    res3=bd.historicos_medios(id);

                    JsonObject jason=crearBody(res1,res2,res3);
                    body= jason.toString();
                    break;
                }

                case "valoresDia":{
                    int id= Integer.parseInt(tokens[1]);
                    String fecha=tokens[2];
                    int dia = Integer.parseInt(fecha.substring(0,2));
                    int mes = Integer.parseInt(fecha.substring(3,5));
                    int ano = Integer.parseInt(fecha.substring(6,10));
                    List<Integer> res1 = new ArrayList<Integer>();
                    res1=bd.dia_minimos(id,dia,mes,ano);
                    List<Integer> res2 = new ArrayList<Integer>();
                    res2=bd.dia_maximos(id,dia,mes,ano);
                    List<Double> res3 = new ArrayList<Double>();
                    res3=bd.dia_medios(id,dia,mes,ano);

                    JsonObject jason=crearBody(res1,res2,res3);
                    body= jason.toString();
                }
                default:
                    assert false;
            }



            //System.out.println(body);
            return body;
        }

        private JsonObject crearBody(List<Integer> res1,List<Integer> res2,List<Double> res3){
            JsonObject hey=new JsonObject();
            hey.addProperty("temp_hist_min",res1.get(0));
            hey.addProperty("aforo_hist_min",res1.get(1));
            hey.addProperty("hum_hist_min",res1.get(2));
            hey.addProperty("temp_hist_max",res2.get(0));
            hey.addProperty("aforo_hist_max",res2.get(1));
            hey.addProperty("hum_hist_max",res2.get(2));
            hey.addProperty("media_hist_temp",res3.get(0));
            hey.addProperty("media_hist_aforo",res3.get(1));
            hey.addProperty("media_hist_hum",res3.get(2));
            return hey;
        }
        private void insertar(BLE_Nordic dato){
            bd.insertIndividual(dato);
        }
        private String makeHeaderAnswer() {
            String answer = "";
            answer += "HTTP/1.0 200 OK\r\n";
            answer += "Content-type: application/json\r\n";
            answer += "\r\n";
            // blank line between headers and content, very important !
            return answer;
        }
    } // SocketThread

} // WebServer