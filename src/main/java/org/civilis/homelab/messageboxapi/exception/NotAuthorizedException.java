package org.civilis.homelab.messageboxapi.exception;

public class NotAuthorizedException extends RuntimeException {
    public NotAuthorizedException() {
        super("not authorized");
    }
}
