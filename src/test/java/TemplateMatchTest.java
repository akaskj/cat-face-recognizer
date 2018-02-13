import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class TemplateMatchTest {

  @Test
  public void templateMatchTest() {
    
    List<String> template = null;
    List<String> image = null;
    try {
      template = Files.readAllLines(
          Paths.get(getClass().getClassLoader().getResource("CatFace perfect_cat_image.txt").toURI())
      );
      image = Files.readAllLines(
          Paths.get(getClass().getClassLoader().getResource("CatFace image_with_cats.txt").toURI())
      );
    } catch (Exception e) {
      e.printStackTrace();
    }

    slidingWindow(template,image, 8);

//    Assert.assertEquals(expectedData, data.toString().trim());

  }

  void slidingWindow(List<String> template, List<String> image, int threshold) {

    int imageCount = 0;

    int templateMaxWidth = 0;
    List<int[]> positions = new ArrayList<>();

    for(String t: template) {
      templateMaxWidth = Math.max(templateMaxWidth, t.length());
    }

    for(int i = 0; i<= image.size() - template.size(); i++) {
      for(int j = 0; j <= image.get(0).length() - templateMaxWidth; j++) {
        if(matchTemplate(template, image.subList(i,i+template.size()), j, j + templateMaxWidth) < threshold ){
          int[] position = {i, j};
          positions.add(position);
          imageCount++;
        }
      }
    }

    System.out.println(imageCount);

    for(int[] position: positions) {
      System.out.println(position[0] + " " + position[1]);
    }

  }

  public int matchTemplate(List<String> template, List<String> image, int rowStart, int rowEnd) {

    int deviation = 0;

    for(int j = 0; j < image.size(); j++) {
      String t = template.get(j);
      String img = image.get(j);
      if(rowEnd > img.length()) {
        deviation += getStringDiff(t, img.substring(rowStart));
      } else {
        deviation += getStringDiff(t, img.substring(rowStart, rowEnd));
      }
    }

    return deviation;

  }

  int getStringDiff(String s1, String s2) {
    int d = 0;

    for(int i = 0; i < Math.min(s1.length(), s2.length()); i++) {
      if(s1.charAt(i) != s2.charAt(i))
        d++;
    }

    return d;
  }



}
