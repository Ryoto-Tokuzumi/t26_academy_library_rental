package jp.co.metateam.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

import jp.co.metateam.library.service.AccountService;
import jp.co.metateam.library.service.StockService;
import jp.co.metateam.library.values.RentalStatus;
import jp.co.metateam.library.service.RentalManageService;
import jp.co.metateam.library.model.RentalManageDto;
import jp.co.metateam.library.model.Stock;
import jp.co.metateam.library.model.Account;
import lombok.extern.log4j.Log4j2;
import java.util.List;

/**
 * 貸出管理関連クラスß
 */

@Log4j2
@Controller
public class RentalManageController {

    /**
     * 貸出一覧画面初期表示
     * @param model
     * @return
     */
    private final RentalManageService rentalManageService;
    private final StockService stockService;
    private final AccountService accountService;

    @Autowired
    public RentalManageController(StockService stockService, AccountService accountService,
            RentalManageService rentalManageService) {
        this.stockService = stockService;
        this.accountService = accountService;
        this.rentalManageService = rentalManageService;
    }

    @GetMapping("/rental/index")
    public String index(Model model) {
        // 貸出管理テーブルから全件取得

        // 貸出一覧画面に渡すデータをmodelに追加

        // 貸出一覧画面に遷移
        return "/rental/index";
    }

    @GetMapping("/rental/add")
    public String add(Model model) {
        List<Stock> stockList = this.stockService.findAll();
        List<Account> accounts = this.accountService.findAll();

        model.addAttribute("rentalManageDto", new RentalManageDto());
        model.addAttribute("stockList", stockList);
        model.addAttribute("accounts", accounts);
        model.addAttribute("rentalStatus", RentalStatus.values());

        return "/rental/add";
    }

    @PostMapping("/rental/add")
    public String register(@Valid @ModelAttribute("rentalManageDto") RentalManageDto rentalManageDto,
            BindingResult result,
            Model model) {
        try {
            rentalManageService.save(rentalManageDto, result);

            if (result.hasErrors()) {
                throw new Exception("Validation error.");
            }

            return "redirect:/rental/index";

        } catch (Exception e) {
            log.error(e.getMessage());

            model.addAttribute("rentalManageDto", rentalManageDto);
            model.addAttribute("org.springframework.validation.BindingResult:rentalManageDto", result);

            List<Stock> stockList = this.stockService.findAll();
            List<Account> accounts = this.accountService.findAll();
            model.addAttribute("stockList", stockList);
            model.addAttribute("accounts", accounts);
            model.addAttribute("rentalStatus", RentalStatus.values());

            return "/rental/add";
        }
    }
}
