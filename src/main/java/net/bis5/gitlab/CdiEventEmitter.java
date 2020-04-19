package net.bis5.gitlab;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import org.gitlab4j.api.webhook.BuildEvent;
import org.gitlab4j.api.webhook.IssueEvent;
import org.gitlab4j.api.webhook.JobEvent;
import org.gitlab4j.api.webhook.MergeRequestEvent;
import org.gitlab4j.api.webhook.NoteEvent;
import org.gitlab4j.api.webhook.PipelineEvent;
import org.gitlab4j.api.webhook.PushEvent;
import org.gitlab4j.api.webhook.TagPushEvent;
import org.gitlab4j.api.webhook.WebHookListener;
import org.gitlab4j.api.webhook.WikiPageEvent;

@ApplicationScoped
public class CdiEventEmitter implements WebHookListener {

    @Inject
    Event<BuildEvent> buildEventBus;

    @Override
    public void onBuildEvent(BuildEvent event) {
        buildEventBus.fireAsync(event);
    }

    @Inject
    Event<IssueEvent> issueEventBus;

    @Override
    public void onIssueEvent(IssueEvent event) {
        issueEventBus.fireAsync(event);
    }

    @Inject
    Event<JobEvent> jobEventBus;

    @Override
    public void onJobEvent(JobEvent event) {
        jobEventBus.fireAsync(event);
    }

    @Inject
    Event<MergeRequestEvent> mergeRequestEventBus;

    @Override
    public void onMergeRequestEvent(MergeRequestEvent event) {
        mergeRequestEventBus.fireAsync(event);
    }

    @Inject
    Event<NoteEvent> noteEventBus;

    @Override
    public void onNoteEvent(NoteEvent event) {
        noteEventBus.fireAsync(event);
    }

    @Inject
    Event<PipelineEvent> pipelineEventBus;

    @Override
    public void onPipelineEvent(PipelineEvent event) {
        pipelineEventBus.fireAsync(event);
    }

    @Inject
    Event<PushEvent> pushEventBus;

    @Override
    public void onPushEvent(PushEvent event) {
        pushEventBus.fireAsync(event);
    }

    @Inject
    Event<TagPushEvent> tagPushEventBus;

    @Override
    public void onTagPushEvent(TagPushEvent event) {
        tagPushEventBus.fireAsync(event);
    }

    @Inject
    Event<WikiPageEvent> wikiPageEventBus;

    @Override
    public void onWikiPageEvent(WikiPageEvent event) {
        wikiPageEventBus.fireAsync(event);
    }

}
