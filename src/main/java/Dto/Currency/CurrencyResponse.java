package Dto.Currency;

public class CurrencyResponse {

    private Integer id;
    private String fullName;
    private String code;
    private String sign;

    public CurrencyResponse(Integer id, String fullName, String code, String sign) {
        this.id = id;
        this.fullName = fullName;
        this.code = code;
        this.sign = sign;
    }

    public CurrencyResponse() {
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getCode() {
        return code;
    }

    public String getSign() {
        return sign;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return String.format(
                "{" +
                     "\"id\": %d," +
                     "\"name\": \"%s\"," +
                     "\"code\": \"%s\"," +
                     "\"sign\": \"%s\"" +
                "}",
                id, fullName, code, sign);
    }
}
