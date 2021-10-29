package com.enoxs.Java8;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ISinkSort<T> implements ISink<T>{
    private final ISink<T> downstream;
    private List<T> list;
    private final Comparator<T> cpt;

    public ISinkSort(ISink<T> downstream, Comparator<T> cpt) {
        this.downstream = downstream;
        this.cpt = cpt;
    }
    /**
     *                sorted sink
     *                      begin => list
     *                      end =>  list.sort(cpt)
     *                              for loop list
     *                                  downstreamSink.accept(v)
     *                              downstreamSink.end()
     *                      accept => list.add(v)
     */
    @Override
    public void begin(long size) {
        list = new ArrayList<>();
    }

    @Override
    public void end() {
        list.sort(cpt);
        downstream.begin(-1);
        list.forEach(t -> downstream.accept(t));
        downstream.end();
    }

    @Override
    public void accept(T t) {
        list.add(t);
    }
}
