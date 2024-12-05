package placeholder.api;

import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.ResponseTemplatePath;
import com.zebrunner.carina.api.annotation.SuccessfulHttpStatus;
import com.zebrunner.carina.api.http.HttpMethodType;
import com.zebrunner.carina.api.http.HttpResponseStatusType;
import com.zebrunner.carina.utils.config.Configuration;

@Endpoint(url = "${base_url}/posts/${id}", methodType = HttpMethodType.GET)
@ResponseTemplatePath(path = "api/posts/_get_by_id/rs.json")
@SuccessfulHttpStatus(status = HttpResponseStatusType.OK_200)
public class GetOnePostMethod extends AbstractApiMethodV2 {
    public GetOnePostMethod(String id) {
        replaceUrlPlaceholder("base_url", Configuration.getRequired("api_url"));
        replaceUrlPlaceholder("id", id);
    }
}
