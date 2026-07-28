
import com.jeelowcode.tool.framework.common.constant.AppNameConstant;
import com.jeelowcode.tool.framework.config.MyApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class CoinApplication {

    public static void main(String[] args) {
        MyApplication.run(AppNameConstant.COIN_APP, CoinApplication.class, args);
    }

}
