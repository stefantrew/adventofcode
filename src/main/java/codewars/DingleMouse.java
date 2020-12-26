package codewars;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DingleMouse {

    public static int countDeafRats(final String town) {
        // Your code here

        log.info("Input {}", town);
        String stage = town.replaceAll("\\s", "");
        log.info("Stage {}", stage);

        int pos = stage.indexOf("P");
        log.info("Pos  {}", pos);
        stage = stage.replaceAll("P", "");
        log.info("Stage {}",stage);

        int deaf = 0;
        for (int i = 0; i < stage.length() / 2; i++) {
            log.info("{}{}", stage.charAt(2 * i), stage.charAt(2 * i + 1));

            boolean isLeft = stage.charAt(2 * i) == '~';
            boolean shouldBeLeft = 2 * i < pos;
            if (isLeft != shouldBeLeft) deaf++;

        }

        return deaf;
    }

}
