package com.thing.soap;

import com.thing.core.Album;
import com.thing.core.AlbumRepository;
import com.thing.core.Log;
import com.thing.core.RepException;
import com.thing.impl.AlbumRepositoryImpl;

import javax.jws.WebService;
import java.util.List;

@WebService(endpointInterface = "com.thing.soap.LogService")
public class LogServiceImpl implements LogService {
    private static final AlbumRepository albumRepository = new AlbumRepositoryImpl();

    @Override
    public List<Log> getChangeLog(String isrc, String from, String to, String changeType) {
        return albumRepository.getChangeLogs(isrc, new DateParam(from).getDate(), new DateParam(to).getDate(), changeType);
    }

    @Override
    public boolean clearLogs(String isrc) throws MySOAPFault {
        RepException exception = new RepException("Not implemented");
        throw new MySOAPFault("This function is not implemented", exception);
    }
}
