package com.codedstreams.tradeproducer.util;


import com.codedstreams.cryptorisk.contracts.schemas.CryptoTrade;
import com.codedstreams.cryptorisk.contracts.schemas.RiskAlert;
import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AvroSerialization {
    private static final Logger log = LoggerFactory.getLogger(AvroSerialization.class);

    // Trade Serialization
    public static byte[] serializeTrade(CryptoTrade trade) throws IOException {
        return serializeAvro(trade, CryptoTrade.class);
    }

    public static CryptoTrade deserializeTrade(byte[] data) throws IOException {
        return deserializeAvro(data, CryptoTrade.class);
    }

    // Alert Serialization
    public static byte[] serializeAlert(RiskAlert alert) throws IOException {
        return serializeAvro(alert, RiskAlert.class);
    }

    public static RiskAlert deserializeAlert(byte[] data) throws IOException {
        return deserializeAvro(data, RiskAlert.class);
    }

    // Generic Avro serialization
    private static <T> byte[] serializeAvro(T avroObject, Class<T> clazz) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            DatumWriter<T> writer = new SpecificDatumWriter<>(clazz);
            BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(outputStream, null);

            writer.write(avroObject, encoder);
            encoder.flush();

            return outputStream.toByteArray();
        }
    }

    private static <T> T deserializeAvro(byte[] data, Class<T> clazz) throws IOException {
        DatumReader<T> reader = new SpecificDatumReader<>(clazz);
        BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(data, null);

        return reader.read(null, decoder);
    }
}
