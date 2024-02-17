package org.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Employee {
    public static final String ID_FIELD = "_id";
    public static final String FULL_NAME_FIELD = "full_name";
    public static final String EMAIL_FIELD = "email";
    public static final String PHONE_NUMBER_FIELD = "phone_number";
    public static final String BIRTH_DATE_FIELD = "birth_date";
    public static final String SALARY_FIELD = "salary";
    public static final String HIRE_DATE_FIELD = "hire_date";
    public static final String POSITION_ID_FIELD = "position_id";
    @BsonProperty(ID_FIELD)
    private ObjectId id;
    @BsonProperty(FULL_NAME_FIELD)
    private FullName fullName;
    @BsonProperty(EMAIL_FIELD)
    private String email;
    @BsonProperty(PHONE_NUMBER_FIELD)
    private String phoneNumber;
    @BsonProperty(BIRTH_DATE_FIELD)
    private LocalDateTime birthDate;
    @BsonProperty(SALARY_FIELD)
    private double salary;
    @BsonProperty(HIRE_DATE_FIELD)
    private LocalDateTime hireDate;
    @BsonProperty(POSITION_ID_FIELD)
    private ObjectId positionId;

}
