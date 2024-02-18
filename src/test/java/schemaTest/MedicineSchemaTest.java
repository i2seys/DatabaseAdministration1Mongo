package schemaTest;

import org.example.db.DrugStoreMongoConnector;
import org.example.model.Medicine;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MedicineSchemaTest implements SchemaTest {
    private static DrugStoreMongoConnector conn;

    @BeforeAll
    public static void init() {
        conn = DrugStoreMongoConnector.getInstance();
    }
    @AfterAll
    public static void close() {
        conn.close();
    }

    @Test
    public void schemaTest() {
        var fields = Set.of(
            Medicine.ID_FIELD,
            Medicine.TITLE_FIELD,
            Medicine.COUNT_FIELD,
            Medicine.PRICE_FIELD,
            Medicine.MANUFACTURER_FIELD,
            Medicine.VENDOR_CODE_FIELD,
            Medicine.DATE_OF_MANUFACTURE_FIELD,
            Medicine.PRESCRIPTION_FIELD,
            Medicine.CATEGORY_ID_FIELD);

        //ignore zero medicines count for now ¯\_(ツ)_/¯
        var dbFields = conn.medicinesDoc()
                .find()
                .into(new ArrayList<>())
                .getFirst();

        assertEquals(dbFields.keySet(), fields);
    }
}
