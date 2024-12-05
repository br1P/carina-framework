import com.zebrunner.carina.core.IAbstractTest;
import io.restassured.response.Response;
import placeholder.api.DeletePostMethod;
import placeholder.api.GetAllPostMethod;

import org.testng.Assert;
import org.testng.annotations.Test;
import placeholder.api.GetOnePostMethod;

public class JsonPlaceholderTests implements IAbstractTest {
    @Test
    public void testGetAllPosts() {
        GetAllPostMethod getAllPosts = new GetAllPostMethod();
        getAllPosts.callAPIExpectSuccess();
        getAllPosts.validateResponseAgainstSchema("api/posts/_get/rs.schema");
    }

    @Test
    public void testGetPostById() {
        GetOnePostMethod getPostById = new GetOnePostMethod("1");
        getPostById.callAPIExpectSuccess();
        getPostById.validateResponseAgainstSchema("api/posts/_get_by_id/rs.schema");
    }

    @Test()
    public void testDeleteComment() {
        DeletePostMethod deleteComment = new DeletePostMethod("1");
        deleteComment.callAPIExpectSuccess();
        deleteComment.validateResponse();
    }


}