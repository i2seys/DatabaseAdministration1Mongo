package org.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
public class Client {
    public static final String ID_FIELD = "_id";
    public static final String FULL_NAME_FIELD = "full_name";
    public static final String EMAIL_FIELD = "email";
    public static final String PHONE_FIELD = "phone";
    @BsonProperty(ID_FIELD)
    private ObjectId id;
    @BsonProperty(FULL_NAME_FIELD)
    private FullName fullName;
    @BsonProperty(EMAIL_FIELD)
    private String email;
    @BsonProperty(PHONE_FIELD)
    private String phone;
}
