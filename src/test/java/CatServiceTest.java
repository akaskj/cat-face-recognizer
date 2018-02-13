import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import recognizer.Application;
import recognizer.matcher.TemplateMatcher;
import recognizer.model.Image;
import recognizer.model.Positions;
import recognizer.service.CatService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class CatServiceTest {


  @Mock
  private TemplateMatcher templateMatcher;

  @InjectMocks
  private CatService catService;

  @Test
  public void catFacePositionsTest(){

    List<String> images = new ArrayList<>();

    images.add("+++++ +++++ + ++++++ ++ + ++++++");

    when(templateMatcher.slidingWindow(images , 10)).thenReturn(new Positions());

    Image image = new Image();
    image.setImages(images);
    image.setThreshold(10);

    assertThat(catService.catFacePositions(image).getStatusCode()).isEqualTo(HttpStatus.OK);

  }

}
