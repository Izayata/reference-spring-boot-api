package bar.imagine.demo.service;

public record PasswordResetRequestedEvent(String recipientEmail, String recipientName, String resetUrl) {}
