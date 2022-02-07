package nh.publy.backend.graphql;

import nh.publy.backend.domain.CommentAddedEvent;
import nh.publy.backend.domain.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * RatingPublisher
 */
@Component
public class CommentEventPublisher {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  @PersistenceContext
  private EntityManager entityManager;

  private final CommentRepository commentRepository;

  private final Flux<OnNewCommentEvent> publisher;
  private FluxSink<OnNewCommentEvent> emitter;

  public CommentEventPublisher(CommentRepository commentRepository) {
    this.commentRepository = commentRepository;
    ConnectableFlux<OnNewCommentEvent> publish = Flux.<OnNewCommentEvent>create(fluxSink -> this.emitter = fluxSink)
      .publish();
    this.publisher = publish.autoConnect();
  }

  @TransactionalEventListener
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void onNewComment(CommentAddedEvent event) {
    if (this.emitter == null) {
      logger.info("onNewComment received event for comment {}, but emitter is null", event.commentId());
      return;
    }
    logger.info("onNewComment received event for comment {}, and emitter is set ", event.commentId());
    this.emitter.next(
      new OnNewCommentEvent(
        commentRepository,
        event.storyId(),
        event.commentId()
      )
    );
  }

  public Flux<OnNewCommentEvent> getPublisher(Long storyId) {
    return this.publisher.filter(event -> storyId.equals(event.getStoryId()));
  }
}