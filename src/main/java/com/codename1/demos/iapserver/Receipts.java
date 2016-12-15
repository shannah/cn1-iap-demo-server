/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.demos.iapserver;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author shannah
 */
@Entity
@Table(name = "RECEIPTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Receipts.findAll", query = "SELECT r FROM Receipts r"),
    @NamedQuery(name = "Receipts.findByTransactionId", query = "SELECT r FROM Receipts r WHERE r.transactionId = :transactionId"),
    @NamedQuery(name = "Receipts.findByStoreAndTransactionId", query = "SELECT r FROM Receipts r WHERE r.transactionId = :transactionId AND r.storeCode = :storeCode"),
    @NamedQuery(name = "Receipts.findByUsername", query = "SELECT r FROM Receipts r WHERE r.username = :username"),
    @NamedQuery(name = "Receipts.findBySku", query = "SELECT r FROM Receipts r WHERE r.sku = :sku"),
    @NamedQuery(name = "Receipts.findByPurchaseDate", query = "SELECT r FROM Receipts r WHERE r.purchaseDate = :purchaseDate"),
    @NamedQuery(name = "Receipts.findByExpiryDate", query = "SELECT r FROM Receipts r WHERE r.expiryDate = :expiryDate"),
    @NamedQuery(name = "Receipts.findByCancellationDate", query = "SELECT r FROM Receipts r WHERE r.cancellationDate = :cancellationDate"),
    @NamedQuery(name = "Receipts.findByOrderData", query = "SELECT r FROM Receipts r WHERE r.orderData = :orderData"),
    @NamedQuery(name = "Receipts.findNextToValidate", query = "SELECT r FROM Receipts r WHERE r.storeCode = :storeCode AND "
            + "         (r.lastValidated IS NULL OR r.lastValidated < :threshold) AND "
            + "         (r.lastValidated <= r.expiryDate OR r.lastValidated IS NULL OR r.expiryDate IS NULL) AND "
            + "         NOT (r.expiryDate IS NULL AND r.lastValidated IS NOT NULL) ORDER BY r.lastValidated")
})
public class Receipts implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "TRANSACTION_ID")
    private String transactionId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "USERNAME")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "SKU")
    private String sku;
    @Column(name = "PURCHASE_DATE")
    private Long purchaseDate;
    @Column(name = "EXPIRY_DATE")
    private Long expiryDate;
    @Column(name = "CANCELLATION_DATE")
    private Long cancellationDate;
    @Size(max = 20)
    @Column(name = "STORE_CODE")
    private String storeCode;
    @Size(max = 32000)
    @Column(name = "ORDER_DATA")
    private String orderData;
    
    @Column(name = "LAST_VALIDATED")
    private Long lastValidated;

    public Receipts() {
    }

    public Receipts(String transactionId) {
        this.transactionId = transactionId;
    }

    public Receipts(String transactionId, String username, String sku) {
        this.transactionId = transactionId;
        this.username = username;
        this.sku = sku;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Long getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Long purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Long getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Long expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Long getCancellationDate() {
        return cancellationDate;
    }

    public void setCancellationDate(Long cancellationDate) {
        this.cancellationDate = cancellationDate;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getOrderData() {
        return orderData;
    }

    public void setOrderData(String orderData) {
        this.orderData = orderData;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transactionId != null ? transactionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Receipts)) {
            return false;
        }
        Receipts other = (Receipts) object;
        if ((this.transactionId == null && other.transactionId != null) || (this.transactionId != null && !this.transactionId.equals(other.transactionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.codename1.demos.iapserver.Receipts[ transactionId=" + transactionId + " ]";
    }

    /**
     * @return the lastValidated
     */
    public Long getLastValidated() {
        return lastValidated;
    }

    /**
     * @param lastValidated the lastValidated to set
     */
    public void setLastValidated(Long lastValidated) {
        this.lastValidated = lastValidated;
    }
    
}
