package rowing.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.ResourceBundle;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet для вывода результатов соревнований по гребле
 * с поддержкой локализации (русский и английский языки)
 */
public class RowingCompetitions extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RowingCompetitions() {
        super();
    }

    /**
     * Processes requests for both HTTP GET and POST methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String lang = request.getParameter("lang");
        if (lang == null) {
            lang = "ru";
        }
        if (!("en".equalsIgnoreCase(lang) || "ru".equalsIgnoreCase(lang))) {
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,
                    "Параметр lang может принимать значения: en или ru");
            return;
        }

        response.setContentType("text/html;charset=UTF-8");

        Locale currentLocale = "en".equalsIgnoreCase(lang)
                ? Locale.ENGLISH
                : new Locale("ru", "RU");

        ResourceBundle messages = ResourceBundle.getBundle("RowingMessages", currentLocale);

        PrintWriter out = response.getWriter();
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("<title>" + messages.getString("page.title") + "</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; margin: 20px; }");
            out.println("h1 { color: #2c3e50; }");
            out.println("table { border-collapse: collapse; width: 100%; max-width: 800px; }");
            out.println("th { background-color: #3498db; color: white; padding: 12px; text-align: left; }");
            out.println("td { padding: 10px; border: 1px solid #ddd; }");
            out.println("tr:nth-child(even) { background-color: #f2f2f2; }");
            out.println(".language-selector { margin-bottom: 20px; }");
            out.println(".language-selector a { margin: 0 10px; padding: 5px 10px; text-decoration: none; border: 1px solid #3498db; border-radius: 3px; }");
            out.println(".language-selector a:hover { background-color: #3498db; color: white; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");

            out.println("<div class=\"language-selector\">");
            out.println("Язык: ");
            out.println("<a href=\"RowingCompetitions?lang=ru\">Русский</a> | ");
            out.println("<a href=\"RowingCompetitions?lang=en\">English</a>");
            out.println("</div>");

            out.println("<h1>" + messages.getString("page.heading") + "</h1>");

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>" + messages.getString("table.date") + "</th>");
            out.println("<th>" + messages.getString("table.distance") + "</th>");
            out.println("<th>" + messages.getString("table.time") + "</th>");
            out.println("</tr>");

            out.println("<tr><td>15.09.2025</td><td>500 м</td><td>1:45.32</td></tr>");
            out.println("<tr><td>22.09.2025</td><td>1000 м</td><td>3:42.15</td></tr>");
            out.println("<tr><td>05.10.2025</td><td>2000 м</td><td>7:15.48</td></tr>");
            out.println("<tr><td>12.10.2025</td><td>500 м</td><td>1:43.87</td></tr>");
            out.println("<tr><td>28.10.2025</td><td>1000 м</td><td>3:38.92</td></tr>");
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
