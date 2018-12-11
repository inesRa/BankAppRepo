package com.bank.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bank.entities.Compte;
import com.bank.entities.Operation;
import com.bank.metier.Bank;
import com.bank.metier.IBank;

@Controller
public class BankController {
	@Autowired
	private IBank bankMetier;
	@RequestMapping("/operation")
	public String index() {
		return "compte";
	}
	

	@RequestMapping("/consulterCompte")
	public String consulter(Model model, String codeCompte,
			@RequestParam(name="page", defaultValue="0") int page,
			@RequestParam(name="size", defaultValue="5") int size){
		model.addAttribute("codeCompte", codeCompte);
		try{
			Compte cp = bankMetier.consulterCompte(codeCompte);
			model.addAttribute("compte", cp);
			Page<Operation> pageOperations= bankMetier.listOperation(codeCompte, page, size);
			model.addAttribute("listOperations", pageOperations.getContent());
			int[] pages = new int [pageOperations.getTotalPages()];
			model.addAttribute("pages",pages);
		} catch (Exception e) {
			model.addAttribute("exception",e);
		}
		return "compte";
	}
	
	@RequestMapping(value="/saveOperation", method=RequestMethod.POST)
	public String saveOperation(Model model, 
			String typeOperation, String codeCompte,
			double montant, String codeCompte2){
		
		try{
			if(typeOperation.equals("VERS")){
				bankMetier.verser(codeCompte, montant);
			}
			else if(typeOperation.equals("RETR")){
				bankMetier.retirer(codeCompte, montant);
			} 
			if (typeOperation.equals("VIR")){
				bankMetier.virement(codeCompte, codeCompte2, montant);
			}
		} catch(Exception e) {
			model.addAttribute("error", e);
			return "redirect:/consulterCompte?codeCompte="+codeCompte+
					"&error="+e.getMessage();
		}
		
		return "redirect:/consulterCompte?codeCompte="+codeCompte;
	}

// ****************************for junit test courses***********************************************
	
	public int isCorrectLogin(String string) {
		String users[]= {"admin","user"};
		for(int i=0;i<users.length;i++) {
			if (users[i].equals(string)) return 1;
		}
		return 0;
	}


	public int isCorrectLoginPassword(String string, String string2) {
		String users[]= {"admin","user"};
		String usersPass[]= {"admin","user"};
		for(int i=0;i<users.length;i++) {
			if (users[i].equals(string) && usersPass[i].equals(string2)) return 1;
		}
		return 0;
	}


	public String getLogin() {
		
		return "admin";
	}
}
