package first.second.third.fuckmylife.controller;

import first.second.third.fuckmylife.Entity.Order;
import first.second.third.fuckmylife.Entity.OrderList;
import first.second.third.fuckmylife.Entity.Product;
import first.second.third.fuckmylife.Entity.User;
import first.second.third.fuckmylife.service.ImagesService;
import first.second.third.fuckmylife.service.OrderService;
import first.second.third.fuckmylife.service.ProductService;
import first.second.third.fuckmylife.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
public class Shop {

    @Autowired
    private ProductService productService;
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ImagesService imagesService;

    @RequestMapping("/shop/{id}")
    public String showProduct(@PathVariable("id") Long id, Model model) {
        try {
            productService.actualityCheck(productService.getProductById(id));
        } catch (ServiceException e) {
            model.addAttribute("error", "Sorry, this product is out of stock!");
            return "error.html";
        }
        model.addAttribute("product", productService.getProductById(id));
        return "product.html";
    }

    @PostMapping("/shop/add-to-cart")
    public String addToCart(HttpSession session, @RequestParam("productId") Long id, @RequestParam("size") String size) {
        if (session.getAttribute("cart") == null) {
            session.setAttribute("cart", new HashMap<Long, String>());
        }
        HashMap<Long, String> cart = (HashMap<Long, String>) session.getAttribute("cart");
        if (cart.containsKey(id) && cart.get(id).equals(size)) {
            return "redirect:/shop/" + id;
        }
        cart.put(id, size);
        session.setAttribute("cart", cart);
        return "redirect:/shop/" + id;
    }

    @GetMapping("/shop/cart")
    public String showCart(Model model) {
        if (httpSession.getAttribute("cart") == null) {
            httpSession.setAttribute("cart", new HashMap<Product, String>());
        }
        HashMap<Long, String> cart = (HashMap<Long, String>) httpSession.getAttribute("cart");
        HashMap<Product, String> cart1 = new HashMap<>();
        for (int i = 0; i < cart.size(); i++) {
            cart1.put(productService.getProductById((Long) cart.keySet().toArray()[i]), cart.get(cart.keySet().toArray()[i]));
        }
        model.addAttribute("cart", cart1);
        return "cart.html";
    }

    @PostMapping("/shop/checkout")
    public String checkout(@ModelAttribute OrderList orderList, Model model) {
        if(orderList == null || orderList.getOrders() == null) {
            return "redirect:/error?message=Your cart is empty!";
        }
        User user = (User) httpSession.getAttribute("user");
        orderList.setUser(user);
        for(Order order : orderList.getOrders()) {
            order.setProduct(productService.getProductById(order.getId()));
        }
        try {
            productService.checkAndProcessOrder(orderList);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error.html";
        }
        productService.substractProduct(orderList);
        orderService.saveOrderList(orderList);
        model.addAttribute("orderList", orderList);
        httpSession.setAttribute("cart", new HashMap<Long, String>());
        return "success.html";
    }
    @GetMapping("/shop/removefromcart/{id}")
    public String removeFromCart(@PathVariable("id") Long id, Model model){
        HashMap<Long, String> cart = (HashMap<Long, String>) httpSession.getAttribute("cart");
        cart.remove(id);
        httpSession.setAttribute("cart", cart);
        return "redirect:/shop/cart";
    }

    @RequestMapping("/shop")
    public String showShop(Model model) {
        model.addAttribute("products", productService.getActualProducts());
        return "shop.html";
    }

    @RequestMapping("/shop/main")
    public String showMain(Model model){
        model.addAttribute("images", imagesService.findAllImages());
        return "main.html";
    }

    @RequestMapping("/shop/terms")
    public String showAbout(){
        return "about.html";
    }

    @RequestMapping("/shop/contact")
    public String contact(){
        return "contact.html";
    }
}
