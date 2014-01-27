package apn.trunknotes.server;

import apn.trunknotes.reader.TrunkNoteReader;
import apn.trunknotes.types.TrunkNote;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.markdown4j.Markdown4jProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.String.format;

/**
 * User: antonion
 * Date: 22/01/14 10:54
 */
public class TrunkNotesServer {

    public static void main(String[] args) throws Exception {
        final Path path = Paths.get(args[0]);
        if (!path.toFile().exists()) {
            System.exit(-1);
        }
        Server server = new Server(8081);
        HandlerList handlerList = new HandlerList();
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        boolean exists = Files.exists(new File("src/main/resources").toPath());
        System.out.println("exists = " + exists);
        resourceHandler.setResourceBase("src/main/resources");

        handlerList.setHandlers(new Handler[]{resourceHandler,new TrunkNotesHandler(path)});
        server.setHandler(handlerList);
        server.start();
        server.join();
    }

    public static class TrunkNotesHandler extends AbstractHandler {

        private final Path path;

        public TrunkNotesHandler(Path path) {
            this.path = path;
        }

        @Override
        public void handle(String s,
                           Request request,
                           HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse) throws IOException, ServletException {
            if (s.startsWith("/assets")) return;
            httpServletResponse.setContentType("text/html;charset=utf-8");
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            request.setHandled(true);
            PrintWriter writer = httpServletResponse.getWriter();
            TrunkNote loadedNote = null;
            try {

                loadedNote = new TrunkNoteReader().load(new File(path.toAbsolutePath()+s+".markdown"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            Markdown4jProcessor markdown4jProcessor = new Markdown4jProcessor();
            writer.print(format(
                    "<html>\n" +
                            "<head>\n" +
                            "<link rel=\"stylesheet\" href=\"/assets/browser.css\" type=\"text/css\">" +
                            "    <title>%s</title>\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "<h1 class=\"trunk_h1\"><span class=\"link-light\">[[</span>%s<span class=\"link-light\">]]</span></h1>" +
                            "%s" +
                            "</body>\n" +
                            "</html>", loadedNote.title().asString(),loadedNote.noteFileName() ,markdown4jProcessor.process(loadedNote.body().asString())));
        }
    }
}

