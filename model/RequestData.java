package model;

public class RequestData {
    private StringBuilder data = new StringBuilder();

    public void addValues(Parameters p, String s) {
        if (data.toString().length() < 1) {
            data.append("{");
        } else {
            data.append(",");
        }
        data.append("\"")
                .append(p)
                .append("\"")
                .append(": ")
                .append("\"")
                .append(s)
                .append("\"");
    }

    @Override
    public String toString() {
        if (data.toString().charAt(data.length() - 1) != '}') {
            data.append("}");
        }
        return data.toString();
    }
}
