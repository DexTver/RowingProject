package rowing.web;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        String athlete = request.getParameter("athlete");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Соревнования по гребле</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; margin: 20px; }");
            out.println("h1 { color: #2c3e50; }");
            out.println("table { border-collapse: collapse; width: 100%; max-width: 800px; }");
            out.println("th { background-color: #3498db; color: white; padding: 12px; text-align: left; }");
            out.println("td { padding: 10px; border: 1px solid #ddd; }");
            out.println("tr:nth-child(even) { background-color: #f2f2f2; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");

            if (athlete != null && !athlete.isEmpty()) {
                out.println("<h1>Результаты спортсмена: " + athlete + "</h1>");
            } else {
                out.println("<h1>Результаты соревнований по гребле</h1>");
            }

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Дата соревнования</th>");
            out.println("<th>Дистанция</th>");
            out.println("<th>Время</th>");
            out.println("</tr>");

            out.println("<tr>");
            out.println("<td>15.09.2025</td>");
            out.println("<td>500 м</td>");
            out.println("<td>1:45.32</td>");
            out.println("</tr>");

            out.println("<tr>");
            out.println("<td>22.09.2025</td>");
            out.println("<td>1000 м</td>");
            out.println("<td>3:42.15</td>");
            out.println("</tr>");

            out.println("<tr>");
            out.println("<td>05.10.2025</td>");
            out.println("<td>2000 м</td>");
            out.println("<td>7:15.48</td>");
            out.println("</tr>");

            out.println("<tr>");
            out.println("<td>12.10.2025</td>");
            out.println("<td>500 м</td>");
            out.println("<td>1:43.87</td>");
            out.println("</tr>");

            out.println("<tr>");
            out.println("<td>28.10.2025</td>");
            out.println("<td>1000 м</td>");
            out.println("<td>3:38.92</td>");
            out.println("</tr>");

            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    /**
     * Handles the HTTP GET method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP POST method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
