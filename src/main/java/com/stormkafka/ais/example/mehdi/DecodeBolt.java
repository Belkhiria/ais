/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stormkafka.ais.example.mehdi;

import dk.tbsalling.aismessages.AISInputStreamReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.storm.shade.org.apache.commons.lang.StringUtils;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

/**
 *
 * @author Belkhiria
 */
public class DecodeBolt extends BaseBasicBolt {

    	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// here we declare we will be emitting tuples with
		// a single field called "sentence"
		declarer.declare(new Fields("mmsi", "speed", "destination"));
	}

    public void execute(Tuple input, BasicOutputCollector boc) {
                try {
                    String word = input.getString(0);
                    if (StringUtils.isBlank(word)) {
                        // ignore blank lines
                        return;
                    }
                    InputStream inputStream = new ByteArrayInputStream(word.getBytes());
                    AISInputStreamReader streamReader = new AISInputStreamReader(inputStream, aisMessage
                            -> {
                        //System.out.println(new Values(aisMessage.getSourceMmsi().getMMSI(), aisMessage.dataFields().get("speedOverGround"), aisMessage.dataFields().get("destination")));
                        boc.emit(new Values(aisMessage.getSourceMmsi().getMMSI(), aisMessage.dataFields().get("speedOverGround"), aisMessage.dataFields().get("destination")));
                    }
                    );
                    streamReader.run();
                } catch (IOException ex) {
                    //Logger.getLogger(DecodeBolt.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
}
