package com.thing.soap;

import com.thing.core.Album;
import com.thing.core.AlbumRepository;
import com.thing.core.Log;
import com.thing.core.RepException;
import com.thing.impl.AlbumRepositoryImpl;

import javax.jws.WebService;
import java.util.Date;
import java.util.List;

@WebService(endpointInterface = "com.thing.soap.LogService")
public class LogServiceImpl implements LogService {
    private static final AlbumRepository albumRepository = new AlbumRepositoryImpl();

    @Override
    public List<Log> getChangeLog(String isrc, Date from, Date to, String changeType) {
        return albumRepository.getChangeLogs(isrc, from, to, changeType);
    }

    @Override
    public boolean clearLogs(Album album) throws MySOAPFault {
        RepException exception = new RepException("Not implemented");
        throw new MySOAPFault("There is an error", exception);
    }
}
