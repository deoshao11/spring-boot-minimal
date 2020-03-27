package hello;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class HelloController {

    @GetMapping("/")
    public String getGreeting() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping(value = "accounts/internal/{accountName}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get internal account by account name",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public InternalAccount getQuoteStatus(@PathVariable String accountName) {
        InternalAccount res = new InternalAccount();
        res.setAccountName(accountName);
        res.setExternalAssociation("BINANCE. sub2");
        res.setProductAssociation("BTCUSDT");
        res.setSourceId("BINANCE HYBRID 1");
        res.setType("TRADING");
        return res;
    }

    @GetMapping(value = "accounts/internal/")
    @ApiOperation(value = "Get all internal account ",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<InternalAccount> getQuoteStatus() {
        ArrayList<InternalAccount> resultList = new ArrayList<>();
        InternalAccount res = new InternalAccount();
        res.setAccountName("S9 .BTCUSDT@BINANCE");
        res.setExternalAssociation("BINANCE. sub2");
        res.setProductAssociation("BTCUSDT");
        res.setSourceId("BINANCE HYBRID 1");
        res.setType("TRADING");

        resultList.add(res);

        return resultList;
    }

}

