import javax.json.JsonObject;

public interface Jsonable {
    public String toJsonString();
    public JsonObject toJsonObject();
}
