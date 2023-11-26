package controller;

import View.UserInterface;
import model.*;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Controller {
    private User user;

    public Controller(User user) {
        this.user = user;
    }

    public void createMarketOrder(Ticker symbol, OrderSide side, int quantity) {
        RequestData data = new RequestData();
        data.addValues(Parameters.symbol, String.valueOf(symbol));
        data.addValues(Parameters.side, String.valueOf(side));
        data.addValues(Parameters.orderQty, String.valueOf(quantity));
        long expires = Signature.createExpires();
        try {
            String signPost = Signature.generateSignature(user.getSecret(), String.valueOf(RequestType.POST), EndPoints.ORDER.path, expires, String.valueOf(data));
            HttpRequest requestToBuy = HttpRequest.newBuilder()
                    .uri(URI.create(user.getBaseUrl() + EndPoints.ORDER))
                    .header(RequestHeaders.CONTENT.toString(), (RequestHeaders.JSON.toString()))
                    .header((RequestHeaders.KEY.toString()), user.getKey())
                    .header((RequestHeaders.EXPIRES.toString()), String.valueOf(expires))
                    .header((RequestHeaders.SIGNATURE.toString()), signPost)
                    .POST(HttpRequest.BodyPublishers.ofString(String.valueOf(data)))
                    .build();
            HttpResponse<String> response = user.getClient().send(requestToBuy, HttpResponse.BodyHandlers.ofString());

            //UserInterface.showMessage(String.valueOf(response));
        } catch (Exception e) {
            UserInterface.showMessage(e.getMessage());
        }
    }

    public void createLimitOrder(Ticker symbol, OrderSide side, int price, int quantity) {
        RequestData data = new RequestData();
        data.addValues(Parameters.symbol, String.valueOf(symbol));
        data.addValues(Parameters.side, String.valueOf(side));
        data.addValues(Parameters.ordType, "Limit");
        data.addValues(Parameters.price, String.valueOf(price));
        data.addValues(Parameters.orderQty, String.valueOf(quantity));
        long expires = Signature.createExpires();
        try {
            String signPost = Signature.generateSignature(user.getSecret(), String.valueOf(RequestType.POST), EndPoints.ORDER.path, expires, String.valueOf(data));
            HttpRequest requestToBuy = HttpRequest.newBuilder()
                    .uri(URI.create(user.getBaseUrl() + EndPoints.ORDER))
                    .header(RequestHeaders.CONTENT.toString(), (RequestHeaders.JSON.toString()))
                    .header((RequestHeaders.KEY.toString()), user.getKey())
                    .header((RequestHeaders.EXPIRES.toString()), String.valueOf(expires))
                    .header((RequestHeaders.SIGNATURE.toString()), signPost)
                    .POST(HttpRequest.BodyPublishers.ofString(String.valueOf(data)))
                    .build();
            HttpResponse<String> response = user.getClient().send(requestToBuy, HttpResponse.BodyHandlers.ofString());

            //UserInterface.showMessage(String.valueOf(response));

            String orderId = parseInfo(response, "orderID");
            user.limitOrders.put(orderId, data.toString());
        } catch (Exception e) {
            UserInterface.showMessage(e.getMessage());
        }
    }

    public void closePositionByTicker(Ticker ticker) {
        RequestData data = new RequestData();
        data.addValues(Parameters.symbol, String.valueOf(ticker));
        data.addValues(Parameters.execInst, "Close");

        long expires = Signature.createExpires();
        try {
            String signature = Signature.generateSignature(user.getSecret(), String.valueOf(RequestType.POST), EndPoints.ORDER.path, expires, String.valueOf(data));
            HttpRequest requestToClose = HttpRequest.newBuilder()
                    .uri(URI.create(user.getBaseUrl() + EndPoints.ORDER))
                    .header(RequestHeaders.CONTENT.toString(), (RequestHeaders.JSON.toString()))
                    .header((RequestHeaders.KEY.toString()), user.getKey())
                    .header((RequestHeaders.EXPIRES.toString()), String.valueOf(expires))
                    .header((RequestHeaders.SIGNATURE.toString()), signature)
                    .POST(HttpRequest.BodyPublishers.ofString(String.valueOf(data)))
                    .build();
            HttpResponse<String> response = user.getClient().send(requestToClose, HttpResponse.BodyHandlers.ofString());

            //UserInterface.showMessage(String.valueOf(response));
        } catch (Exception e) {
            UserInterface.showMessage(e.getMessage());
        }
    }

    public void closeOrderByID(String id) {

    }

    public void canselAllOrders() {
    }

    public String getCurrentPnl() {
        String data = "";
        long expires = Signature.createExpires();
        try {
            String signature = Signature.generateSignature(user.getSecret(), String.valueOf(RequestType.GET), EndPoints.POSITION.path, expires, data);
            HttpRequest requestToGetPosition = HttpRequest.newBuilder()
                    .uri(URI.create(user.getBaseUrl() + EndPoints.POSITION))
                    .header(RequestHeaders.CONTENT.toString(), (RequestHeaders.JSON.toString()))
                    .header((RequestHeaders.KEY.toString()), user.getKey())
                    .header((RequestHeaders.EXPIRES.toString()), String.valueOf(expires))
                    .header((RequestHeaders.SIGNATURE.toString()), signature)
                    .GET()
                    .build();
            HttpResponse<String> response = user.getClient().send(requestToGetPosition, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return parseInfo(response, "unrealisedPnl");
            }
        } catch (Exception e) {
            UserInterface.showMessage(e.getMessage());
        }
        return "you have no positions";
    }

    private String parseInfo(HttpResponse response, String key) {
        String info = "";
        String[] strings = response.body().toString().split(",");
        for (String s : strings) {
            s = s.replaceAll("[[{}\"]]", "");
            String[] parts = s.split(":");
            if (parts[0].toLowerCase().equals(key.toLowerCase())) {
                info = parts[1];
            }
        }
        return info;
    }
}
