package recognizer.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Image {

  private List<String> images;
  private int threshold;

}

