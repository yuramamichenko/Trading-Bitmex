import View.UserInterface;
import controller.Controller;
import model.*;

public class MainClass {
    private static String key = "R3YheWjE6wURNtSr6dzddj2R";
    private static String secret = "XZNGtPZEdl9PzZsRa-AfSkrItMeLc1Y77Nfx75dyxesb5vgK";
    private static String baseUrl = "https://testnet.bitmex.com";


    public static void main(String[] args) {
        User user = new User(key, secret, baseUrl);
        Controller controller = new Controller(user);

        controller.createLimitOrder(Ticker.XBTUSD, OrderSide.BUY, 30000, 100);

        controller.createMarketOrder(Ticker.XBTUSD, OrderSide.BUY, 100);

        UserInterface.showMessage(controller.getCurrentPnl());

        controller.closePositionByTicker(Ticker.XBTUSD);
    }
}
