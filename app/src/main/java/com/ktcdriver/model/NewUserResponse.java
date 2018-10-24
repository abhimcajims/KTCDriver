package com.ktcdriver.model;

import java.util.List;

/**
 * Created by Rakhi on 10/17/2018.
 */
public class NewUserResponse {

    private List<ResponseBean> response;

    public List<ResponseBean> getResponse() {
        return response;
    }

    public void setResponse(List<ResponseBean> response) {
        this.response = response;
    }

    public static class ResponseBean {
        /**
         * response : Success
         */

        private String response;

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }
    }
}
