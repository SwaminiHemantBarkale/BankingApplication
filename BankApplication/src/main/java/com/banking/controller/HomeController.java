package com.banking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.banking.entity.Admin;
import com.banking.entity.Customers;
import com.banking.helper.Message;
import com.banking.repository.AdminRepository;
import com.banking.repository.CustomersRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private CustomersRepository customersRepository;
	
	
	@RequestMapping("/indexPage")
	public String indexPage(Model model) {
		
		model.addAttribute("title", "Index Page");
		
		return "index";
		
	}
	
	@RequestMapping("/homeA")
	public String adminHome(Model model) {
		
		model.addAttribute("title","Admin Home");
		
		return "adminDashboard";
	}
	
	@RequestMapping("/adminLogin")
	public String adminLogin(Model model) {
		
		model.addAttribute("title", "Admin Login");
		
		return "adminLoginForm";
	}
	
	@PostMapping("/do_adminLogin")
	public String doAdminLogin(Model model,
			                   @RequestParam("username") String username,
			                   @RequestParam("password") String password,
			                   HttpSession session) {
		
		
		Admin admin=adminRepository.getAdminByUsername(username);
		String Username=admin.getUsername();
		String Password=admin.getPassword();
		
		if(Username.equals(username) && Password.equals(password)) {
			
			
			return "adminDashboard";
		}
		
		return "adminLoginForm";
		
		
	}
	
	@GetMapping("/add_customers")
	public String addCustomer(Model model,
			                  HttpSession session) {
		
		model.addAttribute("title", "Add Customer Form");
		
		return "addCustomerForm";
	}
	
	@PostMapping("/process_add_customer")
	public String processAddCustomer(Model model,
			                         @ModelAttribute Customers customers,
			                         @RequestParam("name") String name,
			                         @RequestParam("username") String username,
			                         @RequestParam("password") String password,
			                         @RequestParam("balance") Integer openingBalance,
			                         HttpSession session) {
		
		model.addAttribute("title", "Process Add Customer");
		
		Admin admin=adminRepository.getById(1);
		
	    customers.setAdmin(admin);
	    customers.setName(name);
	    customers.setUsername(username);
	    customers.setPassword(password);
	    customers.setBalance(openingBalance);
	    
	    customersRepository.save(customers);
		
	    session.setAttribute("message", new Message("Customer added successfully !!!","alert-warning"));
		return "addCustomerForm";
	}
	
	@GetMapping("/view_all_customers")
	public String viewAllCustomers(Model model) {
		
		model.addAttribute("title", "View All Customers");
		List<Customers> customers=customersRepository.findAll();
		
		model.addAttribute("customers", customers);
		
		return "viewAllCustomers";
	}
	
	@RequestMapping("/customerLogin")
	public String customerLogin(Model model) {
		
		model.addAttribute("title","Customer Login Form");
		
		return "customerLoginForm";
	}
	
	@PostMapping("/do_customerLogin")
	public String doCustomerLogin(Model model,
			                     @RequestParam("username") String username,
			                     @RequestParam("password") String password) {
		
		
		Customers customers=customersRepository.getCustomersByUsername(username);
		
		String Username=customers.getUsername();
		String Password=customers.getPassword();
		
		if(Username.equals(username) && Password.equals(password)) {
			
			return "customerDashboard";
		}
		
		return "customerLoginForm";
	}
	
	@RequestMapping("/homeC")
	public String home(Model model) {
		
		model.addAttribute("title", "Customer dashboard");
		
		return "customerDashboard";
	}
	
	@GetMapping("/checkBalance")
	public String checkBalance(Model model) {
		
		model.addAttribute("title","Chack Balance Form");
		
		return "checkBalanceForm";
	}
	
	@PostMapping("/process_checkBalance")
	public String processCheckBalance(Model model,
			                          @RequestParam("acNo") Integer acNo,
			                          HttpSession session) {
		
		model.addAttribute("title"," Process Check Balance");
		
		Customers customers=customersRepository.getById(acNo);
		
		model.addAttribute("customer", customers);
		session.setAttribute("message",new Message("Your balance is.......", "alert-warning"));
		
		return "viewBalance";
	}
	
	@GetMapping("/depositeMoney")
	public String depositeMoney(Model model) {
		model.addAttribute("title", "Deposite Money Form");
		
		return "depositeMoneyForm";
	}
	
	@PostMapping("/process_depositeAmount")
	public String processDepositeAmount(Model model,
			                            @RequestParam("") Integer acNo,
			                            @RequestParam("") Integer depositeAmount,
			                            HttpSession session) {
		
		model.addAttribute("title","Process Deposite Money");
		
		Customers customers=customersRepository.getById(acNo);
		int oldBalance=customers.getBalance();
		
		int newBalance=oldBalance+depositeAmount;
		
		customers.setBalance(newBalance);
		customersRepository.save(customers);
		
		session.setAttribute("message", new Message("Amount deposited successfully !!!","alert-warning"));
		return "depositeMoneyForm";
	}
	
	@GetMapping("/withdrawMoney")
	public String withdrawMoney(Model model) {
		
		model.addAttribute("title", "Withdraw Money Form");
		
		return "withdrawMoneyForm";
	}
	
	@PostMapping("/process_withdrawAmount")
	public String processWithdrawAmount(Model model,
			                     @RequestParam("acNo") Integer acNo,
			                     @RequestParam("withdrawAmount") Integer withdrawAmount,
			                     HttpSession session) {
		
		
		Customers customers=customersRepository.getById(acNo);
		
		int oldBalance=customers.getBalance();
		
		if(oldBalance-withdrawAmount>=1000) {
			
			int newBalance=oldBalance-withdrawAmount;
			customers.setBalance(newBalance);
			
			customersRepository.save(customers);
			
			session.setAttribute("message",new Message("Amount withdrawn successfully !!!","alert-warning"));
			return "withdrawMoneyForm";
		}
		
		    session.setAttribute("message", new Message("Sorry !!! Amount cannot be withdrawn..Insufficient Balance","alert-danger"));
			return "customerDashboard";
		
		
		
	}
	
	@GetMapping("/transferMoney")
	public String transferMoney(Model model) {
		
		model.addAttribute("title","Transfer Money Form");
		
		return "transferMoneyForm";
	}
	
	@PostMapping("/process_transferMoney")
	public String processTransferMoney(Model model,
			                           @RequestParam("acNoFrom") Integer acNoFrom,
			                           @RequestParam("acNoTo") Integer acNoTo,
			                           @RequestParam("transferAmount") Integer transferAmount,
			                           HttpSession session) {
		
		Customers customersFrom=customersRepository.getById(acNoFrom);
		int oldBalanceFrom=customersFrom.getBalance();
		
		Customers customersTo=customersRepository.getById(acNoTo);
		int oldBalanceTo=customersTo.getBalance();
		
		if(oldBalanceFrom-transferAmount>=1000) {
			
			int newBalanceTo=oldBalanceTo+transferAmount;
			customersTo.setBalance(newBalanceTo);
			
			customersRepository.save(customersTo);
			
			
			int newBalanceFrom=oldBalanceFrom-transferAmount;
			customersFrom.setBalance(newBalanceFrom);
			
			customersRepository.save(customersFrom);
			
			session.setAttribute("message",new Message("Amount transfered successfully !!!","alert-warning"));
			return "transferMoneyForm";
		}
		
		session.setAttribute("message",new Message("Sorry !!! Amount could not be transfered...Insufficient balance","alert-danger"));
		return "customerDashboard";
		
	}
	
	@GetMapping("/editInfo")
	public String editInfo(Model model) {
		
		model.addAttribute("title","Edit Info");
		
		return "editInfoForm";
	}
	
	@PostMapping("/process_editInfo")
	public String processEditInfo(Model model,
			                      @RequestParam("acNo") Integer acNo,
			                      @RequestParam("username") String Username,
			                      @RequestParam("password") String Password,
			                       HttpSession session) {
		
		Customers customers=customersRepository.getById(acNo);
		
		customers.setUsername(Username);
		customers.setPassword(Password);
		
		customersRepository.save(customers);
		
		session.setAttribute("message",new Message("Information updated successfully !!!","alert-warning"));
		return "editInfoForm";
		
	}
	
	@GetMapping("/deleteCustomer/{acNo}")
	public String deleteCustomer(Model model,
			                     @PathVariable("acNo") Integer acNo,
			                     HttpSession session) {
		
		model.addAttribute("title","Delete Customer");
		
		int delete=customersRepository.deleteCustomersByAccountNo(acNo);
		
		session.setAttribute("message", new Message("Customer deleted successfully !!!","alert-warning"));
		return "adminDashboard";
	}
	
	@PostMapping("/updateCustomer/{acNo}")
	public String updateCustomer(Model model,
			                     @PathVariable("acNo") Integer acNo ) {
		
		model.addAttribute("title","Update Customer Form");
		
		Customers customers=customersRepository.getById(acNo);
		model.addAttribute("customers", customers);
		
		return "updateCustomerForm";
	}
	
	@PostMapping("/process_update_customer")
	public String processUpdateCustomers(Model model,
			                             @RequestParam("acNo") Integer acNo,
			                             @RequestParam("name") String name,
			                             HttpSession session) {
		
		Customers customers=customersRepository.getById(acNo);
		
		customers.setName(name);
		customersRepository.save(customers);
		
		session.setAttribute("message", new Message("Customer updated successfully !!!","alert-warning"));
		return "adminDashboard";
		
		
	}
	
	

}
