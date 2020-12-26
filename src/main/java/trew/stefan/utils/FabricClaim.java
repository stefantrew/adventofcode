package trew.stefan.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@AllArgsConstructor
public class FabricClaim {
    String id;
    Integer x;
    Integer y;
    Integer width;
    Integer height;
}
