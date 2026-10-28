
import com.google.gson.Gson;

public class ConversorJson {

    public static RegistroTelefonia converterParaObjeto(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, RegistroTelefonia.class);
    }
}