/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stormkafka.ais.example.mehdi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class AverageSpeedBolt extends BaseBasicBolt {

    Map<Integer, List<Float>> shipsSpeed = new HashMap<Integer, List<Float>>();

    @Override
    public void declareOutputFields(OutputFieldsDeclarer ofd) {
        ofd.declare(new Fields("mmsi", "averageSpeed"));

    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector boc) {
        Integer mmsi = tuple.getIntegerByField("mmsi");
        Float speed = tuple.getFloatByField("speed");
        List<Float> listSpeed = shipsSpeed.get(mmsi);
        if (listSpeed == null) {
            listSpeed = new ArrayList<Float>();
        }
        if (speed != null) {
            listSpeed.add(speed);
        } else {
            listSpeed.add(0.0F);
        }
        shipsSpeed.put(mmsi, listSpeed);
        Float averageSpeed = 0.0F;
        for (Float sd : listSpeed) {
            averageSpeed += sd;
        }
        //System.err.println(new Values(mmsi, averageSpeed / listSpeed.size()));
        boc.emit(new Values(mmsi, averageSpeed / listSpeed.size()));
    }
}
