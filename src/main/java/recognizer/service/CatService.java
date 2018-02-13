package recognizer.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import recognizer.matcher.TemplateMatcher;
import recognizer.model.Image;
import recognizer.model.Positions;

@RestController
@RequestMapping(value = "/cat")
public class CatService {

  @Autowired
  private TemplateMatcher templateMatcher;

  /**
   * Endpoint to receive a Image object and return list of positions and number of matches
   *
   * Image: object containing list of string representing pixels of a image and threshold value to match
   */
  @RequestMapping(value = "/recognize", method = RequestMethod.POST)
  public ResponseEntity<Positions> catFacePositions(@RequestBody Image image) {

    List<String> imageGenerated;

    final Logger logger = LoggerFactory.getLogger(CatService.class);

    try {

      imageGenerated = Files.readAllLines(
          Paths.get(getClass().getClassLoader().getResource("CatFace image_with_cats.txt").toURI())
      );

      //image.setImage(imageGenerated);

    } catch (Exception e) {

      logger.error("Error while loading cats image");

      e.printStackTrace();

    }

    Positions positions = templateMatcher.slidingWindow(image.getImages(), image.getThreshold());

    return ResponseEntity.ok(positions);
  }

}
