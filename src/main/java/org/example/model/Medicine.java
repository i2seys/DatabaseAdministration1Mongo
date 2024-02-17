package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medicine {
    public static final String ID_FIELD = "_id";
    public static final String TITLE_FIELD = "title";
    public static final String COUNT_FIELD = "count";
    public static final String PRICE_FIELD = "price";
    public static final String MANUFACTURER_FIELD = "manufacturer";
    public static final String VENDOR_CODE_FIELD = "vendor_code";
    public static final String DATE_OF_MANUFACTURE_FIELD = "date_of_manufacture";
    public static final String PRESCRIPTION_FIELD = "prescription";
    public static final String CATEGORY_ID_FIELD = "category_id";
    @BsonProperty(ID_FIELD)
    private ObjectId id;
    @BsonProperty(TITLE_FIELD)
    private String title;
    @BsonProperty(COUNT_FIELD)
    private int count;
    @BsonProperty(PRICE_FIELD)
    private double price;
    @BsonProperty(MANUFACTURER_FIELD)
    private String manufacturer;
    @BsonProperty(VENDOR_CODE_FIELD)
    private String vendorCode;
    @BsonProperty(DATE_OF_MANUFACTURE_FIELD)
    private LocalDateTime dateOfManufacture;
    @BsonProperty(PRESCRIPTION_FIELD)
    private boolean prescription;
    @BsonProperty(CATEGORY_ID_FIELD)
    private ObjectId categoryId;

}