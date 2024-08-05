package first.second.third.fuckmylife.controller;

import first.second.third.fuckmylife.Entity.LoginData;
import first.second.third.fuckmylife.Entity.User;
import first.second.third.fuckmylife.service.OrderService;
import first.second.third.fuckmylife.service.ProductService;
import first.second.third.fuckmylife.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

@Controller
public class Gateway {
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(Model model) {
        if(httpSession.getAttribute("user") != null) {
            return "redirect:/shop";
        }
        model.addAttribute("loginData", new LoginData());
        return "login.html";
    }

    @PostMapping("/auth")
    public String auth(@ModelAttribute("loginData") LoginData loginData,
                       @RequestParam(value = "rememberMe", required = false) Boolean rememberMe,
                       HttpServletResponse response,
                       Model model) {
        try {
            User user = userService.auth(loginData); // Предполагается, что метод auth возвращает User
            httpSession.setAttribute("user", user);

            // Если rememberMe установлен, создайте куки
            if (Boolean.TRUE.equals(rememberMe)) {
                Cookie authCookie = new Cookie("rememberMe", user.getUsername()); // Простой флаг куки
                authCookie.setPath("/"); // Установите путь для куки
                authCookie.setMaxAge(7 * 24 * 60 * 60); // Установите срок жизни куки на 7 дней
                authCookie.setSecure(true); // Сделать куки безопасной (требуется HTTPS)
                response.addCookie(authCookie);
            }
        } catch (ServiceException e) {
            model.addAttribute("error", e.getMessage());
            return "login.html";
        }
        return "redirect:/shop";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("rememberMe")) {
                    cookie.setMaxAge(0);
                    cookie.setPath("/"); // Убедитесь, что путь совпадает с путем установки куки
                    response.addCookie(cookie);
                }
            }
        }
        httpSession.setAttribute("user", null);
        httpSession.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        if(httpSession.getAttribute("user") != null) {
            return "redirect:/shop";
        }
        model.addAttribute("user", new User());
        return "registration.html";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") User user, Model model) {
        try {
            userService.save(user);
        } catch (ServiceException e) {
            model.addAttribute("error", e.getMessage());
            return "registration.html";
        }
        return "redirect:/login";
    }

    @RequestMapping("/error")
    public String handleError(@ModelAttribute("errorMessage") HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errorMessage = (String) request.getAttribute("errorMessage");

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == 404 || statusCode == 400 || statusCode == 500){
                model.addAttribute("error", errorMessage != null ? errorMessage : "Page not found");
            } else {
                model.addAttribute("error", "Произошла ошибка");
            }
        }
        return "error.html"; // Имя представления (без .html или .jsp)
    }

    @RequestMapping("/")
    public String showLoginPage(Model theModel) {
        return "redirect:/login";
    }
}

