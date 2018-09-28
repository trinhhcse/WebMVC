package webmvc.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import webmvc.demo.model.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
//@Transactional
@RequestMapping("/product")
public class ProductWebController {

    private String BASE_URL = "http://localhost:8080";
    private RestTemplate restTemplate = new RestTemplate();

    //Save
    @PostMapping("/save")
    public String save(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @RequestParam("file") MultipartFile file){
        if(bindingResult.hasErrors()){
            return "product";
        }else{
            //Add new
            if(product.getId()==0){
                if(file!=null&&file.getSize() != 0l){
                    try {
                        product.setImage(file.getBytes());
                    }catch (Exception e){}
                }
                restTemplate.postForObject(BASE_URL+"/products",product,Product.class);
            }else{
                //update
                Product existed = restTemplate.getForObject(BASE_URL+"/products/"+product.getId(),Product.class);
//            existed.setImage(product.getImage());
                existed.setName(product.getName());
                existed.setPrice(product.getPrice());
                existed.setQuantity(product.getQuantity());
                if(file!=null&&file.getSize() != 0l){
                    try {
                        existed.setImage(file.getBytes());
                    }catch (Exception e){}
                }

                restTemplate.put(BASE_URL+"/products/"+product.getId(),existed);
            }
            return "redirect:/product/list";
        }
    }
    //    @PostMapping("/product/create")
//    public String save(@ModelAttribute("productForm") ProductForm productForm){
//        Product product = new Product();
//        product.setName(productForm.getName());
//        product.setPrice(productForm.getPrice());
//        product.setQuantity(productForm.getQuantity());
//        try {
//            product.setImage(productForm.getFileData().getBytes());
//        }catch (Exception e){}
//
//        restTemplate.postForObject(BASE_URL+"/products",product,Product.class);
//
//        return "redirect:/product/list";
//    }
//        if(product.getId()==null){
//
//        restTemplate.postForObject(BASE_URL+"/products",product,Product.class);
//    }else{
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("id", product.getId());
//
//        restTemplate.put(BASE_URL+"/products/"+product.getId(),product,params);
//    }
//
//    @GetMapping("/showFormForAdd")
//    public String showForm(Model model){
//        model.addAttribute("product",new Product());
//        return "product-form";
//    }
//
    @GetMapping("/create")
    public String showFormForAdd(Model model){
        model.addAttribute("product",new Product());
        return "product";
    }

    @GetMapping("/update/{id}")
    public String showFormForUpdate(@PathVariable("id") @Valid int id, Model model){
        Product product = restTemplate.getForObject(BASE_URL+"/products/"+id,Product.class);
        model.addAttribute("product",product);
        System.out.println("Update for product id = "+product.getId());
        return "product";
    }
//    @GetMapping("/product/showFormForAdd")
//    public String showForm(Model model){
//        model.addAttribute("productForm",new ProductForm());
//        return "product-form";
//    }
//    //Update product
//    @GetMapping("/saveProduct")
//    public String updateProduct(@RequestParam("id") String id, @ModelAttribute("product") Product product){
//
//        return "redirect:/product/list";
//    }
//
    //Delete product
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") @Valid int id, Model model){
        restTemplate.delete(BASE_URL+"/products/"+id);
        return "redirect:/product/list";
    }


    //Show list of object
    @GetMapping("/list")
    public String listProducts(Model model){
        ResponseEntity<Object[]> response = restTemplate.getForEntity(BASE_URL+"/products",Object[].class);
        model.addAttribute("products", Arrays.asList(response.getBody()));
        return "product-list";
    }

    @GetMapping( "/productImage/{id}" )
    public void productImage(HttpServletRequest request, HttpServletResponse response, Model model,
                             @PathVariable("id") @Valid int id) throws IOException {
        Product product = restTemplate.getForObject(BASE_URL+"/products/"+id,Product.class);
        if (product != null && product.getImage() != null) {
            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            response.getOutputStream().write(product.getImage());
        }
        response.getOutputStream().close();
    }
}
