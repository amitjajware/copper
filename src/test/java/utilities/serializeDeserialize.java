package utilities;

import io.restassured.response.Response;
import org.apache.juneau.html.HtmlSerializer;
import org.apache.juneau.json.JsonParser;
import org.apache.juneau.json.JsonSerializer;
import org.apache.juneau.parser.ParseException;
import org.apache.juneau.serializer.SerializeException;
import org.apache.juneau.xml.XmlSerializer;

import java.math.BigDecimal;
import java.net.URISyntaxException;

public class serializeDeserialize {

    public static BigDecimal bdec = new BigDecimal("50.23");
    public static AssetPOJO assetPOJO = new AssetPOJO(1L,"Copper.co","USD", (short) 2022, bdec);

    public static void pojoToJson()throws SerializeException {
        //POJO to JSON
        JsonSerializer jsonSerializer = JsonSerializer.DEFAULT_READABLE;
        String json = jsonSerializer.serialize(assetPOJO);
        System.out.println(json);

    }

    public static void pojoToXML()throws SerializeException {
        //POJO to JSON
        XmlSerializer xmlSerializer = XmlSerializer.DEFAULT_NS_SQ_READABLE;
        String xml = xmlSerializer.serialize(assetPOJO);
        System.out.println(xml);

    }

    public static void pojoToHTML()throws SerializeException {
        //POJO to JSON
        HtmlSerializer jsonSerializer = HtmlSerializer.DEFAULT_SQ_READABLE;
        String html = jsonSerializer.serialize(assetPOJO);
        System.out.println(html);

    }

   // Deserializer

    public static void jsonToPOJO() throws URISyntaxException, ParseException {

        JsonParser jsonParser = JsonParser.DEFAULT;
        String ResponseValue = String.valueOf(RestAssuredExtension.getRequest("http://localhost:8080/collaterals/"));
        AssetPOJO assetPOJO1 = jsonParser.parse(ResponseValue,AssetPOJO.class);
        System.out.println(assetPOJO1.getId());
        System.out.println(assetPOJO1.getName());
        System.out.println(assetPOJO1.getCurrency());
        System.out.println(assetPOJO1.getYear());
        System.out.println(assetPOJO1.getValue());
    }
}
