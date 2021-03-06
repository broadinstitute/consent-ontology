package org.broadinstitute.dsde.consent.ontology.resources;

import java.io.InputStream;
import java.net.URI;
import java.util.Properties;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import org.apache.commons.lang3.StringUtils;
import org.broadinstitute.dsde.consent.ontology.Utils;
import org.parboiled.common.FileUtils;
import org.slf4j.Logger;

@Path("/")
public class SwaggerResource {

    private final Logger log = Utils.getLogger(this.getClass());

    // Default swagger ui library if not found in properties
    // should not hard-code the actual version here!
    private final static String DEFAULT_LIB = "META-INF/resources/webjars/swagger-ui/latest/";
    final static String MEDIA_TYPE_CSS = new MediaType("text", "css").toString();
    final static String MEDIA_TYPE_JS = new MediaType("application", "js").toString();
    final static String MEDIA_TYPE_PNG = new MediaType("image", "png").toString();
    private final static String MEDIA_TYPE_GIF = new MediaType("image", "gif").toString();

    private String swaggerResource = null;

    private String getSwaggerResource() {
        if (swaggerResource == null) {
            try (InputStream is = this.getClass().getResourceAsStream("/mvn.properties")) {
                Properties p = new Properties();
                p.load(is);
                if (StringUtils.isNotEmpty(p.getProperty("swagger.ui.path"))) {
                    swaggerResource = p.getProperty("swagger.ui.path");
                } else {
                    log.warn("swagger.ui.path is not configured correctly, defaulting to: " + DEFAULT_LIB);
                    swaggerResource = DEFAULT_LIB;
                }
            } catch (Exception e) {
                log.warn(e.getMessage());
                log.warn("Defaulting to: " + DEFAULT_LIB);
                swaggerResource = DEFAULT_LIB;
            }
        }
        return swaggerResource;
    }

    @Context
    UriInfo uriInfo;

    @GET
    public Response main() {
        return content("");
    }

    @GET
    @Path("swagger")
    public Response swagger() {
        URI uri = UriBuilder.fromPath("/").scheme("https").build();
        return Response.seeOther(uri).build();
    }

    @GET
    @Path("{path:.*}")
    public Response content(@PathParam("path") String path) {
        String swaggerResource = getSwaggerResource();
        Response response;
        String mediaType = getMediaTypeFromPath(path);
        if (path.isEmpty() || path.equals("index.html")) {
            response = Response.ok().entity(getIndex(swaggerResource)).type(mediaType).build();
        } else {
            mediaType = getMediaTypeFromPath(path);
            Object content;
            if (path.endsWith("png") || path.endsWith("gif")) {
                content = FileUtils.readAllBytesFromResource(swaggerResource + path);
            } else {
                content = FileUtils.readAllTextFromResource(swaggerResource + path);
            }
            if (content != null) {
                response = Response.ok().entity(content).type(mediaType).build();
            } else {
                response = Response.status(Response.Status.NOT_FOUND).build();
            }
        }
        return response;
    }

    private String getMediaTypeFromPath(String path) {
        String mediaType;
        switch (StringUtils.substringAfterLast(path, ".")) {
            case "css":
                mediaType = MEDIA_TYPE_CSS;
                break;
            case "js":
                mediaType = MEDIA_TYPE_JS;
                break;
            case "png":
                mediaType = MEDIA_TYPE_PNG;
                break;
            case "gif":
                mediaType = MEDIA_TYPE_GIF;
                break;
            default:
                mediaType = MediaType.TEXT_HTML;
                break;
        }
        return mediaType;
    }

    private String getIndex(String swaggerResource) {
        String content = FileUtils.readAllTextFromResource(swaggerResource + "index.html");
        return content
            .replace("url: \"https://petstore.swagger.io/v2/swagger.json\"",
                " syntaxHighlight: {\n" +
                    "          activated: false,\n" +
                    "          theme: \"agate\"\n" +
                    "        },\n" +
                    "        docExpansion: 'none',\n" +
                    "        displayRequestDuration: true,\n" +
                    "        tryItOutEnabled: true,\n" +
                    "        operationsSorter: 'alpha',\n" +
                    "        tagsSorter: 'alpha',\n" +
                    "        url: '/api-docs/api-docs.yaml'\n");
    }

}
