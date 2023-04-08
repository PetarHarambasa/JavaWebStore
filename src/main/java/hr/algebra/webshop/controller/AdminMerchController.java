package hr.algebra.webshop.controller;

import hr.algebra.webshop.model.Category;
import hr.algebra.webshop.model.Merch;
import hr.algebra.webshop.service.CategoryService;
import hr.algebra.webshop.service.MerchService;
import jakarta.transaction.Transactional;
import jakarta.websocket.server.PathParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static hr.algebra.webshop.controller.AuthenticationController.isAuthenticated;

@Controller
public class AdminMerchController {

    private final MerchService merchService;
    private final CategoryService categoryService;

    public AdminMerchController(MerchService merchService, CategoryService categoryService) {
        this.merchService = merchService;
        this.categoryService = categoryService;
    }

    @GetMapping("/adminMerch")
    public String getAdminMerch(Model model){
        if (isAuthenticated){
            model.addAttribute("merchList", merchService.getAllMerchItems());
            return "adminMerch";
        }
        return "redirect:/dragonBallStore";
    }

    public String getCategoryName(Long id){
        return categoryService.findCategoryByIdCategory(id).getName();
    }

    @GetMapping("/adminMerch/createNewMerch")
    public String createNewMerch(Model model){
        model.addAttribute("newMerch", new Merch());
        return "createNewMerch";
    }

    @PostMapping("/adminMerch/createNewMerch")
    public String addCreatedNewMerch(Model model, @ModelAttribute("newMerch") Merch newMerch, @RequestParam("imageBase64") MultipartFile imageBase64) throws IOException {
        imageConvertToBase64(newMerch, imageBase64);
        merchService.addNewMerch(newMerch);
        model.addAttribute("Message", "Merch created successfully");
        return  "createNewMerch";
    }

    private static void imageConvertToBase64(Merch newMerch, MultipartFile imageBase64) throws IOException {
        byte[] imageData = imageBase64.getBytes();
        String fileExtension = getFileExtension(Objects.requireNonNull(imageBase64.getOriginalFilename()));
        String base64Image = Base64.getEncoder().encodeToString(imageData);
        newMerch.setFrontImageBase64("data:image/"+fileExtension+";base64,"+base64Image);
    }

    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("/adminMerch/deleteMerch/{idMerch}")
    public String deleteMerch(@PathVariable Long idMerch){
        merchService.deleteMerchById(Long.valueOf(idMerch));
        return "redirect:/adminMerch";
    }

    public static String getFileExtension(String fileName) {
        // Get the last index of '.' in the file name
        int lastIndex = fileName.lastIndexOf('.');

        // If the last index is greater than 0 and less than the length of the file name - 1
        // then return the file extension
        if (lastIndex > 0 && lastIndex < fileName.length() - 1) {
            return fileName.substring(lastIndex + 1);
        } else {
            return "";
        }
    }
}
