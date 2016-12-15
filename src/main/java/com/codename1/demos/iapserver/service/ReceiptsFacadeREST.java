/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.demos.iapserver.service;

import com.codename1.demos.iapserver.Receipts;
import com.codename1.io.Log;
import com.codename1.payment.Receipt;
import com.codename1.payments.IAPValidator;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

/**
 *
 * @author shannah
 */
@Stateless
@Path("com.codename1.demos.iapserver.receipts")
public class ReceiptsFacadeREST extends AbstractFacade<Receipts> {
    
    public static final boolean DISABLE_PLAY_STORE_VALIDATION=true;
    public static final boolean DISABLE_ITUNES_STORE_VALIDATION=true;
    
    public static final String APPLE_SECRET = "73649dadc2234e708f83191f0652faf8";
   
    public static final String GOOGLE_DEVELOPER_API_CLIENT_ID="iapdemo@iapdemo-152500.iam.gserviceaccount.com";
    public static final String GOOGLE_DEVELOPER_PRIVATE_KEY="-----BEGIN PRIVATE KEY-----\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDpJt6MDSeiT2hT\nB3rVmGidBgVzEihsbF2RTZXX14AD1RaA4204ve1x2YS1h0aqM7Gb3TuJ3o3HvWQH\nvDx65pntZUyst/xYNB2Sc45PVolreg1XleSz9d2+KxYu3mz1bwkHBsMIp7IX2u7V\nF5nWgAus609/QytSM2Kc01fMz/Brp+yEZkpx/aafdDyjuUiE1lxmIfYLl/B3ZgKG\n/Yq3glT28KcuujP/TEn17ifMEqi5ZQINwxPe4qtrXRKzMpw5+S7h9plUFwdf/YDK\nLrxZsL5JVreuVu00Z8HkN9Nl2PXH4GSyCbMW9T1GZ3OIxfMQlpqX2862DAluFhON\nIRGhb1OzAgMBAAECggEADRsmP3Et3S3hi2lMOl2K4+jGvaUzqISe7eASoEN46r47\nuZsPNLVYd6Hml9221zJ4tW5GPoXIKoY27UhjjfUQigu7t6nQAZVMZEMiqMsQhn4h\n6d5/MK6NfHcnlBLkxRcsxrxWklH67ORdsJTXDPu9rEa9/UVlVgWVcYNf2B+IQETg\nyzq+4xdAVl5xIfu1cBWVuI2BNcaaEhzn+rfDoWoBtDE21GA9/hVTNGDrRpe5OB0x\nUdst3KKKkESazTZnyW5OZYu2RteKs+FLAnvuXADOoU+SJ9Fo/hLqAHsB7xcQp5og\nhVSgt6WVS/NeduFUuFuAFH79HNoy88Q9TGdurF2XYQKBgQD0m08BN7u0rfTnmqZr\nJoeIafTaje0swIfNIOOjCMUPAdgtsdH/d8tzEyztqiQFYGeP9IxAPCbInojn/lx+\ncEE6GAiZOrI9ULbSld9BG1p0M61DHPqyP7ElnwW9BaVmSff0gOM/XN1b67VdRk/R\nrO+3lFZzwvY16CRJnuTR6dGqpQKBgQD0Avkekn/4OUuqngBIHnIA3aeQj79YGDmC\n+bcRBDyjwTFH5piLXmmt6E4tGjgIQgehauY8/rXTmFo1JH3vGi5g7M5TTzD7TG1k\n1rZEfX3awQJwcN6JYEozhZyn4WH/Uui1Kdfs5dnUdirUyHOoFPlo/Xh1FrY413FM\nP6BgzvItdwKBgQCF5s8k94GFswSyFHKtjXX8MySaz77aFLtCi5KRmSdmw7e3q/Qt\nSULSR9j8cXsuhkuq/lAYidUhVcfUV+YCQSkzyBxM5VjVP+4U8X45gePOYMukRaTn\nFv6+fYMCqqiUikF2U2gTM57pxuqNmQPw4B6J/GYnEL7/W4kh1B2m27yvEQKBgQDs\nT9jNR9nRHkPuZ3gkX//OYNt/wAbyHPvNoWMugqjrehTs6iC/kF7OQlU7jtHHBZN4\nOxvg5FhZJInwP3gK0JMr4QpsWhw4syqaZuo76ECzUwq2tEiASJVz0ikF2NtcNwIR\nOQXZcjyEazBNxBdmJ3HnwNogoy80W0lpK5F5T9HwPwKBgGTnK3gxgLvje6g3UO6p\nLNf8NOd9ZD211fLitgSY57zbOMNm0KljrdaYYdPYZDwfpvUZfpxSbDzOGnHRYeub\npDBJDYRayKBoc9HKjfpQJ5iDVMFNHfHd05w33AiCpXqqbxdakA2M+/0Xb2jwPHhp\nIt/3qNgrgRuCeWXU/h/AQboX\n-----END PRIVATE KEY-----\n";
    
    
    public static final String SKU_10_CREDITS = "iapdemo.credits.10";
    public static final String SKU_NOADS_1MONTH = "iapdemo.noads.month.auto";
    public static final String SKU_NOADS_3MONTH = "iapdemo.noads.3month.auto";
    public static final String SKU_TUTORIALS_1MONTH =  "iapdemo.tutorials.1month";
    /**
     * List of all product skus we have
     */
    static String[] products = new String[] {
        SKU_NOADS_1MONTH,
        SKU_TUTORIALS_1MONTH,
        SKU_NOADS_3MONTH,
        SKU_10_CREDITS,
        "iapdemo.calculator"
        
    };
    
