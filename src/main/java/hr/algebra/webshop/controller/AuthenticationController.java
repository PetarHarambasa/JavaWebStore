package hr.algebra.webshop.controller;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import hr.algebra.webshop.model.LoginHistory;
import hr.algebra.webshop.model.ShopUser;
import hr.algebra.webshop.service.LoginHistoryService;
import hr.algebra.webshop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static hr.algebra.webshop.controller.DragonBallMerchController.merchCartItems;

@Controller
public class AuthenticationController {
    public static ShopUser authenticatedShopUser = new ShopUser(false);
    private final UserService userService;
    private final LoginHistoryService loginHistoryService;

    public AuthenticationController(UserService userService, LoginHistoryService loginHistoryService) {
        this.userService = userService;
        this.loginHistoryService = loginHistoryService;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("AuthenticatedShopUser", authenticatedShopUser);
        if (authenticatedShopUser.isAuthenticated()) {
            return "redirect:/dragonBallStore";
        }
        return "login";
    }

    @SneakyThrows
    @PostMapping("/login")
    public String processLoginForm(@RequestParam("email") String email, @RequestParam("password") String password,
                                   Model model, HttpServletRequest request) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        ShopUser shopUser = userService.getUserByEmail(email);
        if (shopUser == null) {
            model.addAttribute("AuthenticatedShopUser", authenticatedShopUser);
            model.addAttribute("ErrorMessage", "Login failed! Wrong credentials!");
        } else {
            if (shopUser.getEmail().trim().equals(email.trim()) && passwordEncoder.matches(password.trim(), shopUser.getPassword().trim())) {
                model.addAttribute("AuthenticatedShopUser", authenticatedShopUser);
                authenticatedShopUser = shopUser;
                authenticatedShopUser.setAuthenticated(true);
                userService.saveUser(authenticatedShopUser);
                LoginHistory loginHistory = newLoginHistory(authenticatedShopUser, request);
                loginHistoryService.saveLogin(loginHistory);
                TimeUnit.SECONDS.sleep(1);
                return "redirect:/dragonBallStore";
            } else {
                model.addAttribute("AuthenticatedShopUser", authenticatedShopUser);
                model.addAttribute("ErrorMessage", "Login failed! Wrong credentials!");
                model.addAttribute("Message", "");
            }
        }
        return "login";
    }

    private LoginHistory newLoginHistory(ShopUser authenticatedShopUser, HttpServletRequest request)
            throws IOException, GeoIp2Exception {
        LoginHistory loginHistory = new LoginHistory(null,
                authenticatedShopUser.getUsername(),
                authenticatedShopUser.getEmail(), Timestamp.valueOf(LocalDateTime.now()), getClientIpAddress(request));
        return loginHistory;
    }

    private String getClientIpAddress(HttpServletRequest request) throws IOException, GeoIp2Exception {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

        String ipAddressForPublicIpAddress = null;
        try {
            URL url = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            ipAddressForPublicIpAddress = in.readLine();
            System.out.println("My public IP address is: " + ipAddressForPublicIpAddress);

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File database = new File("GeoLite2-City.mmdb");
        InetAddress ipAddressWithCityAndCounty = InetAddress.getByName(ipAddressForPublicIpAddress);

        DatabaseReader reader = new DatabaseReader.Builder(database).build();
        CityResponse response = reader.city(ipAddressWithCityAndCounty);

        String city = response.getCity().getName();
        String country = response.getCountry().getName();


        return city + " " + country + " " + ipAddress;
    }

    @SneakyThrows
    @GetMapping("/logout")
    public String logoutProcess(Model model) {
        model.addAttribute("AuthenticatedShopUser", authenticatedShopUser);
        if (!authenticatedShopUser.isAuthenticated()) {
            return "redirect:/login";
        }
        authenticatedShopUser.setAuthenticated(false);
        userService.saveUser(authenticatedShopUser);
        authenticatedShopUser = new ShopUser(false);
        merchCartItems = new ArrayList<>();
        model.addAttribute("AuthenticatedShopUser", authenticatedShopUser);
        return "login";
    }
}
