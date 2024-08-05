package first.second.third.fuckmylife.controller;

import first.second.third.fuckmylife.Entity.OrderList;
import first.second.third.fuckmylife.Entity.PreviewImage;
import first.second.third.fuckmylife.Entity.Product;
import first.second.third.fuckmylife.service.ImagesService;
import first.second.third.fuckmylife.service.OrderService;
import first.second.third.fuckmylife.service.ProductService;
import first.second.third.fuckmylife.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class Admin {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ImagesService imagesService;
    @Autowired
    private HttpSession httpSession;

    @GetMapping("/admin/orders")
    public String showOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrdersByStatus(false));
        return "orders.html";
    }
    // Обработка данных из формы
    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute("product") Product product, Model model){
        productService.addProduct(product);

        return "redirect:/admin/productmanager";
    }

    @RequestMapping("/admin/productmanager")
    public String showProductManager(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("products", productService.getAllProducts());
        return "productpanel.html"; // Название вашего HTML шаблона
    }


    @GetMapping("admin/add-to-blacklist/{id}")
    public String addToBlacklist(@PathVariable("id") Long id, Model model) {
        userService.addToBlackList(id);
        for(OrderList orderList : orderService.getAllOrders()) {
            if(orderList.getUser().getId().equals(id)) {
                orderService.deleteOrder(orderList);
            }
        }
        return "redirect:/admin/orders";
    }

    @GetMapping("/admin/mark-done/{id}")
    public String markOrderDone(@PathVariable("id") Long id){
        orderService.confirmOrder(id);
        return "redirect:/admin/orders";
    }

    @GetMapping("/admin/orders-history")
    public String orderHistory(Model model){
        model.addAttribute("orders", orderService.getAllOrdersByStatus(true));
        return "history.html";
    }

    @RequestMapping("/admin/users")
    public String userPanel(Model model){
        model.addAttribute("admins", userService.getAllPrivUsers());
        model.addAttribute("blacklistedUsers", userService.getAllBlacklisted());
        return "users.html";
    }

    @PostMapping("/admin/add-admin")
    public String addAdmin(@RequestParam("username") String username){
        userService.changeUserRole(username, "ADMIN");
        return "redirect:/admin/users";
    }

    @PostMapping("/admin/add-blacklisted")
    public String addBlacklisted(@RequestParam("username") String username){
        userService.changeUserRole(username, "MUTED");
        return "redirect:/admin/users";
    }

    @PostMapping("/admin/remove-role")
    public String removeRole(@RequestParam("username") String username){
        userService.changeUserRole(username, "USER");
        return "redirect:/admin/users";
    }
    @GetMapping("/admin/deleteProduct/{id}")
    public String deleteProduct(@PathVariable("id") Long id){
        productService.deleteProduct(id);
        return "redirect:/admin/productmanager";
    }

    @GetMapping("/admin/editProduct/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model){
        model.addAttribute("product", productService.getProductById(id));
        return "productedit.html";
    }
    @PostMapping("/admin/editProduct")
    public String editConfirm(@ModelAttribute Product product){
        productService.editProduct(product);
        return "redirect:/admin/productmanager";
    }


    @GetMapping("/admin/manageImages")
    public String manageImages(Model model) {
        List<PreviewImage> images = imagesService.findAllImages();
        model.addAttribute("images", images);
        return "images.html";
    }

    @PostMapping("/admin/addImage")
    public String addImage(@RequestParam("imageUrl") String imageUrl) {
        PreviewImage previewImage = new PreviewImage();
        previewImage.setUrl(imageUrl);
        imagesService.saveImage(previewImage);
        return "redirect:/admin/manageImages";
    }

    @GetMapping("/admin/deleteImage/{id}")
    public String deleteImage(@PathVariable("id") Long id) {
        PreviewImage previewImage = imagesService.findImageById(id);
        if (previewImage != null) {
            imagesService.deleteImage(previewImage);
        }
        return "redirect:/admin/manageImages";
    }
}
