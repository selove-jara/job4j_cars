package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.model.*;
import ru.job4j.cars.repository.CarRepository;
import ru.job4j.cars.repository.ColorRepository;
import ru.job4j.cars.service.*;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;
    private final CarBrandService carBrandService;
    private final CarModelService carModelService;
    private final EngineService engineService;
    private final CarService carService;
    private final CarBodyService carBodyService;
    private final ColorService colorService;
    private final CategoryService categoryService;
    private final PriceHistoryService priceHistoryService;

    @GetMapping
    public String getAll(Model model) {
        var posts = postService.findAllOrderById();
        Map<Integer, BigInteger> postPrices = new HashMap<>();

        for (Post post : posts) {
            List<PriceHistory> priceHistory = priceHistoryService.findByPostId(post.getId());
            if (!priceHistory.isEmpty()) {
                postPrices.put(post.getId(), priceHistory.get(0).getAfter());
            }
        }
        model.addAttribute("posts", posts);
        model.addAttribute("postPrices", postPrices);
        return "posts/list";
    }

    @GetMapping("/{id}")
    public String getCarDetails(@PathVariable("id") int id, Model model) {
        var post = postService.findById(id);
        List<PriceHistory> price = priceHistoryService.findByPostId(id);
        var latestPrice = price.get(0).getAfter();
        if (post.isPresent()) {
            model.addAttribute("post", post.get());
            model.addAttribute("price", latestPrice);
            return "posts/one";
        } else {
            return "errors/404";
        }
    }

    @GetMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute("carBrands", carBrandService.findAllOrderById());
        model.addAttribute("carModels", carModelService.findAllOrderById());
        model.addAttribute("engines", engineService.findAllOrderById());
        model.addAttribute("carBodies", carBodyService.findAllOrderById());
        model.addAttribute("colors", colorService.findAllOrderById());
        model.addAttribute("categories", categoryService.findAllOrderById());
        return "posts/create";
    }

    @PostMapping("/save")
    public String createPost(@RequestParam("engineId") int engineId,
                             @RequestParam("colorId") int colorId,
                             @RequestParam("categoryId") int categoryId,
                             @RequestParam("carBodyId") int carBodyId,
                             @RequestParam("carModelId") int carModelId,
                             @RequestParam("carBrandId") int carBrandId,
                             @RequestParam("carName") String carName,
                             @RequestParam("description") String description,
                             @RequestParam("price") int price,
                             @RequestParam List<MultipartFile> files,
                             Model model,
                             HttpSession session) {
        var user = (User) session.getAttribute("user");
        var created = LocalDateTime.now();
        List<FileDto> filesDto = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    filesDto.add(new FileDto(file.getOriginalFilename(), file.getBytes()));
                }
            }
            var car = carService.createCar(user, carName, engineId, carBrandId, carModelId, carBodyId, colorId, categoryId);
            postService.createNewPost(user, description, created, price, car, filesDto, false);
            return "redirect:/posts";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/last")
    public String list(Model model) {
        List<Post> recentPosts = postService.findPostsForLastDay();
        Map<Integer, BigInteger> postPrices = new HashMap<>();

        for (Post post : recentPosts) {
            List<PriceHistory> priceHistory = priceHistoryService.findByPostId(post.getId());
            if (!priceHistory.isEmpty()) {
                postPrices.put(post.getId(), priceHistory.get(0).getAfter());
            }
        }

        model.addAttribute("posts", recentPosts);
        model.addAttribute("postPrices", postPrices);
        return "posts/list";
    }

    @GetMapping("/photos")
    public String getPostsWithPhotos(Model model) {
        List<Post> posts = postService.findPostsWithPhotos();
        Map<Integer, BigInteger> postPrices = new HashMap<>();
        for (Post post : posts) {
            List<PriceHistory> priceHistory = priceHistoryService.findByPostId(post.getId());
            if (!priceHistory.isEmpty()) {
                postPrices.put(post.getId(), priceHistory.get(0).getAfter());
            }
        }

        model.addAttribute("posts", posts);
        model.addAttribute("postPrices", postPrices);
        return "posts/list";
    }

    @GetMapping("/brand/{brandName}")
    public String getPostsByBrand(@PathVariable String brandName, Model model) {
        List<Post> posts = postService.findPostsByCarBrand(brandName);
        model.addAttribute("posts", posts);
        return "posts/list";
    }

    @PostMapping("/complete/{id}")
    public String complete(@PathVariable("id") int id, Model model) {
        boolean rsl = postService.complete(id);
        if (!rsl) {
            model.addAttribute("message", "Не удалось изменить статус");
            return "errors/404";
        }
        return "redirect:/posts/" + id;
    }
}
