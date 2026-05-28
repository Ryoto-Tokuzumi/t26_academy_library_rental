package jp.co.metateam.library.model;

import java.sql.Timestamp;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * 貸出登録
 */
@Entity
@Table(name = "rental_manage")
public class RentalManage {

    /** 貸出管理番号 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 貸出ステータス */
    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "employee_id", nullable = false)
    private String employeeId;

    @Column(name = "stock_id", nullable = false)
    private String stockId;

    /** 貸出予定日 */
    @Column(name = "expected_rental_on", nullable = false)
    private LocalDate expected_rental_on;

    /** 貸出予定日 */
    @Column(name = "expected_return_on", nullable = false)
    private LocalDate expected_return_on;

    /** 貸出日時 */
    @Column(name = "rentaled_at")
    private Timestamp rentaled_at;

    /** 返却日時 */
    @Column(name = "returned_at")
    private Timestamp returned_at;

    /** キャンセル日時 */
    @Column(name = "canceled_at")
    private Timestamp canceled_at;

    /** Getters */

    public Long getId() {
        return id;
    }

    public Integer getStatus() {
        return status;
    }

    public LocalDate getExpectedRentalOn() {
        return expected_rental_on;
    }

    public LocalDate getExpectedReturnOn() {
        return expected_return_on;
    }

    public Timestamp getRentaledAt() {
        return rentaled_at;
    }

    public Timestamp getReturnedAt() {
        return returned_at;
    }

    public Timestamp getCanceledAt() {
        return canceled_at;
    }

    public String getStockId() {
        return stockId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setExpectedRentalOn(LocalDate expected_rental_on) {
        this.expected_rental_on = expected_rental_on;
    }

    public void setExpectedReturnOn(LocalDate expected_return_on) {
        this.expected_return_on = expected_return_on;
    }

    public void setRentaledAt(Timestamp rentaled_at) {
        this.rentaled_at = rentaled_at;
    }

    public void setReturnedAt(Timestamp returned_at) {
        this.returned_at = returned_at;
    }

    public void setCanceledAt(Timestamp canceled_at) {
        this.canceled_at = canceled_at;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

}