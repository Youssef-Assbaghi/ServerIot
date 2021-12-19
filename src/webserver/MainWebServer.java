package webserver;
import java.io.IOException;

public class MainWebServer {
    public static void main(String[] args) throws IOException {
        webServer();
    }

    public static void webServer() throws IOException {
        //final Plano root = makeTreeCourses();
        // implement this method that returns the tree of
        // appendix A in the practicum handout
        // start your clock
        //final Clock reloj = Clock.getInstance();
        new WebServer();
    }

}