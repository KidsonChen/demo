package com.example.demo;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Userfile.User;
import com.example.demo.Userfile.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;  // 通过依赖注入
    
    @Autowired
    private UserService userService;

    private final RequestCache requestCache = new HttpSessionRequestCache();

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@ModelAttribute LoginForm loginForm, Model model) {
        Optional<User> userOptional = userService.findByUseremail(loginForm.getUseremail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            if (passwordEncoder.matches(loginForm.getPassword(), user.getPassword())) {
                return "redirect:/home?name={username}";  // 假設登錄成功後重定向到儀表板
            } else {
                model.addAttribute("error", "密碼錯誤");
                return "login";
            }
        } else {
            model.addAttribute("error", "用戶不存在");
            return "login";
        }
    }
@GetMapping("/home")
    public String home(HttpServletRequest request, HttpServletResponse response, Model model) {
        // 檢查是否有保存的請求
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            return "redirect:" + targetUrl; // 重定向到保存的請求
        }
        return "home"; // 如果沒有保存的請求，返回默認的 home 頁面
    }


    @GetMapping("/diet_record")
public String dietRecord(Model model, Principal principal) {
    // 獲取當前登錄用戶的名稱
    String username = principal.getName(); // Spring Security 提供的 Principal 將返回當前用戶名
    model.addAttribute("username", username);
    return "diet_record"; // 返回 diet_record.html 模板
}

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User(null, null, null, null));
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        try {
            userService.registerNewUser(user);
            return "redirect:/login";  // 使用重定向來避免表單重提交
        } catch (Exception e) {
            model.addAttribute("error", "註冊失敗: " + e.getMessage());
            return "register";
        }
    }

    @PostMapping("/error")
    public String error() {
        return "error"; // 返回名為 error 的 Thymeleaf 模板
    }
}