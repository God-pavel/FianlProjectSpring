package springBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springBoot.entity.Product;
import springBoot.service.ProductService;
import springBoot.validator.AmountValidator;
import springBoot.validator.PriceValidator;
import springBoot.validator.Result;

import java.math.BigDecimal;
import java.util.Map;


@Controller
@RequestMapping("/storage")
public class StorageController {

    private final ProductService productService;

    @Autowired
    public StorageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String allProducts(Map<String, Object> model) {
        model.put("products", productService.getAllProducts());
        return "storage";
    }

    @GetMapping("{product}")
    public String productEditPage(@PathVariable Product product, Model model) {
        model.addAttribute("product", product);

        return "product_change";
    }

    @PostMapping
    public String productSave(
            @RequestParam("productId") Product product,
            @RequestParam("price") BigDecimal price,
            @RequestParam("amount") Long amount,
            Model model
    ) {

        Result checkAmount = new AmountValidator().validate(amount);

        if (!checkAmount.isValid()) {
            model.addAttribute("product", product);
            return "product_change";
        }

        Result checkPrice = new PriceValidator().validate(price);

        if (!checkPrice.isValid()) {
            model.addAttribute("product", product);
            return "product_change";
        }

        productService.editProduct(product, price, amount);

        return "redirect:/storage";
    }

}