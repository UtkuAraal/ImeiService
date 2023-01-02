package com.hub.imei.core;

public interface EmailService {
    public void sendPasswordResetEmail(String toEmail, String verifyToken);
}
