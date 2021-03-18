package com.thing.soap;

import com.thing.core.Album;
import com.thing.core.Log;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.Date;
import java.util.List;

@WebService
@SOAPBinding
public interface LogService {
    @WebMethod
    List<Log> getChangeLog(String isrc, String from, String to, String changeType);
    @WebMethod
    boolean clearLogs(Album album) throws MySOAPFault;
}
