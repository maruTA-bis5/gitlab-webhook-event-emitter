package net.bis5.gitlab;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.webhook.WebHookManager;

@Path("/webhook")
@RequestScoped
public class GitLabWebhookResource {

    @Inject
    CdiEventEmitter eventEmitter;

    private WebHookManager gitlabWebhookManager;

    @PostConstruct
    void initialize() {
        // TODO token support
        gitlabWebhookManager = new WebHookManager();
        gitlabWebhookManager.addListener(eventEmitter);
    }

    @PreDestroy
    void close() {
        gitlabWebhookManager.removeListener(eventEmitter);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response onWebhookReceived(@Context HttpServletRequest request)
            throws GitLabApiException {
        gitlabWebhookManager.handleEvent(request);
        return Response.noContent().build();
    }
}
