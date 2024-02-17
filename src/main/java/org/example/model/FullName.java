package org.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
public class FullName {
    public static final String ID_FIELD = "_id";
    public static final String NAME_FIELD = "name";
    public static final String SURNAME_FIELD = "surname";
    public static final String PATRONYMIC_FIELD = "patronymic";
    @BsonProperty(ID_FIELD)
    private ObjectId id;
    @BsonProperty(NAME_FIELD)
    private String name;
    @BsonProperty(SURNAME_FIELD)
    private String surname;
    @BsonProperty(PATRONYMIC_FIELD)
    private String patronymic;
}
