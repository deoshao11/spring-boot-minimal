package com.spring.control;

import com.spring.model.AccountBalance;
import com.spring.model.ExternalAccount;
import com.spring.model.InternalAccount;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class SpecController {

    int start = 0;
    int end = 0;

    @GetMapping("/")
    public String getGreeting() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping(value = "accounts/internal", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get internal account information. If account name is not specified, return all available accounts.",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<InternalAccount> getInternalAccount(@RequestParam(value = "accountName", required = false) String accountName,
                                                    @RequestHeader(value = "x-username", required = false) String userName) {
        ArrayList<InternalAccount> resultList = new ArrayList<>();

        setNumbers(userName);

        if(accountName == null) {
            for (int i = start; i < end; i++) {
                resultList.add(createInternalAccount(String.format("S%d .BTCUSDT@BINANCE", i)));
            }
        } else {
            resultList.add(createInternalAccount(accountName));
        }
        return resultList;
    }

    @GetMapping(value = "accounts/external", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get external account information. If account name is not specified, return all available accounts.",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ExternalAccount> getExternalAccount(@RequestParam(value = "accountName", required = false) String accountName,
                                                    @RequestHeader(value = "x-username", required = false) String userName) {
        ArrayList<ExternalAccount> resultList = new ArrayList<>();

        setNumbers(userName);

        if(accountName == null) {
            for(int i=0; i < 10; i++) {
                resultList.add(createExternalAccount(String.format("BINANCE. sub%d . margin", i)));
            }
        } else {
            resultList.add(createExternalAccount(accountName));
        }
        return resultList;
    }

    @GetMapping(value = "account_balance", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get account balance. If account name is not specified, return all available accounts.",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AccountBalance> getAccountBalance(@RequestParam(value = "accountName", required = false) String accountName,
                                                  @RequestParam(value = "instrument", required = false) String instrument,
                                                  @RequestHeader(value = "x-username", required = false) String userName)
    {
        ArrayList<AccountBalance> resultList = new ArrayList<>();

        setNumbers(userName);

        if(accountName == null) {
            for(int i=0; i < 10; i++) {
                resultList.add(createAccountBalance(String.format("S%d .BTCUSDT@BINANCE", i)));
            }
        } else {
            resultList.add(createAccountBalance(accountName));
        }
        return resultList;

    }

    private void setNumbers(String userName) {
        start = 0;
        end = 0;
        if (userName == "lonk.admin") {
            start = 0;
            end = 10;
        } else if (userName == "lonk.user1") {
            start = 0;
            end = 5;
        } else if (userName == "lonk.user2") {
            start = 5;
            end = 10;
        }
    }

    private InternalAccount createInternalAccount(String accountName) {
        InternalAccount res = new InternalAccount();
        res.setAccountName(accountName);
        res.setChildren(new ArrayList<>());
        res.setExternalAccountAssociation("BINANCE. sub2");
        res.setProductAssociation("BTCUSDT");
        res.setSourceId("BINANCE HYBRID 1");
        res.setType("TRADING");
        return res;
    }

    private ExternalAccount createExternalAccount(String accountName) {
        ExternalAccount res = new ExternalAccount();
        res.setAccountName(accountName);
        res.setType("EXCHANGE");
        res.setExchangeAssociation("BINANCE");
        return res;
    }

    private AccountBalance createAccountBalance(String accountName) {
        AccountBalance res = new AccountBalance();
        res.setAccountName(accountName);
        res.setInstruments(Map.of("USD", new BigDecimal(100.0), "BNB", new BigDecimal(9.99)));
        res.setType("TRADING");
        return res;
    }

}
