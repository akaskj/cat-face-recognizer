import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import recognizer.Application;
import recognizer.matcher.TemplateMatcher;
import recognizer.model.Positions;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TemplateMatcherTest {

  @Autowired
  private TemplateMatcher templateMatcher;

  @Test
  public void slidingWindowTest() {

    List<String> image = new ArrayList<>();
    image.add("++++++ ++ ++++++ ");

    Positions positions = templateMatcher.slidingWindow(image, 10);
    assertThat(positions.getMatches()).isEqualTo(0);

  }

  @Test
  public void matchTemplateTest() {

    List<String> template = new ArrayList<>();
    template.add("++++++ ++ ++++++ ");

    List<String> image = new ArrayList<>();
    image.add("++++++ ++ + +++++ ");

    int deviation = templateMatcher.matchTemplate(template, image, 0, 18);

    assertThat(deviation).isEqualTo(2);

  }

}
