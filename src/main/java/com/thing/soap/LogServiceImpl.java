package com.thing.soap;

import javax.jws.WebService;

@WebService(endpointInterface = "com.thing.soap.LogService")
public class LogServiceImpl implements LogService {

    @Override
    public String binaryToHex(byte[] data) throws MySOAPFault {
        String converted = HexConverter.toHex(data);

        if (converted.length() < 1) {
            throw new MySOAPFault("Invalid binary data provided.");
        }

        return HexConverter.toHex(data);
    }

    @Override
    public byte[] hexToBinary(String data) throws MySOAPFault {
        byte[] converted = HexConverter.fromHex(data);

        if (converted == null) {
            throw new MySOAPFault("Invalid hex provided.");
        }

        return converted;
    }
}