    /**
     * List of product skus for the NO ADs subscription.
     */
    String[] adSkus = new String[] {
        SKU_NOADS_1MONTH,
        SKU_NOADS_3MONTH
    };
    
    /**
     * List of product skus for tutorials section subscription.
     */
    String[] tutorialSkus = new String[]{
        SKU_TUTORIALS_1MONTH
    };
    
    
    @PersistenceContext(unitName = "com.codename1.demos_IAPServer_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Context 
    private ServletContext context;
    
    @Context
    private HttpServletRequest request;
    
    
    public ReceiptsFacadeREST() {
        super(Receipts.class);
    }

    public Principal credentialsWithBasicAuthentication(HttpServletRequest req) {
        String authHeader = req.getHeader("Authorization");
        if (authHeader != null) {
            StringTokenizer st = new StringTokenizer(authHeader);
            if (st.hasMoreTokens()) {
                String basic = st.nextToken();

                if (basic.equalsIgnoreCase("Basic")) {
                    try {
                        String credentials = new String(Base64.getDecoder().decode(st.nextToken()), "UTF-8");
                        //LOG.debug("Credentials: " + credentials);
                        int p = credentials.indexOf(":");
                        if (p != -1) {
                            final String login = credentials.substring(0, p).trim();
                            String password = credentials.substring(p + 1).trim();

                            return new Principal() {

                                @Override
                                public String getName() {
                                    return login;
                                }
                            };
                        } else {
                            //LOG.error("Invalid authentication token");
                        }
                    } catch (UnsupportedEncodingException e) {
                        //LOG.warn("Couldn't retrieve authentication", e);
                    }
                }
            }
        }

        return null;
    }
    
    @POST
    @Consumes({"application/xml", "application/json"})
    public void create(Receipts entity) {
        
        if (entity.getTransactionId() == null || entity.getTransactionId().isEmpty()) {
            entity.setTransactionId("sandbox-"+UUID.randomUUID().toString());
        }
        
        String username = credentialsWithBasicAuthentication(request).getName();
        entity.setUsername(username);
        
        // Save the receipt first in case something goes wrong in the validation stage
        super.create(entity);

        
        // Let's validate the receipt
        validateAndSaveReceipt(entity); // validates the receipt for insert
    }

    
    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Receipts> findAll() {
        String username = credentialsWithBasicAuthentication(request).getName();
        return getEntityManager()
                .createNamedQuery("Receipts.findByUsername")
                .setParameter("username", username)
                .getResultList();
    }
    
    
    
    private static final long ONE_DAY = 24 * 60 * 60 * 1000;
    private static final long ONE_DAY_SANDBOX = 10 * 1000;
    @Schedule(hour="*", minute="*")
    public void validateSubscriptionsCron() {
        
        System.out.println("----------- VALIDATING RECEIPTS ---------");
        List<Receipts> res = null;
        final Set<String> completedTransactionIds = new HashSet<String>();
        for (String storeCode : new String[]{Receipt.STORE_CODE_ITUNES, Receipt.STORE_CODE_PLAY}) {
            if (DISABLE_ITUNES_STORE_VALIDATION && storeCode.equals(Receipt.STORE_CODE_ITUNES)) {
                continue;
            }
            if (DISABLE_PLAY_STORE_VALIDATION && storeCode.equals(Receipt.STORE_CODE_PLAY)) {
                continue;
            }
            while (!(res = getEntityManager().createNamedQuery("Receipts.findNextToValidate")
                    .setParameter("threshold", System.currentTimeMillis() - ONE_DAY_SANDBOX)
                    .setParameter("storeCode", storeCode)
                    .setMaxResults(1)
                    .getResultList()).isEmpty() && 
                    !completedTransactionIds.contains(res.get(0).getTransactionId())) {

                final Receipts curr = res.get(0);
                completedTransactionIds.add(curr.getTransactionId());
                Receipts[] validatedReceipts =  validateAndSaveReceipt(curr);
                em.flush();
                for (Receipts r : validatedReceipts) {
                    completedTransactionIds.add(r.getTransactionId());
                }

            }
        }
    }
    
    
    
    
    private void copy(Receipts target, Receipt source) {
        if (source.getExpiryDate() != null) {
            target.setExpiryDate(source.getExpiryDate().getTime());
        }
        if (source.getCancellationDate() != null) {
            target.setCancellationDate(source.getCancellationDate().getTime());
        }
        if (source.getPurchaseDate() != null) {
            target.setPurchaseDate(source.getPurchaseDate().getTime());
        }
        target.setTransactionId(source.getTransactionId());
        if (source.getOrderData() != null) {
            target.setOrderData(source.getOrderData());
        }
        if (source.getStoreCode() != null) {
            target.setStoreCode(source.getStoreCode());
        }
        target.setSku(source.getSku());
        
    }
    
    /**
     * Gets the "managed" version of the provided receipt from the entity manager.
     * If the provided receipt is managed, then it will be returned directly.  If not
     * the equivalent will be loaded from the database (i.e the receipt with matching
     * transaction id and store code).  If none is found it will return null.
     * @param receipt
     * @return 
     */
    private Receipts getManagedReceipt(Receipts receipt) {
        if (getEntityManager().contains(receipt)) {
            return receipt;
        }
        
        List<Receipts> res = getEntityManager().createNamedQuery("Receipts.findByStoreAndTransactionId")
                .setParameter("transactionId", receipt.getTransactionId())
                .setParameter("storeCode", receipt.getStoreCode())
                .getResultList();
        if (res.isEmpty()) {
            return null;
        }
        return res.get(0);
    }
    
    /**
     * Validates a given receipt, updating the expiry date, 
     * @param receipt The receipt to be validated
     * @param forInsert If true, then an expiry date will be calculated even if there is no validator.
     */
    private Receipts[] validateAndSaveReceipt(Receipts receipt) {
        EntityManager em = getEntityManager();
        Receipts managedReceipt = getManagedReceipt(receipt);
        if (Receipt.STORE_CODE_SIMULATOR.equals(receipt.getStoreCode())) {
            if (receipt.getExpiryDate() == null) {
                //Not inserted yet and no expiry date set yet
                Date dt = calculateExpiryDate(receipt.getSku(), true);
                if (dt != null) {
                    receipt.setExpiryDate(dt.getTime());
                }
                
            } 
            if (managedReceipt == null) {
                em.persist(receipt);
                return new Receipts[]{receipt};
            } else {
                em.merge(managedReceipt);
                return new Receipts[]{managedReceipt};
            }
        } else {
            IAPValidator validator = IAPValidator.getValidatorForPlatform(receipt.getStoreCode());
            if (validator == null) {
                if (receipt.getExpiryDate() == null && managedReceipt == null) {
                    // No expiry date.
                    // Generate one.
                    Date dt = calculateExpiryDate(receipt.getSku(), false);
                    if (dt != null) {
                        receipt.setExpiryDate(dt.getTime());
                    }
                    
                }
                if (managedReceipt == null) {
                    em.persist(receipt);
                    return new Receipts[]{receipt};
                } else {
                    em.merge(managedReceipt);
                    return new Receipts[]{managedReceipt};
                }
                
            }
            if (DISABLE_ITUNES_STORE_VALIDATION && Receipt.STORE_CODE_ITUNES.equals(receipt.getStoreCode())) {
                return new Receipts[]{receipt};
            }
            if (DISABLE_PLAY_STORE_VALIDATION && Receipt.STORE_CODE_PLAY.equals(receipt.getStoreCode())) {
                return new Receipts[]{receipt};
            }
            validator.setAppleSecret(APPLE_SECRET);
            validator.setGoogleClientId(GOOGLE_DEVELOPER_API_CLIENT_ID);
            validator.setGooglePrivateKey(GOOGLE_DEVELOPER_PRIVATE_KEY);
            com.codename1.payment.Receipt r2 = new com.codename1.payment.Receipt();
            r2.setTransactionId(receipt.getTransactionId());
            r2.setOrderData(receipt.getOrderData());
            try {
                Receipt[] result = validator.validate(r2);
                // Depending on the platform, result may contain many receipts or a single receipt
                // matching our receipt.  In the case of iTunes, none of the receipt transaction IDs
                // might match the original receipt's transactionId because the validator
                // will set the transaction ID to the *original* receipt's transaction ID.
                // If none match, then we should remove our receipt, and update each of the returned
                // receipts in the database.
                 Receipt matchingValidatedReceipt = null;
                for (Receipt r3 : result) {
                    if (r3.getTransactionId().equals(receipt.getTransactionId())) {
                        matchingValidatedReceipt = r3;
                        break;
                    }
                }
                
                if (matchingValidatedReceipt == null) {
                    // Since the validator didn't find our receipt,
                    // we should remove the receipt.  The equivalent
                    // is stored under the original receipt's transaction ID
                    if (managedReceipt != null) {
                        em.remove(managedReceipt);
                        managedReceipt = null;
                    }
                }
                List<Receipts> out = new ArrayList<Receipts>();
                // Now go through and 
                for (Receipt r3 : result) {
                    if (r3.getOrderData() == null) {
                        // No order data found in receipt.  Setting it to the original order data 
                        r3.setOrderData(receipt.getOrderData());
                    }
                    Receipts eReceipt = new Receipts();
                    eReceipt.setTransactionId(r3.getTransactionId());
                    eReceipt.setStoreCode(receipt.getStoreCode());
                    Receipts eManagedReceipt = getManagedReceipt(eReceipt);
                    if (eManagedReceipt == null) {
                        copy(eReceipt, r3);
                        eReceipt.setUsername(receipt.getUsername());
                        eReceipt.setLastValidated(System.currentTimeMillis());
                        em.persist(eReceipt);
                        out.add(eReceipt);
                    } else {
                        
                        copy(eManagedReceipt, r3);
                        eManagedReceipt.setUsername(receipt.getUsername());
                        eManagedReceipt.setLastValidated(System.currentTimeMillis());
                        em.merge(eManagedReceipt);
                        out.add(eManagedReceipt);
                    }
                }
                
                return out.toArray(new Receipts[out.size()]);
                
            } catch (Exception ex) {
                // We should probably store some info about the failure in the 
                // database to make it easier to find receipts that aren't validating,
                // but for now we'll just log it.
                Log.p("Failed to validate receipt "+r2);
                Log.p("Reason: "+ex.getMessage());
                Log.e(ex);
                return new Receipts[]{receipt};
                
            }
        }
    }
    
    
    
    private String toString(Receipt r) {
        return "Receipt {storeCode="+r.getStoreCode()+", transactionId="+r.getTransactionId()+", sku="+r.getSku()+", orderData="+r.getOrderData()+", purchaseDate="+r.getPurchaseDate()+", cancellationDate="+r.getCancellationDate()+", expiryDate="+r.getExpiryDate()+"}";
    }
    
    private String toString(Receipt[] receipts) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Receipt r : receipts) {
            if (!first) {
                sb.append(", ");
            }
            first = false;
            sb.append(toString(r));
        }
        sb.append("[");
        return sb.toString();
    }
    
    /*
    Sandbox conversion times https://developer.apple.com/library/content/documentation/LanguagesUtilities/Conceptual/iTunesConnectInAppPurchase_Guide/Chapters/TestingInAppPurchases.html
    1 week = 3 minutes
    1 month = 5 minutes
    2 month = 10 minutes
    3 month = 15 minutes
    6 month = 30 minutes
    1 year = 1 hour
    
    */
    /**
     * 
     * @param sku
     * @param sandbox
     * @return 
     */
    private Date calculateExpiryDate(String sku, boolean sandbox) {
        switch (sku) {
            case SKU_TUTORIALS_1MONTH: {
                Date curr = getExpiryDate(tutorialSkus);
                if (curr == null) {
                    curr = new Date();
                }
                Calendar cal = Calendar.getInstance();
                cal.setTime(curr);
                if (sandbox) {
                    cal.add(Calendar.MINUTE, 5);
                } else {
                    cal.add(Calendar.MONTH, 1);
                }
                return cal.getTime();
            }
            case SKU_NOADS_3MONTH:
            case SKU_NOADS_1MONTH: {
                Date curr = getExpiryDate(adSkus);
                if (curr == null) {
                    curr = new Date();
                }
                Calendar cal = Calendar.getInstance();
                cal.setTime(curr);
                if (sku.equals(SKU_NOADS_1MONTH)) {
                    if (sandbox) {
                        cal.add(Calendar.MINUTE, 5);
                    } else {
                        cal.add(Calendar.MONTH, 1);
                    }
                } else if (sku.equals(SKU_NOADS_3MONTH)) {
                    if (sandbox) {
                        cal.add(Calendar.MINUTE, 15);
                    } else {
                        cal.add(Calendar.MONTH, 3);
                    }
                } else {
                    throw new RuntimeException("This should never happen");
                }
                return cal.getTime();
                
            }
            
            default : {
                return null;
            }
                
                
        }
    }
    
    private Date getExpiryDate(String... skus) {
        List<String> lSkus = Arrays.asList(skus);
        List<Receipts> receipts = findAll();
        Date dt = null;
        for (Receipts r : receipts) {
            if (!lSkus.contains(r.getSku())) {
                continue;
            }
            if (r.getExpiryDate() != null && (dt == null || r.getExpiryDate() > dt.getTime())) {
                dt = new Date(r.getExpiryDate());
            }
        }
        return dt;
    }


    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
