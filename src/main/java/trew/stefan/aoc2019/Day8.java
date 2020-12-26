package trew.stefan.aoc2019;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;
import trew.stefan.utils.NumberUtil;

import java.util.List;

@Slf4j
public class Day8 implements Day {

    class Layer {

        public byte[][] rows = new byte[6][25];

        Layer(String rowsstring) {

            for (int i = 0; i < 6; i++) {

                String row = rowsstring.substring(0, 25);
                rowsstring = rowsstring.substring(25);
                rows[i] = NumberUtil.BurstDigits(row);
            }

//            log.info("Rows {}", rows);
        }

    }


    public void run() {
        List<String> lines = InputReader2019.readStrings(8, "");
        String data = lines.get(0);
        int number = data.length() / 150;
        log.info("layers {}", number);
        Layer[] layers = new Layer[number];


        for (int i = 0; i < number; i++) {

            String row = data.substring(0, 150);


            data = data.substring(150);


            layers[i] = new Layer(row);
        }

        for (int i = 0; i < 6; i++) {
            StringBuilder str = new StringBuilder();
            for (int j = 0; j < 25; j++) {
                byte current = 0;
                for (int l = 0; l < 100; l++) {
                    byte sample = layers[l].rows[i][j];
                    if (sample != 2) {
                        current = sample;
                        break;
                    }

                }
                if (current == 1) {
                    str.append(current);

                } else {
                    str.append(' ');

                }
            }
            log.info(str.toString());
        }
    }


}
