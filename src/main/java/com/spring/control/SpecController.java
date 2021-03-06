package com.spring.control;

import com.spring.model.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Cipher;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
public class SpecController {

    int start = 0;
    int end = 0;

    @Autowired
    Cipher cipher;

    @GetMapping("/")
    public String getGreeting() {
        return "Greetings from Spring Boot!";
    }

    @PostMapping(value= "transfer", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get transfer information. If ID is not specified, return all available transfers.",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Transfer createTransfer(@RequestBody @Validated RequestTransferInput request) {
        Transfer result = new Transfer();
        result.setId(BigInteger.valueOf(1234567));
        result.setStatus("success");
        result.setMsg("");
        return result;
    }

    @GetMapping(value = "transfer", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get transfer information. If ID is not specified, return all available transfers.",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Transfer> getTransfer(@RequestParam(value = "startTime", required = true) BigInteger startTime,
                                      @RequestParam(value = "endTime", required = true) BigInteger endTime) {
        ArrayList<Transfer> resultList = new ArrayList<>();
        Transfer one = new Transfer();
        one.setId(BigInteger.valueOf(1234567));
        one.setStatus("success");
        one.setMsg("");
        one.setTime(startTime);
        resultList.add(one);
        Transfer two = new Transfer();
        two.setId(BigInteger.valueOf(567898));
        two.setStatus("failed");
        two.setMsg("invalid parameter");
        two.setTime(endTime);
        resultList.add(two);
        return resultList;
    }

    @GetMapping(value = "accounts/internal", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get internal account information. If account name is not specified, return all available accounts.",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<InternalAccount> getInternalAccount(@RequestParam(value = "accountName", required = false) String accountName,
                                                    @RequestHeader(value = "x-encrypted-username", required = false) String encryptedUserName) {
        ArrayList<InternalAccount> resultList = new ArrayList<>();

        setNumbers(decryptUserName(encryptedUserName));

        if(accountName == null) {
            for (int i = start; i < end; i++) {
                resultList.add(createInternalAccount(String.format("Internal Account %d", i),false));
                resultList.add(createInternalAccount(String.format("Internal Account %d Child", i), true));
            }
        } else {
            resultList.add(createInternalAccount(accountName, false));
        }
        return resultList;
    }

    @GetMapping(value = "accounts/external", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get external account information. If account name is not specified, return all available accounts.",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ExternalAccount> getExternalAccount(@RequestParam(value = "accountName", required = false) String accountName,
                                                    @RequestHeader(value = "x-encrypted-username", required = false) String encryptedUserName) {
        ArrayList<ExternalAccount> resultList = new ArrayList<>();

        setNumbers(decryptUserName(encryptedUserName));

        if(accountName == null) {
            for(int i=start; i < end; i++) {
                resultList.add(createExternalAccount(String.format("External account %d", i)));
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
                                                  @RequestHeader(value = "x-encrypted-username", required = false) String encryptedUserName)
    {
        ArrayList<AccountBalance> resultList = new ArrayList<>();

        setNumbers(decryptUserName(encryptedUserName));

        if(accountName == null) {
            for(int i=start; i < end; i++) {
                resultList.add(createAccountBalance(String.format("Internal Account %d", i)));
                resultList.add(createAccountBalance(String.format("Internal Account %d Child", i)));
                resultList.add(createAccountBalance(String.format("External Account %d", i)));
            }
        } else {
            resultList.add(createAccountBalance(accountName));
        }
        return resultList;

    }

    private void setNumbers(String userName) {
        start = 0;
        end = 0;

        if(userName == null) return;
        else if (userName.equals("lonk.admin")) {
            start = 0;
            end = 10;
        } else if (userName.equals("lonk.user1")) {
            start = 0;
            end = 5;
        } else if (userName.equals("lonk.user2")) {
            start = 5;
            end = 10;
        }
    }

    private InternalAccount createInternalAccount(String accountName, Boolean isChildAccount) {
        InternalAccount res = new InternalAccount();
        res.setAccountName(accountName);
        res.setChildren(new ArrayList<>());
        res.setExternalAccountAssociation("BINANCE. sub2");
        res.setProductAssociation("BTCUSDT");
        res.setSourceId("BINANCE HYBRID 1");
        res.setType("TRADING");
        if(isChildAccount) {
            res.setChildren(new ArrayList<>());
            res.setHasParents(true);
        } else {
            res.setChildren(Arrays.asList(accountName+" Child"));
            res.setHasParents(false);
        }
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

    private String decryptUserName(String userName) {
        try {
            byte[] encrypted_decoded_bytes = Base64.getDecoder().decode(userName);
            return new String(cipher.doFinal(encrypted_decoded_bytes), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "";
        }
    }

}
