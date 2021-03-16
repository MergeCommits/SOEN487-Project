package com.thing.soap;

import javax.xml.ws.WebFault;

@WebFault(name="MySOAPFault", targetNamespace="http://www.example.com")
public class MySOAPFault extends Exception {
    public MySOAPFault(String message) {
        super(message);
    }
    public MySOAPFault(String message, Throwable cause) {
        super(message, cause);
    }
}
