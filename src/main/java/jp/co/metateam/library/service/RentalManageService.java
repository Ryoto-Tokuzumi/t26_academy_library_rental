package jp.co.metateam.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Validator;

import jp.co.metateam.library.model.Account;
import jp.co.metateam.library.model.BookMst;
import jp.co.metateam.library.model.BookMstDto;
import jp.co.metateam.library.model.RentalManage;
import jp.co.metateam.library.model.RentalManageDto;
import jp.co.metateam.library.model.Stock;
import jp.co.metateam.library.model.StockDto;
import jp.co.metateam.library.repository.BookMstRepository;
import jp.co.metateam.library.repository.StockRepository;
import jp.co.metateam.library.repository.AccountRepository;
import jp.co.metateam.library.repository.RentalManageRepository;

@Service
public class RentalManageService {
    private final StockRepository stockRepository;
    private final AccountRepository accountRepository;
    private final RentalManageRepository rentalManageRepository;
    private final Validator validator;

    @Autowired
    public RentalManageService(StockRepository stockRepository, AccountRepository accountRepository,
            RentalManageRepository rentalManageRepository, Validator validator) {
        this.stockRepository = stockRepository;
        this.accountRepository = accountRepository;
        this.rentalManageRepository = rentalManageRepository;
        this.validator = validator;
    }

    @Transactional
    public void save(RentalManageDto rentalManageDto) throws Exception {
        try {
            // RentalManageDtoからRentalManageへの変換
            RentalManage rentalManage = new RentalManage();
            Account account = this.accountRepository.findById(rentalManageDto.getEmployeeId()).orElse(null);
            Stock stock = this.stockRepository.findById(rentalManageDto.getStockId()).orElse(null);
            Integer status = rentalManageDto.getStatus();

            if (account == null) {
                throw new Exception("Account record not found.");
            }

            if (stock == null) {
                throw new Exception("Stock record not found.");
            }

            if (status != null && (status == 2 || status == 3)) {
                throw new Exception("Invalid status.");
            }

            rentalManage.setEmployeeId(rentalManageDto.getEmployeeId());
            rentalManage.setExpectedRentalOn(rentalManageDto.getExpectedRentalOn());
            rentalManage.setExpectedReturnOn(rentalManageDto.getExpectedReturnOn());
            rentalManage.setStockId(rentalManageDto.getStockId());
            rentalManage.setStatus(rentalManageDto.getStatus());

            this.rentalManageRepository.save(rentalManage);

        } catch (Exception e) {
            throw e;
        }
    }
}
