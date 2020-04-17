package com.spring.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "Payload for requesting a new transfer")
public class RequestTransferInput {
    @ApiModelProperty(required = true)
    @NotNull
    private String fromAccountName;

    @ApiModelProperty(required = true)
    @NotNull
    private String toAccountName;

    @ApiModelProperty(required = true)
    @NotNull
    private String instrument;

    @ApiModelProperty(required = true)
    @NotNull
    private String qty;
}
