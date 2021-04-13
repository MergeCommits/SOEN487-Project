package com.thing.soap;

import javax.xml.ws.WebFault;

@WebFault(name="MySOAPFault", targetNamespace="com.thing.soap.LogService")
public class MySOAPFault extends Exception {
    public MySOAPFault(String message) {
        super(message);
    }
}
