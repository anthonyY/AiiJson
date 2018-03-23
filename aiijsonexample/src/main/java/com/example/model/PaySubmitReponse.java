package com.example.model;

import com.aiitec.openapi.json.annotation.JSONField;

/**
 * @author Anthony
 * @version 1.0
 *          createTime 2018/3/15.
 */

public class PaySubmitReponse extends Entity {

    @JSONField(name = "q")
    PaySubmitResponseQuery query ;

    public PaySubmitResponseQuery getQuery() {
        return query;
    }

    public void setQuery(PaySubmitResponseQuery query) {
        this.query = query;
    }
}
