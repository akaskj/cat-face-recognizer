package recognizer.matcher;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import recognizer.model.Position;
import recognizer.model.Positions;

@Component
public class TemplateMatcher {

  private List<String> template;

  private final Logger logger = LoggerFactory.getLogger(TemplateMatcher.class);

  /**
  * Initialize with perfect cat face.
  * */
  @Autowired
  public TemplateMatcher(@Value("${template}")String templateFile) {
    try {
      template = Files.readAllLines(
          Paths.get(
              getClass().getClassLoader().getResource(templateFile).toURI()
          )
      );

      logger.info("Cat face template initialized");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Match the template with the image with sliding window technique
   *
   * To Do: improve time complexity of algorithm
   *
   * image: list of string representing pixel of images
   * threshold: minimum match value of template and image
   */
  public Positions slidingWindow(List<String> image, int threshold) {

    int imageCount = 0;
    int templateMaxWidth = 0;

    List<Position> positionList = new ArrayList<>();
    Positions positions = new Positions();

    for(String t: template) {
      templateMaxWidth = Math.max(templateMaxWidth, t.length());
    }

    for(int i = 0; i<= image.size() - template.size(); i++) {
      for(int j = 0; j <= image.get(0).length() - templateMaxWidth; j++) {

        int currentDeviation = matchTemplate(template, image.subList(i,i+template.size()), j, j + templateMaxWidth);

        if(currentDeviation < threshold ){

          Position position = new Position();
          position.setPosition(new int[]{i, j});
          position.setDeviation(currentDeviation);
          positionList.add(position);
          imageCount++;

        }
      }
    }

    logger.info(imageCount + ": images found");

    positions.setPositionList(positionList);
    positions.setMatches(imageCount);

    return positions;

  }


  /**
   * Matches 2 matrix and returns deviation
   *
   * template: list of string of template image
   * image: part of the image to be matched
   * rowStart: start of element to be matched
   * rowEnd: end of element to be matched
   */
  public int matchTemplate(List<String> template, List<String> image, int rowStart, int rowEnd) {

    int deviation = 0;

    for(int j = 0; j < image.size(); j++) {

      String t = template.get(j);
      String img = image.get(j);
      int end = (rowEnd > img.length()) ? img.length() : rowEnd;

      for(int i = 0; i < t.length(); i++) {
        if(i+rowStart < end && t.charAt(i) != img.charAt(i+rowStart)) {
          deviation++;
        }
      }

    }

    return deviation;

  }

}
