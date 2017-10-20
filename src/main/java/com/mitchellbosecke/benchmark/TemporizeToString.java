package com.mitchellbosecke.benchmark;

import com.mitchellbosecke.benchmark.model.Stock;
import index.Stocks;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Benchmark for Rocker template engine by Fizzed.
 * <p>
 * https://github.com/fizzed/rocker
 *
 * @author joelauer
 */
public class TemporizeToString extends BaseBenchmark {

    private List<Stock> items;

    public static void main(String... args) throws IOException {
        String str = getOutput(Stock.dummyItems());
        System.out.println(str);
    }

    public static String getOutput(List<Stock> stocks) {
        List<Stocks.Items> items = new ArrayList<>();

        for (int i = 0; i < stocks.size(); i++) {
            final Stock stock = stocks.get(i);

            int idx = i + 1;
            items.add(new Stocks.Items()
                    .setIndex(String.valueOf(idx))
                    .setName(stock.getName())
                    .setRatio(String.valueOf(stock.getRatio()))
                    .setChange(String.valueOf(stock.getChange()))
                    .setNegativeClass(stock.getChange() > 0.0d ? "" : " class=\"minus\"")
                    .setSymbol(stock.getSymbol())
                    .setPrice(String.valueOf(stock.getPrice()))
                    .setUrl(stock.getUrl())
                    .setRowClass(idx % 2 == 0 ? "even" : "odd")
            );
        }

        return new Stocks()
                .setItems(items)
                .toString();
    }

    @Setup
    public void setup() throws IOException {
        // no config needed, replicate stocks from context
        this.items = Stock.dummyItems();
    }

    @Benchmark
    public String benchmark() throws IOException {
        return getOutput(this.items);
    }
}
