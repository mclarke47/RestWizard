package com.gmail.matthewclarke47;

import com.gmail.matthewclarke47.formatting.DocView;
import com.gmail.matthewclarke47.metadata.ResourceMetaData;

import javax.print.Doc;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/docs")
public class DocResource {

    private List<ResourceMetaData> list;

    public DocResource(List<ResourceMetaData> list){

        this.list = list;
    }

    @GET
    @Path("/docs")
    @Produces(MediaType.TEXT_HTML)
    public Response getPerson() {
        return Response.ok(new DocView("doc.mustache", list)).build();
    }
}
