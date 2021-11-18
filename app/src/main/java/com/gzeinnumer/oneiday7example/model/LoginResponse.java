package com.gzeinnumer.oneiday7example.model;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("data")
    private Data data;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;

    @SerializedName("token")
    private String token;

    public Data getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public boolean isStatus() {
        return status;
    }

    public String getToken() {
        return token;
    }

    //	@Override
// 	public String toString(){
//		return
//			"LoginResponse{" +
//			"data = '" + data + '\'' +
//			",message = '" + message + '\'' +
//			",status = '" + status + '\'' +
//			",token = '" + token + '\'' +
//			"}";
//		}
    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this, LoginResponse.class);
    }
}