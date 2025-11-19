package rowing.web;

import java.io.IOException;
import java.net.URLEncoder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;

/**
 * Сервлет для обработки предпочтений пользователя
 * Сохраняет имя пользователя и цвет фона в Cookie
 * Отслеживает количество посещений в Session
 */
public class PreferencesProcessor extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String bgcolor = request.getParameter("bgcolor");

        if (username == null || username.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Имя пользователя не может быть пустым!");
            return;
        }

        Cookie usernameCookie = new Cookie("user.name", URLEncoder.encode(username, "UTF-8"));
        usernameCookie.setMaxAge(2592000);
        usernameCookie.setPath("/");
        response.addCookie(usernameCookie);

        Cookie colorCookie = new Cookie("page.color", bgcolor);
        colorCookie.setMaxAge(2592000);
        colorCookie.setPath("/");
        response.addCookie(colorCookie);

        HttpSession session = request.getSession();

        Integer visitCount = (Integer) session.getAttribute("visitCount");
        if (visitCount == null) {
            visitCount = 0;
        }
        visitCount++;
        session.setAttribute("visitCount", visitCount);

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String lastVisit = sdf.format(new java.util.Date());
        session.setAttribute("lastVisit", lastVisit);
        session.setAttribute("currentUser", username);

        System.out.println("=== Предпочтения сохранены ===");
        System.out.println("Пользователь: " + username);
        System.out.println("Цвет: " + bgcolor);
        System.out.println("Количество посещений: " + visitCount);

        response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/competition.jsp"));
    }
}