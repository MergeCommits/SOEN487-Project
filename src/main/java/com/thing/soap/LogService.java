package com.thing.soap;

import com.thing.core.Log;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;

@WebService
@SOAPBinding
public interface LogService {
    @WebMethod
    String binaryToHex(byte[] data) throws MySOAPFault;
    @WebMethod
    byte[] hexToBinary(String data) throws MySOAPFault;
}
