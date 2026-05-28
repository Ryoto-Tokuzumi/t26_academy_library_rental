package jp.co.metateam.library.service;

import java.util.List;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import jakarta.validation.Validator;

import jp.co.metateam.library.model.RentalManage;
import jp.co.metateam.library.model.RentalManageDto;
import jp.co.metateam.library.model.Stock;
import jp.co.metateam.library.repository.StockRepository;
import jp.co.metateam.library.repository.RentalManageRepository;

@Service
public class RentalManageService {
    private final StockRepository stockRepository;
    private final RentalManageRepository rentalManageRepository;

    @Autowired
    public RentalManageService(StockRepository stockRepository, RentalManageRepository rentalManageRepository,
            Validator validator) {
        this.stockRepository = stockRepository;
        this.rentalManageRepository = rentalManageRepository;

    }

    @Transactional
    public void save(RentalManageDto rentalManageDto, BindingResult result) {
        try {
            // RentalManageDtoからRentalManageへの変換
            RentalManage rentalManage = new RentalManage();

            Integer status = rentalManageDto.getStatus();
            LocalDate expectedRentalOn = rentalManageDto.getExpectedRentalOn();
            LocalDate expectedReturnOn = rentalManageDto.getExpectedReturnOn();
            LocalDate today = LocalDate.now(java.time.ZoneId.of("Asia/Tokyo"));

            if (status == 2 || status == 3) {
                result.rejectValue("status", "Invalid.status", "「貸出ステータス」は「貸出待ち」または「貸出中」で入力してください。");
            }

            if (!result.hasFieldErrors("expectedRentalOn") && !result.hasFieldErrors("expectedReturnOn"))

                // 日付整合性チェック
                if (expectedRentalOn.isAfter(expectedReturnOn)) {
                    result.rejectValue("expectedReturnOn", "Invalid.dateOrder", "「返却予定日」は「貸出予定日」以降の日付を入力してください。");
                }

            if ((!result.hasFieldErrors("status") && !result.hasFieldErrors("expectedRentalOn"))
                    && (!result.hasFieldErrors("expectedReturnOn"))) {

                // ステータス妥当性チェック①
                if (expectedRentalOn.isAfter(today) && status == 1) {
                    result.rejectValue("status", "Invalid.statusValidation", "「貸出予定日」が未来の場合は「貸出ステータス」は「貸出待ち」を選択してください");
                }

                if (expectedRentalOn.isEqual(today) && status == 0) {
                    result.rejectValue("status", "Invalid.statusValidation", "「貸出予定日」が今日の場合は「貸出ステータス」は「貸出中」を選択してください");
                }
            }

            if (result.hasErrors()) {
                return;
            }

            Stock stock = this.stockRepository.findById(rentalManageDto.getStockId()).orElse(null);

            if (stock.getStatus() == 1) {
                result.rejectValue("stockId", "Invalid.unavalable", "その書籍は利用できません。");
            }

            List<RentalManage> rentalManageList = this.rentalManageRepository
                    .findByStockId(rentalManageDto.getStockId());

            if (!rentalManageList.isEmpty()) {
                for (RentalManage existing : rentalManageList) {
                    if (existing.getExpectedReturnOn().isBefore(expectedRentalOn)
                            || existing.getExpectedRentalOn().isAfter(expectedReturnOn)) {

                    } else {
                        result.rejectValue("expectedRentalOn", "Invalid.isPeriodOverlapped", "その書籍は既に予約されています");
                        break;
                    }
                }
            }

            if (result.hasErrors()) {
                return;
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
