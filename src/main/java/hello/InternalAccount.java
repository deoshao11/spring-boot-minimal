package hello;

import lombok.Data;

import java.util.List;

@Data
public class InternalAccount {
    String accountName;

    String sourceId;

    String type;

    List<String> children;

    String productAssociation;

    String externalAssociation;

}
