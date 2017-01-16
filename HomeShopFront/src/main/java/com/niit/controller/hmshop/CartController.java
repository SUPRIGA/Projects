package com.niit.controller.hmshop;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.niit.homeshop.model.Cart;
import com.niit.homeshop.model.Product;
import com.niit.homeshop.service.CartDAO;
import com.niit.homeshop.service.ProductDAO;



@Controller
public class CartController {
	
	@Autowired
	private Cart cart;
	
	@Autowired
	private CartDAO cartDAO;
	
	@Autowired
	ProductDAO productDAO;
	
	@Autowired
	Product product;
	
	@RequestMapping("/order/{user_name}")
	public String placeorder(@PathVariable("user_name") String user_name){
		
		return "redirect:/checkout?user_name"+user_name;
		
	}
	@RequestMapping("/viewcart")
	public String viewcart(){
		return "Cart";
	  
		/*@ExceptionHandler({Exception.class})
		public ModelAndView viewcart(){
			
			ModelAndView mv = new ModelAndView("404");
			mv.addObject("msg","hi sorry....");
			return mv;*/
			
		}
		
		
	
	@RequestMapping("/refreshcart/{user_name}")
	public @ResponseBody List<Cart> refreshCartItem(@PathVariable("user_name") String user_name){
		
		return cartDAO.getCart(user_name);
	}
	
	@RequestMapping(value="/addtocart/{itemId}" , method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void addtocart(@PathVariable("itemId") int itemId,Principal pr){
		Product p = productDAO.getProduct(itemId);
		cart.setUser_name(pr.getName());
		cart.setPn(p.getPn());
		cart.setId(p.getId());
		cart.setPrice(p.getPrice());
		cart.setQuantity(1);
		System.out.println("inside insert cartController");
		cartDAO.insertCart(cart);
	}
	@RequestMapping(value="/removeItemFromCart/{cart_id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void removeItemFromCart(@PathVariable("cart_id") int cart_id){
		cartDAO.deleteCart(cart_id);
	}
	
}
